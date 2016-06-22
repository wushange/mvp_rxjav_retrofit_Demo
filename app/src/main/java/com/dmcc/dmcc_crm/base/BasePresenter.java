package com.dmcc.dmcc_crm.base;

import com.dmcc.dmcc_crm.api.ApiHelper;

import org.xutils.common.util.LogUtil;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * p层
 */
public abstract class BasePresenter<T> {
    public T mView;//View
    protected Subscription mSubscription;
    protected CompositeSubscription mCompositeSubscription;//使用compositesubcription 管理Subcription
    protected ApiHelper apiHelper;

    public void attachView(T view) {
        LogUtil.e("");
        this.mView = view;
        if (apiHelper == null)
            apiHelper = new ApiHelper();
        if (mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();
    }


    public void detachView() {
        mCompositeSubscription.unsubscribe();//取消订阅
        mView = null;
    }

}
