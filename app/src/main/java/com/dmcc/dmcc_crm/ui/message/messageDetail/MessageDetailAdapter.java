package com.dmcc.dmcc_crm.ui.message.messageDetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmcc.dmcc_crm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wushange on 2016/06/15.
 */
public class MessageDetailAdapter extends BaseAdapter {
    List<String> mList = new ArrayList<String>();
    Context context;

    public MessageDetailAdapter(Context context, List<String> mList) {
        this.mList = mList;
        this.context = context;
    }

    public void updataList(List<String> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder cvh;
        if (convertView == null) {
            cvh = new CustomViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_message_view, null);
            cvh.linearLayout = (LinearLayout) convertView.findViewById(R.id.ll_msg_item);
            cvh.imgLogo = (ImageView) convertView.findViewById(R.id.iv_item_messgae_icon);
            cvh.txtName = (TextView) convertView.findViewById(R.id.tv_item_message_name);
            convertView.setTag(cvh);
        } else {
            cvh = (CustomViewHolder) convertView.getTag();
        }
        cvh.txtName.setText(mList.get(position).toString());
        showItemAnim(cvh.linearLayout, position);
        return convertView;
    }

    private int mLastPosition = -1;

    public void showItemAnim(final View view, final int position) {
        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.item_bottom_in);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    view.setAlpha(1);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            view.startAnimation(animation);
            mLastPosition = position;
        }
    }

    class CustomViewHolder {
        public LinearLayout linearLayout;
        public ImageView imgLogo;
        public TextView txtName;
    }
}
