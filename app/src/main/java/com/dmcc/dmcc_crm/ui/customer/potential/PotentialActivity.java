package com.dmcc.dmcc_crm.ui.customer.potential;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.base.MvpBaseSwipeBackActivity;
import com.dmcc.dmcc_crm.bean.User;
import com.dmcc.dmcc_crm.customview.AppTitle;
import com.dmcc.dmcc_crm.customview.MyLetterView;
import com.dmcc.dmcc_crm.ui.message.messageDetail.MessageDetailActivity;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wushange on 2016/06/20.
 */
public class PotentialActivity extends MvpBaseSwipeBackActivity<PotentialContract.PotentialView, PotentialPresenter> implements PotentialContract.PotentialView {
    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, PotentialActivity.class);
        mContext.startActivity(intent);
    }

    @ViewInject(R.id.apptitle)
    AppTitle appTitle;
    @ViewInject(R.id.smlv_potential_list)
    SwipeMenuListView mListView;
    @ViewInject(R.id.mlv_right_letter)
    private MyLetterView letterSideBar; // 字母导航栏
    @ViewInject(R.id.tv_dialog)
    private TextView dialog;
    private PotentialAdapter adapter;
    private List<User> members = new ArrayList<User>();
    int itemPosition, top;

    @Override
    public PotentialPresenter initPresenter() {
        return new PotentialPresenter();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_potential_main_view;
    }

    @Override
    public void initView(View view) {
        appTitle.init(AppTitle.AppTitleStyle.BACK_TITLE);
        appTitle.setTitle("潜在客户");
        adapter = new PotentialAdapter(getContext(), members);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        initMenuList();
        initFirstLetter();
        LogUtil.e("initView");
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getCustomers(6);
    }

    /**
     * 初始化右侧字母
     */
    private void initFirstLetter() {
        letterSideBar.setTextView(dialog);
        letterSideBar
                .setOnTouchingLetterChangedListener(new LetterListViewListener());
        mListView.setSelectionFromTop(itemPosition, top);
    }

    /**
     * 初始化列表侧滑菜单
     */
    public void initMenuList() { // step 1. create a MenuCreator
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                openItem.setBackground(new ColorDrawable(Color.parseColor("#FFB14D")));
                openItem.setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width_img) - 30);
                openItem.setTitle("转为客户");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn_width_img) - 30);
                deleteItem.setTitle("取消关注");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(deleteItem);
            }
        };
        mListView.setMenuCreator(creator);
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                User item = members.get(position);
                switch (index) {
                    case 0:
                        break;
                    case 1:
                        members.remove(position);
                        adapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageDetailActivity.startActivity(getContext());
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Toast.makeText(getContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public void OnLoadDataSuccess(List<User> users) {
        members = users;
        adapter.updateDatas(members);
    }

    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }


    private class LetterListViewListener implements
            MyLetterView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(String s) {
            if (adapter != null && mListView != null) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position + 1);
                } else {
//                    mListView.setSelection(0);
                }
            }
        }
    }

}
