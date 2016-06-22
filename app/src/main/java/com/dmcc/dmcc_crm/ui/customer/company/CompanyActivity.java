package com.dmcc.dmcc_crm.ui.customer.company;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.base.MvpBaseSwipeBackActivity;
import com.dmcc.dmcc_crm.bean.User;
import com.dmcc.dmcc_crm.customview.AppTitle;
import com.dmcc.dmcc_crm.customview.MyLetterView;
import com.dmcc.dmcc_crm.ui.customer.myfollow.FollowPresenter;
import com.dmcc.dmcc_crm.ui.customer.myfollow.MyFollowContract;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wushange on 2016/06/20.
 */
public class CompanyActivity extends MvpBaseSwipeBackActivity<MyFollowContract.MyFollowView, FollowPresenter> implements MyFollowContract.MyFollowView {
    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, CompanyActivity.class);
        mContext.startActivity(intent);
    }

    @ViewInject(R.id.apptitle)
    AppTitle appTitle;
    @ViewInject(R.id.lv_recyclerView)
    ListView mListView;
    @ViewInject(R.id.mlv_right_letter)
    private MyLetterView letterSideBar; // 字母导航栏
    @ViewInject(R.id.tv_dialog)
    private TextView dialog;
    private CompanyAdapter adapter;
    private List<User> members = new ArrayList<User>();
    int itemPosition, top;

    @Override
    public FollowPresenter initPresenter() {
        return new FollowPresenter();
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_company_main_view;
    }

    @Override
    public void initView(View view) {
        appTitle.init(AppTitle.AppTitleStyle.BACK_TITLE);
        appTitle.setTitle("公司");
        adapter = new CompanyAdapter(getContext(), members);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
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
