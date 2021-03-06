package com.leelit.stuer.module_baseinfo.date;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leelit.stuer.R;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.utils.DialogUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Leelit on 2016/3/1.
 */
public class MyDateAdapter extends BaseListAdapter<MyDateAdapter.ViewHolder> {
    private Context mContext;
    private List<List<? extends BaseInfo>> mLists;


    public MyDateAdapter(List<List<? extends BaseInfo>> lists) {
        mLists = lists;
    }

    @Override
    public void removeData(int position) {
        mLists.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyDateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_date_my, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyDateAdapter.ViewHolder holder, int position) {
        final List<? extends BaseInfo> list = mLists.get(position);

        holder.mTextViewDateType.setText(((DatingInfo) list.get(0)).getType());
        holder.mTextViewDescription.setText(((DatingInfo) list.get(0)).getDescription());
        holder.mTextViewTiming.setText(list.get(0).getDate() + "  " + list.get(0).getTime());
        holder.mLinearLayout.removeAllViews();

        // fixed by placeholder //avoid reuse problem
        for (int i = 0; i < list.size(); i++) {
            DatingInfo info = (DatingInfo) list.get(i);
            TextView textView = getTextView(info, holder.mLinearLayout);
            holder.mLinearLayout.addView(textView);
        }

        if (mOnItemClickListener != null) {
            // 删除操作转移到view
            holder.mControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(v, pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }


    private TextView getTextView(final DatingInfo info, LinearLayout linearLayout) {
        TextView textView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.recycler_baseinfo_friends_inflate, linearLayout, false);
        if (info.getFlag().equals("host")) {
            String text = "拼主:" + info.getName() + " 已有:" + info.getTemporaryCount();
            textView.setText(text);
            textView.setBackgroundResource(R.drawable.carpool_tv_confirm_host);
        } else {
            String text = "拼客:" + info.getName() + " 已有:" + info.getTemporaryCount();
            textView.setText(text);
        }
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = DialogUtils.createContactDialog(mContext, info.getTel(), info.getShortTel(), info.getWechat());
                dialog.show();
            }
        });
        return textView;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.textView_type)
        TextView mTextViewDateType;
        @InjectView(R.id.textView_description)
        TextView mTextViewDescription;
        @InjectView(R.id.textView_timing)
        TextView mTextViewTiming;
        @InjectView(R.id.control)
        ImageButton mControl;
        @InjectView(R.id.linearLayout_place_holder)
        LinearLayout mLinearLayout;

        ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
