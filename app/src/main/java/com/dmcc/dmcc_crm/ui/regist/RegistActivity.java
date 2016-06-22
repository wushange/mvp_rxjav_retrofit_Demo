package com.dmcc.dmcc_crm.ui.regist;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.base.MvpBaseSwipeBackActivity;
import com.dmcc.dmcc_crm.customview.AppTitle;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by wushange on 2016/06/01.
 */
public class RegistActivity extends MvpBaseSwipeBackActivity<RegistContract.RegistView, RegistPresenter> implements RegistContract.RegistView {

    @ViewInject(R.id.regist_btn)
    Button regist;

    @ViewInject(R.id.getcode_btn)
    Button getcode_btn;
    @ViewInject(R.id.apptitle)
    AppTitle appTitle;

    @Override
    public int bindLayout() {
        return R.layout.activity_regist_main_view;
    }

    @Override
    public void initView(View view) {
        appTitle.init(AppTitle.AppTitleStyle.BACK_TITLE);
        appTitle.setTitle("注册");
        appTitle.setBackClickClisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Event(R.id.regist_btn)
    private void setRegist(View v) {
        presenter.regist("111", "111", "1");
    }

    @Event(R.id.getcode_btn)
    private void setGetcode_btn(View v) {
        presenter.getCode("1564594066");
    }

    @Override
    public void onShowSuccessRegistView(String user) {
        LogUtil.e("--onShowSuccessRegistView---" + user);

    }

    @Override
    public void onShowFailedRegistView() {
        LogUtil.e("--onShowFailedRegistView---");
    }

    @Override
    public void OnShowSuccessGetCodeView() {
        LogUtil.e("--OnShowSuccessGetCodeView---");
    }

    @Override
    public void OnshowFailGetCodeView() {
        LogUtil.e("--OnshowFailGetCodeView---");
    }

    @Override
    public void startLoading() {
        LogUtil.e("--showLoadingView---");
    }

    @Override
    public void endLoading() {
        LogUtil.e("--dissLoadingView---");
    }


    @Override
    public RegistPresenter initPresenter() {
        return new RegistPresenter();
    }
}
