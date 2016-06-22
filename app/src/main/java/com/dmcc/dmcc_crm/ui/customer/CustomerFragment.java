package com.dmcc.dmcc_crm.ui.customer;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.base.MvpBaseFragmentV4;
import com.dmcc.dmcc_crm.bean.User;
import com.dmcc.dmcc_crm.customview.AppTitle;
import com.dmcc.dmcc_crm.customview.MyLetterView;
import com.dmcc.dmcc_crm.ui.customer.company.CompanyActivity;
import com.dmcc.dmcc_crm.ui.customer.myfollow.MyFollowActivity;
import com.dmcc.dmcc_crm.ui.customer.potential.PotentialActivity;
import com.dmcc.dmcc_crm.ui.login.LoginActivity;
import com.dmcc.dmcc_crm.util.NotifyUtil;
import com.wushange.commsdk.util.ShowToast;
import com.wushange.commsdk.view.LoadingView;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by wushange on 2016/05/31.
 */
public class CustomerFragment extends MvpBaseFragmentV4<CustomerContract.CustomerView, CustomersPresenter> implements CustomerContract.CustomerView {
    @ViewInject(R.id.apptitle)
    AppTitle appTitle;
    private View mHeaderView;
    @ViewInject(R.id.lv_recyclerView)
    ListView mListView;
    @ViewInject(R.id.mlv_right_letter)
    private MyLetterView letterSideBar; // 字母导航栏
    @ViewInject(R.id.tv_dialog)
    private TextView dialog;
    private List<User> members = new ArrayList<User>();
    private CustomerAdapter adapter;
    int itemPosition, top;
    @ViewInject(R.id.ldv_loading)
    LoadingView loadingView;
    private int requestCode = (int) SystemClock.uptimeMillis();

    @Override
    public int bindLayout() {
        return R.layout.fragment_customer_main_view;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        appTitle.init(AppTitle.AppTitleStyle.TITLE_RIGHT_BTN);
        appTitle.setTitle("客户");
        appTitle.setRightBtnClickClisener(R.mipmap.jiahao, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notify_mailbox();
                ShowToast.showToastInCenter(getContext(), "添加");

            }
        });
        initHeadView();
        adapter = new CustomerAdapter(getContext(), members);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        LogUtil.e("initView");
    }

    /**
     * 收件箱样式
     */
    private void notify_mailbox() {
        //设置想要展示的数据内容
        int smallIcon = R.mipmap.ic_launcher;
        int largeIcon = R.mipmap.ic_launcher;
        String ticker = "您有一条新通知";
        String title = "范冰冰";
        String content = "文明，今晚在希尔顿酒店2016号房哈";
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),
                requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);


        int lefticon = R.mipmap.icon_audio;
        String lefttext = "回复";
        Intent leftIntent = new Intent();
        leftIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent leftPendingIntent = PendingIntent.getActivity(getContext(),
                requestCode, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int righticon = R.mipmap.icon_contact;
        String righttext = "拨打";
        Intent rightIntent = new Intent(getContext(), LoginActivity.class);
        rightIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent rightPendingIntent = PendingIntent.getActivity(getContext(),
                requestCode, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //实例化工具类，并且调用接口
        NotifyUtil notify8 = new NotifyUtil(getContext(), 8);
        notify8.notify_HeadUp(pendingIntent, smallIcon, largeIcon, ticker, title, content, lefticon, lefttext, leftPendingIntent, righticon, righttext, rightPendingIntent, true, true, false);
    }

    /**
     * 初始化头部信息
     */
    private void initHeadView() {
        mHeaderView = LayoutInflater.from(getContext()).inflate(
                R.layout.item_customer_header_view, null);
        LinearLayout shoucang = (LinearLayout) mHeaderView.findViewById(R.id.item_head_customer_shoucang);
        LinearLayout qianzai = (LinearLayout) mHeaderView.findViewById(R.id.item_head_customer_qianzaikehu);
        LinearLayout gongsi = (LinearLayout) mHeaderView.findViewById(R.id.item_head_customer_gongsi);
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyFollowActivity.startActivity(getContext());
            }
        });
        qianzai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PotentialActivity.startActivity(getContext());
            }
        });
        gongsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompanyActivity.startActivity(getContext());
            }
        });
        mListView.addHeaderView(mHeaderView);
        mListView.setEmptyView(loadingView);

        initFirstLetter();
    }

    @Override
    public void doBusiness(Context mContext) {
        LogUtil.e("doBusiness");
        presenter.getCustomers(6);

    }

    @Override
    public void lazyInitBusiness(Context mContext) {
        LogUtil.e("lazyInitBusiness");

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
    public void OnGetCustomersView(final List<User> users) {
        members = users;
        adapter.updateDatas(members);

    }

    @Override
    public void OnGetCustomersErrorView(String errormsg) {
        ShowToast.showToast(getContext(), "错误");
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

    @Override
    public CustomersPresenter initPresenter() {
        return new CustomersPresenter();
    }

}
