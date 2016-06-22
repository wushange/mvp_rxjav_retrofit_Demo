package com.dmcc.dmcc_crm.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.dmcc.dmcc_crm.AppManager;
import com.wushange.commsdk.base.IBaseActivity;

import org.xutils.x;

import java.lang.ref.WeakReference;


/**
 * android  Activity基类
 *
 * @author wushange
 * @version 1.0
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public abstract class MvpBaseSwipeBackActivity<V, T extends BasePresenter<V>> extends BaseSwipeBackActivity_ implements IBaseActivity {
    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = new WeakReference<Activity>(this);
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        setContentView(mContextView);
        x.view().inject(this);
        initP();
        initView(mContextView);
        doBusiness(this);
        AppManager.getAppManager().addActivity(this);

    }

    public abstract T initPresenter();


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeP();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachP();
        AppManager.getAppManager().finishActivity(this);

    }


    private void initP() {
        presenter = initPresenter();
        if (presenter != null)
            presenter.attachView((V) this);
    }

    private void resumeP() {
        if (presenter != null)
            presenter.attachView((V) this);
    }

    private void detachP() {
        if (presenter != null)
            presenter.detachView();
    }

}
