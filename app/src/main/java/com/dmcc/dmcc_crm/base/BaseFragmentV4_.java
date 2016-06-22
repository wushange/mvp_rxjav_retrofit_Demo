package com.dmcc.dmcc_crm.base;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.wushange.commsdk.R;
import com.wushange.commsdk.base.IBaseFragment;
import com.wushange.commsdk.customview.svprogresshud.SVProgressHUD;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 基类Fragment 主要实现土司和dialog
 */
public abstract class BaseFragmentV4_ extends Fragment implements IBaseFragment {
    protected View mContextView = null;
    protected Activity mContext = null;
    protected final String TAG = this.getClass().getSimpleName();
    private String title;
    private int iconId;
    protected boolean isVisible;
    protected boolean isPrepared;
    protected boolean isFirstLoad = true;
    private SVProgressHUD mSVProgressHUD;
    private SweetAlertDialog mSweetAlertDialog;

    private boolean isShow = true;
    private Toast mToast;

    public Activity getContext() {
        return getActivity();
    }

    public void initIconWithText(String text, int iconId) {
        this.title = text;
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;

    }

    public int getIconId() {
        return iconId;
    }


    protected void showToast(CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    protected void EmptyToast() {
        if (mToast == null) {
            mToast = Toast.makeText(getContext(), "不允许为空！", Toast.LENGTH_SHORT);
        } else {
            mToast.setText("不允许为空！");
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    protected void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

    protected void showToastInCenter(String msg) {
        Toast toast = Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    protected void showSweetBasic(String str) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext());
        mSweetAlertDialog.setTitleText(str);
        mSweetAlertDialog.setCancelable(true);
        mSweetAlertDialog.setCanceledOnTouchOutside(true);
        mSweetAlertDialog.show();
    }

    protected void showSweetSub(String str, String subStr) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext());
        mSweetAlertDialog.setTitleText(str);
        mSweetAlertDialog.setCancelable(true);
        mSweetAlertDialog.setContentText(subStr);
        mSweetAlertDialog.setCanceledOnTouchOutside(true);
        mSweetAlertDialog.show();
    }

    protected void showSweetLoading(boolean cancel) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getResources().getString(R.string.loading));
        mSweetAlertDialog.show();
        mSweetAlertDialog.setCancelable(cancel);
    }

    protected void showSweetLoading(String str) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(str);
        mSweetAlertDialog.show();
        mSweetAlertDialog.setCancelable(false);
    }

    protected void showSweetLoadingCanCancel(String str) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(str);
        mSweetAlertDialog.show();
        mSweetAlertDialog.setCancelable(true);
    }

    protected void showSweetSuccess(String title, String content) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
        mSweetAlertDialog.setTitleText(title);
        mSweetAlertDialog.setContentText(content);
        mSweetAlertDialog.show();
    }

    protected void showSweetSuccess(String title, String content, SweetAlertDialog.OnSweetClickListener successCallBack) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.SUCCESS_TYPE);
        mSweetAlertDialog.setTitleText(title);
        mSweetAlertDialog.setContentText(content);
        mSweetAlertDialog.setConfirmClickListener(successCallBack);
        mSweetAlertDialog.show();
    }

    protected void showSweetError(String title, String content) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.ERROR_TYPE);
        mSweetAlertDialog.setTitleText(title);
        mSweetAlertDialog.setContentText(content);
        mSweetAlertDialog.show();
    }


    protected void showSweet2Btn(String title, String content, String canceltext, String confirmText, SweetAlertDialog.OnSweetClickListener cancelCallback, SweetAlertDialog.OnSweetClickListener confirmCallback) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE);
        mSweetAlertDialog.setTitleText(title);
        mSweetAlertDialog.setContentText(content);
        mSweetAlertDialog.setCancelText(canceltext);
        mSweetAlertDialog.setConfirmText(confirmText);
        mSweetAlertDialog.showCancelButton(true);
        mSweetAlertDialog.setCancelClickListener(cancelCallback);
        mSweetAlertDialog.setConfirmClickListener(confirmCallback);
        mSweetAlertDialog.show();
    }

    protected void showSweetCustom(String title, String content, int resId) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        mSweetAlertDialog.setTitleText(title);
        mSweetAlertDialog.setContentText(content);
        mSweetAlertDialog.setCustomImage(resId);
        mSweetAlertDialog.show();
    }


    protected void showLoading() {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus("Wait...");

    }

    protected void showLoading(String text) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatus(text);

    }

    protected void showLoadingCanCancelable() {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatusCanCancelable("Wait...");
    }

    protected void showLoadingCanCancelable(String text) {
        mSVProgressHUD = new SVProgressHUD(getContext());
        mSVProgressHUD.showWithStatusCanCancelable(text);
    }

    protected void dissLoading() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mSVProgressHUD != null) {
                    mSVProgressHUD.dismiss();
                }
                if (mSweetAlertDialog != null) {
                    mSweetAlertDialog.dismissWithAnimation();
                }
            }
        });

    }


    public SVProgressHUD getmSVProgressHUD() {
        return mSVProgressHUD;
    }
}
