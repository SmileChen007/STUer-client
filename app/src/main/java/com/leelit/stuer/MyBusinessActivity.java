package com.leelit.stuer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.leelit.stuer.constant.MyBusinessConstant;
import com.leelit.stuer.module_baseinfo.carpool.MyCarpoolFragment;
import com.leelit.stuer.module_baseinfo.date.MyDateFragment;
import com.leelit.stuer.module_sell.MySellFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MyBusinessActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.content)
    FrameLayout mContent;

    private int mOrderActivityConstant;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybusiness);
        ButterKnife.inject(this);

        mOrderActivityConstant = getIntent().getIntExtra(MyBusinessConstant.TAG, 1);

        if (mOrderActivityConstant == MyBusinessConstant.CARPOOL) {
            mToolbar.setTitle(getString(R.string.mine_title_1));
            mFragment = new MyCarpoolFragment();
        }else if (mOrderActivityConstant == MyBusinessConstant.DATE) {
            mToolbar.setTitle(getString(R.string.mine_title_2));
            mFragment = new MyDateFragment();
        }else if (mOrderActivityConstant == MyBusinessConstant.SELL) {
            mToolbar.setTitle("我的转让");
            mFragment = new MySellFragment();
        }


        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.content, mFragment).
                commit();

    }

}