package com.dmcc.dmcc_crm.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.wushange.commsdk.R;
import com.wushange.commsdk.customview.svprogresshud.SVProgressHUD;
import com.wushange.commsdk.util.HideInputUtils;

import java.lang.ref.WeakReference;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 基类Activity 主要实现土司和dialog
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public abstract class BaseActivity_ extends AppCompatActivity {
    boolean autoDissIm = true;//是否自动检测点击屏幕边缘隐藏输入法
    protected WeakReference<Activity> context = null;
    protected View mContextView = null;
    private SVProgressHUD mSVProgressHUD;//hub等待框
    private SweetAlertDialog mSweetAlertDialog;//sweetalertdialog
    private boolean isShow = true;
    private Toast mToast;

    public boolean isAutoDissIm() {
        return autoDissIm;
    }

    public void setAutoDissIm(boolean autoDissIm) {
        this.autoDissIm = autoDissIm;
    }


    protected Activity getContext() {
        if (null != context)
            return context.get();
        else
            return null;
    }


    protected void showToast(CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    protected void EmptyToast() {
        if (mToast == null) {
            mToast = Toast.makeText(this, "不允许为空！", Toast.LENGTH_SHORT);
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
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    protected void showSweetBasic(String str) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(this);
        mSweetAlertDialog.setTitleText(str);
        mSweetAlertDialog.setCancelable(true);
        mSweetAlertDialog.setCanceledOnTouchOutside(true);
        mSweetAlertDialog.show();
    }

    protected void showSweetSub(String str, String subStr) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(this);
        mSweetAlertDialog.setTitleText(str);
        mSweetAlertDialog.setCancelable(true);
        mSweetAlertDialog.setContentText(subStr);
        mSweetAlertDialog.setCanceledOnTouchOutside(true);
        mSweetAlertDialog.show();
    }

    protected void showSweetLoading(boolean cancel) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(getResources().getString(R.string.loading));
        mSweetAlertDialog.show();
        mSweetAlertDialog.setCancelable(cancel);
    }

    protected void showSweetLoading(String str) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(str);
        mSweetAlertDialog.show();
        mSweetAlertDialog.setCancelable(false);
    }

    protected void showSweetLoadingCanCancel(String str) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText(str);
        mSweetAlertDialog.show();
        mSweetAlertDialog.setCancelable(true);
    }

    protected void showSweetSuccess(String title, String content) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        mSweetAlertDialog.setTitleText(title);
        mSweetAlertDialog.setContentText(content);
        mSweetAlertDialog.show();
    }

    protected void showSweetSuccess(String title, String content, SweetAlertDialog.OnSweetClickListener successCallBack) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE);
        mSweetAlertDialog.setTitleText(title);
        mSweetAlertDialog.setContentText(content);
        mSweetAlertDialog.setConfirmClickListener(successCallBack);
        mSweetAlertDialog.show();
    }

    protected void showSweetError(String title, String content) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE);
        mSweetAlertDialog.setTitleText(title);
        mSweetAlertDialog.setContentText(content);
        mSweetAlertDialog.show();
    }


    protected void showSweet2Btn(String title, String content, String canceltext, String confirmText, SweetAlertDialog.OnSweetClickListener cancelCallback, SweetAlertDialog.OnSweetClickListener confirmCallback) {
        if (mSweetAlertDialog != null && mSweetAlertDialog.isShowing())
            mSweetAlertDialog.dismissWithAnimation();
        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
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
        mSweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
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
        runOnUiThread(new Runnable() {
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (autoDissIm == true) {
            if (ev.getAction() == MotionEvent.ACTION_DOWN) {
                View v = getCurrentFocus();
                if (HideInputUtils.isShouldHideInput(v, ev)) {

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return super.dispatchTouchEvent(ev);
            }
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }

        } else {
            return super.dispatchTouchEvent(ev);
        }
        return onTouchEvent(ev);
    }

    public SVProgressHUD getmSVProgressHUD() {
        return mSVProgressHUD;
    }
}
