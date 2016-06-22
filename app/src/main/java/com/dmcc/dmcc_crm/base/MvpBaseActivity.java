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


@TargetApi(Build.VERSION_CODES.KITKAT)
public abstract class MvpBaseActivity<V, T extends BasePresenter<V>> extends BaseActivity_ implements IBaseActivity {
    boolean autoDissIm = true;
    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContextView = LayoutInflater.from(this).inflate(bindLayout(), null);
        setContentView(mContextView);
        x.view().inject(this);
        initP();
        context = new WeakReference<Activity>(this);
        initView(mContextView);
        doBusiness(this);
        AppManager.getAppManager().addActivity(this);

    }


    public abstract T initPresenter();

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
