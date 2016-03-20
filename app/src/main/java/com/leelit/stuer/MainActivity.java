package com.leelit.stuer;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.leelit.stuer.base_fragments.BaseInfoPostActivity;
import com.leelit.stuer.base_fragments.BaseListFragment;
import com.leelit.stuer.constant.FragmentIndex;
import com.leelit.stuer.constant.MyBusinessConstant;
import com.leelit.stuer.constant.TabConstant;
import com.leelit.stuer.module_baseinfo.carpool.CarpoolFragment;
import com.leelit.stuer.module_baseinfo.date.DateFragment;
import com.leelit.stuer.module_sell.SellFragment;
import com.leelit.stuer.module_sell.SellPostActivity;
import com.leelit.stuer.module_stu.StuFragment;
import com.leelit.stuer.utils.SPUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.fabBtn)
    FloatingActionButton mFabBtn;
    @InjectView(R.id.navigationView)
    NavigationView mNavigationView;
    @InjectView(R.id.drawerLayout)
    DrawerLayout mDrawerLayout;
    @InjectView(R.id.tabLayout)
    TabLayout mTabLayout;

    private MenuItem mMainMenuItem;

    private ActionBarDrawerToggle mDrawerToggle;

    private BaseListFragment currentFragment;

    public static int mTabValue = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        if (!SPUtils.getBoolean(LoginActivity.IS_REGISTER)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;  // must return here
        }

        initDrawerAndToolbar();
        initNavigationView();
        initFab();
        initFragment();
    }


    private void initFragment() {
        BaseListFragment carpoolFragment = new CarpoolFragment();
        showFragment(carpoolFragment);
        currentFragment = carpoolFragment;
    }

    private void initFab() {
        mFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentFragment instanceof CarpoolFragment) {
                    Intent intent = new Intent(MainActivity.this, BaseInfoPostActivity.class);
                    intent.putExtra(FragmentIndex.TAG, FragmentIndex.CARPOOL);
                    startActivity(intent);
                } else if (currentFragment instanceof DateFragment) {
                    Intent intent = new Intent(MainActivity.this, BaseInfoPostActivity.class);
                    intent.putExtra(FragmentIndex.TAG, FragmentIndex.DATE);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, SellPostActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void initDrawerAndToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.hello_world, R.string.hello_world);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.nav_setting) {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    return false;
                }

                mDrawerLayout.closeDrawers();
                switchFragment(menuItem);
                return false;
            }
        });
    }

    private void initNavigationView() {
        mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
    }

    private void switchFragment(MenuItem menuItem) {
        String title = menuItem.getTitle().toString();
        mToolbar.setTitle(title);
        if (title.equals(getString(R.string.carpool))) {
            if (currentFragment instanceof CarpoolFragment) {
                return;
            } else {
                carpoolInit();
            }
        } else if (title.equals(getString(R.string.date))) {
            if (currentFragment instanceof DateFragment) {
                return;
            } else {
                dateInit();
            }
        } else if (title.equals(getString(R.string.stu))) {
            if (currentFragment instanceof StuFragment) {
                return;
            } else {
                stuInit();
            }
        } else if (title.equals(getString(R.string.sell))) {
            if (currentFragment instanceof SellFragment) {
                return;
            } else {
                sellInit();
            }
        }
        showFragment(currentFragment);
    }

    private void stuInit() {
        mFabBtn.setVisibility(View.INVISIBLE);
        mMainMenuItem.setVisible(false);
        currentFragment = new StuFragment();
        mTabLayout.setVisibility(View.GONE);
    }

    private void sellInit() {
        mFabBtn.setVisibility(View.VISIBLE);
        mMainMenuItem.setVisible(true);
        mMainMenuItem.setIcon(R.drawable.ic_menu_sell_my);
        currentFragment = new SellFragment();
        mTabLayout.setVisibility(View.GONE);
    }

    private void dateInit() {
        mFabBtn.setVisibility(View.VISIBLE);
        mMainMenuItem.setVisible(true);
        mMainMenuItem.setIcon(R.drawable.ic_nav_date);
        currentFragment = new DateFragment();
        mTabLayout.setVisibility(View.VISIBLE);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        int[] tabs = {R.string.tab_sport, R.string.tab_eat, R.string.tab_film, R.string.tab_game, R.string.tab_library, R.string.tab_others};
        for (int i = 0; i <= 5; i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(getString(tabs[i])));
        }
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String text = tab.getText().toString();
                if (text.equals(getString(R.string.tab_sport))) {
                    mTabValue = TabConstant.SPORT;
                } else if (text.equals(getString(R.string.tab_eat))) {
                    mTabValue = TabConstant.EAT;
                } else if (text.equals(getString(R.string.tab_film))) {
                    mTabValue = TabConstant.FILM;
                } else if (text.equals(getString(R.string.tab_game))) {
                    mTabValue = TabConstant.GAME;
                } else if (text.equals(getString(R.string.tab_library))) {
                    mTabValue = TabConstant.LIBRARY;
                } else if (text.equals(getString(R.string.tab_others))) {
                    mTabValue = TabConstant.OTHERS;
                }
                currentFragment.taskAfterLoaded();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void carpoolInit() {
        mFabBtn.setVisibility(View.VISIBLE);
        mMainMenuItem.setVisible(true);
        mMainMenuItem.setIcon(R.drawable.ic_nav_car);
        currentFragment = new CarpoolFragment();
        mTabLayout.setVisibility(View.GONE);
    }


    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.content, fragment).
                commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMainMenuItem = menu.getItem(0);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mine) {
            if (currentFragment instanceof CarpoolFragment) {
                Intent intent = new Intent(MainActivity.this, MyBusinessActivity.class);
                startActivity(intent);
            } else if (currentFragment instanceof DateFragment) {
                Intent intent = new Intent(MainActivity.this, MyBusinessActivity.class);
                intent.putExtra(MyBusinessConstant.TAG, MyBusinessConstant.DATE);
                startActivity(intent);
            } else if (currentFragment instanceof SellFragment) {
                Intent intent = new Intent(this, MyBusinessActivity.class);
                intent.putExtra(MyBusinessConstant.TAG, MyBusinessConstant.SELL);
                startActivity(intent);
            }
            return true;
        }

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }

}
