package com.dmcc.dmcc_crm.customview.center_btn_view;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.dmcc.dmcc_crm.R;
import com.wushange.commsdk.util.ShowToast;

import org.xutils.common.util.LogUtil;

public class MoreWindow extends PopupWindow implements OnClickListener {

    private String TAG = MoreWindow.class.getSimpleName();
    Activity mContext;
    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    private Bitmap mBitmap = null;
    private Bitmap overlay = null;

    private Handler mHandler = new Handler();
    RelativeLayout rootLayout;
    ImageView close, jishi, xixixin;

    public MoreWindow(Activity context) {
        mContext = context;
    }

    public void init() {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        setWidth(mWidth);
        setHeight(mHeight);
    }

    private Bitmap blur() {
        if (null != overlay) {
            return overlay;
        }
        long startMs = System.currentTimeMillis();

        View view = mContext.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        mBitmap = view.getDrawingCache();

        float scaleFactor = 8;//图片缩放比例；
        float radius = 10;//模糊程度
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        overlay = Bitmap.createBitmap((int) (width / scaleFactor), (int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        Log.i(TAG, "blur time is:" + (System.currentTimeMillis() - startMs));
        return overlay;
    }

    private Animation showAnimation1(final View view, int fromY, int toY) {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation go = new TranslateAnimation(0, 0, fromY, toY);
        go.setDuration(300);
        TranslateAnimation go1 = new TranslateAnimation(0, 0, -10, 2);
        go1.setDuration(100);
        go1.setStartOffset(250);
        set.addAnimation(go1);
        set.addAnimation(go);

        set.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
        return set;
    }


    public void showMoreWindow(View anchor, int bottomMargin) {
        rootLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.center_more_window, null);
        setContentView(rootLayout);
        LogUtil.e("原始加号的高度" + anchor.getRootView().getBottom() + anchor.getMeasuredHeight() + " 左侧坐标 " + anchor.getLeft() + " 右侧坐标 " + anchor.getRight() + " 上侧坐标 " + anchor.getTop() + " 下侧坐标 " + anchor.getBottom());
        close = (ImageView) rootLayout.findViewById(R.id.center_window_close);
        jishi = (ImageView) rootLayout.findViewById(R.id.jishi);
        xixixin = (ImageView) rootLayout.findViewById(R.id.xixixin);
        jishi.setOnClickListener(this);
        xixixin.setOnClickListener(this);
        close.setOnClickListener(this);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = bottomMargin;
        params.addRule(RelativeLayout.BELOW, R.id.menu_con);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.topMargin = 90;
        params.bottomMargin = 25;
        if (Build.VERSION.SDK_INT > 21) {
            params.bottomMargin = 25 + statusBarHeight;
        }

        close.setLayoutParams(params);

        showAnimation(rootLayout);
        setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
        setOutsideTouchable(true);
        setFocusable(true);
        LogUtil.e("statusBarHeight=" + statusBarHeight);
        showAtLocation(anchor, Gravity.BOTTOM, 0, statusBarHeight);
    }

    private void hideStatusBar() {
        WindowManager.LayoutParams attrs = mContext.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mContext.getWindow().setAttributes(attrs);
    }

    private void showStatusBar() {
        WindowManager.LayoutParams attrs = mContext.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        mContext.getWindow().setAttributes(attrs);
    }


    private void showAnimation(ViewGroup layout) {
        final RotateAnimation animation = new RotateAnimation(0f, 120f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        close.setAnimation(animation);
        animation.setFillAfter(true);
        animation.setDuration(300);//设置动画持续时间
        animation.startNow();
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            if (child.getId() == R.id.center_window_close) {
                continue;
            }
            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                    fadeAnim.setDuration(300);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(150);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }
            }, i * 50);
        }

    }

    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            if (child.getId() == R.id.center_window_close) {
                continue;
            }
            child.setOnClickListener(this);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                    fadeAnim.setDuration(200);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(100);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                    fadeAnim.addListener(new AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            child.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }, (layout.getChildCount() - i - 1) * 30);

            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    dismiss();
                }
            }, (layout.getChildCount() - i) * 30 + 80);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.center_window_close:
                dissMissPop();
                break;
            case R.id.jishi:
                ShowToast.showToast(mContext, "即使标讯");
                dissMissPop();
                break;
            case R.id.xixixin:
                ShowToast.showToast(mContext, "嘻嘻新");
                dissMissPop();
                break;
            default:
                break;
        }
    }

    private void dissMissPop() {
        if (isShowing()) {
            closeAnimation(rootLayout);
        }
    }


    public void destroy() {
        if (null != overlay) {
            overlay.recycle();
            overlay = null;
            System.gc();
        }
        if (null != mBitmap) {
            mBitmap.recycle();
            mBitmap = null;
            System.gc();
        }
    }

}
