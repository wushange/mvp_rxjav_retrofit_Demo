package com.dmcc.dmcc_crm.ui.me;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dmcc.dmcc_crm.base.BasePresenter;
import com.dmcc.dmcc_crm.base.MvpBaseFragmentV4;
import com.dmcc.dmcc_crm.ui.login.LoginActivity;
import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.util.AppUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by wushange on 2016/05/31.
 */
public class MeFragment extends MvpBaseFragmentV4 {
    @ViewInject(R.id.me)
    Button mMeView;

    @Override
    public int bindLayout() {
        return R.layout.fragment_me_main_view;
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
        LogUtil.e("initView");
    }

    @Override
    public void doBusiness(Context mContext) {
        LogUtil.e("doBusiness");
    }

    @Override
    public void lazyInitBusiness(Context mContext) {
        LogUtil.e("lazyInitBusiness");

    }

    @Event(R.id.me)
    private void setmMeView(View v) {
        AppUtils.startAct(getContext(), LoginActivity.class);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
