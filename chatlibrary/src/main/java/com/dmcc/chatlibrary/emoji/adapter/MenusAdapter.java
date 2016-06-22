package com.dmcc.chatlibrary.emoji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmcc.chatlibrary.R;
import com.dmcc.chatlibrary.emoji.data.MenuBean;

import java.util.ArrayList;

public class MenusAdapter extends BaseAdapter {
    private MenuClickCallBack menuClickCallBack;
    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<MenuBean> mDdata = new ArrayList<MenuBean>();

    public MenusAdapter(Context context, ArrayList<MenuBean> data, MenuClickCallBack menuClickCallBack) {
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.menuClickCallBack = menuClickCallBack;
        if (data != null) {
            this.mDdata = data;
        }
    }


    @Override
    public int getCount() {
        return mDdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mDdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_app, null);
            viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final MenuBean menuBean = mDdata.get(position);
        if (menuBean != null) {
            viewHolder.iv_icon.setBackgroundResource(menuBean.getIcon());
            viewHolder.tv_name.setText(menuBean.getFuncName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuClickCallBack.MenuItemClick(menuBean);
                }
            });
        }
        return convertView;
    }

    class ViewHolder {
        public ImageView iv_icon;
        public TextView tv_name;
    }

    public interface MenuClickCallBack {

        void MenuItemClick(MenuBean menuBean);

    }
}