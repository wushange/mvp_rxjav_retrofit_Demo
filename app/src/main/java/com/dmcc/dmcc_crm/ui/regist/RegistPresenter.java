package com.dmcc.dmcc_crm.ui.regist;

import com.dmcc.dmcc_crm.base.BasePresenter;

import org.xutils.common.util.LogUtil;

/**
 * Created by wushange on 2016/06/15.
 */
public class RegistPresenter extends BasePresenter<RegistContract.RegistView> implements RegistContract.RegistPresenter {

    public RegistPresenter() {
    }

    @Override
    public void getCode(final String phone) {
        mView.startLoading();
        LogUtil.e("---模拟子线程耗时操作----");
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mView.OnShowSuccessGetCodeView();
                LogUtil.e("---  模拟获取验证码成功----");
            }
        }.start();
    }

    @Override
    public void regist(final String username, final String password, final String code) {
        LogUtil.e("---模拟子线程耗时操作----");
        mView.startLoading();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mView.onShowSuccessRegistView("注册成功");
                LogUtil.e("---  模拟注册成功----");
            }
        }.start();
    }

}
