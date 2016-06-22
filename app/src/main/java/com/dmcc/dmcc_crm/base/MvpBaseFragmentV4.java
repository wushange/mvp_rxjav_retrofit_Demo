package com.dmcc.dmcc_crm.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wushange.commsdk.base.IBaseFragment;

import org.xutils.x;

public abstract class MvpBaseFragmentV4<V, T extends BasePresenter<V>> extends BaseFragmentV4_ implements IBaseFragment {
    public T presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "BaseFragmentV4_-->onCreateView()");
        if (null == mContextView) {
            initParms(getArguments());
            View mView = bindView();
            if (null == mView) {
                isFirstLoad = true;
                mContextView = inflater.inflate(bindLayout(), container, false);
            } else {
                mContextView = mView;
            }
            x.view().inject(this, mContextView);
            initP();
            initView(mContextView);
            isPrepared = true;
            doBusiness(getActivity());
            lazyLoad();

        }

        return mContextView;
    }

    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirstLoad) {
            return;
        }
        isFirstLoad = false;

        lazyInitBusiness(getActivity());
    }


    public abstract T initPresenter();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        lazyLoad();
    }

    protected void onInvisible() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(TAG, "BaseFragmentV4_-->onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "BaseFragmentV4_-->onSaveInstanceState()");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        Log.d(TAG, "BaseFragmentV4_-->onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "BaseFragmentV4_-->onResume()");
        super.onResume();
        resumeP();
    }

    @Override
    public void onPause() {
        Log.d(TAG, "BaseFragmentV4_-->onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "BaseFragmentV4_-->onStop()");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "BaseFragmentV4_-->onDestroy()");
        detachP();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.d(TAG, "BaseFragmentV4_-->onDetach()");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mContextView != null && mContextView.getParent() != null) {
            ((ViewGroup) mContextView.getParent()).removeView(mContextView);
        }
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
