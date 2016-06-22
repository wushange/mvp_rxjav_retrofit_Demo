package com.dmcc.chatlibrary.emoji.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.dmcc.chatlibrary.R;
import com.dmcc.chatlibrary.emoji.adapter.MenusAdapter;
import com.dmcc.chatlibrary.emoji.data.MenuBean;

import java.util.ArrayList;

public class SimpleMenusGridView extends RelativeLayout {

    protected View view;
    MenusAdapter.MenuClickCallBack menuClickCallBack;

    public SimpleMenusGridView(Context context) {
        this(context, null);
    }

    public void setMenuClickCallBack(MenusAdapter.MenuClickCallBack menuClickCallBack) {
        this.menuClickCallBack = menuClickCallBack;
        init();
    }

    public SimpleMenusGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_apps, this);
    }

    protected void init() {
        GridView gv_apps = (GridView) view.findViewById(R.id.gv_apps);
        ArrayList<MenuBean> mMenuBeanList = new ArrayList<>();
        mMenuBeanList.add(new MenuBean(R.mipmap.icon_photo, "图片"));
        mMenuBeanList.add(new MenuBean(R.mipmap.icon_camera, "拍照"));
        mMenuBeanList.add(new MenuBean(R.mipmap.icon_audio, "视频"));
        mMenuBeanList.add(new MenuBean(R.mipmap.icon_qzone, "空间"));
        mMenuBeanList.add(new MenuBean(R.mipmap.icon_contact, "联系人"));
        mMenuBeanList.add(new MenuBean(R.mipmap.icon_file, "文件"));
        mMenuBeanList.add(new MenuBean(R.mipmap.icon_loaction, "位置"));
        MenusAdapter adapter = new MenusAdapter(getContext(), mMenuBeanList, menuClickCallBack);
        gv_apps.setAdapter(adapter);
    }


}
