package com.dmcc.dmcc_crm.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmcc.dmcc_crm.R;


/**
 * 使用方法 init(标题类型APPtitleStyle) setTitle(各种类型)
 * <p>
 * 默认都有logo   如果设置返回按钮 就隐藏左侧logo
 *
 * @author wushange
 * @Description
 * @ClassName AppTitle
 * @date 2015-11-23 上午10:19:00
 */
public class AppTitle extends LinearLayout {

    private LayoutInflater mInflater;
    private View mHeader;
    // 左侧返回按钮
    private Button mback;
    private ImageView mHeadLogo;
    private TextView mLeftText;
    // 标题
    private TextView mHeadTitle;
    // 右边文本
    private TextView mHeadRightText;
    // 右侧按钮
    private ImageView mHeadRightBtn;
    private ImageView mHeadRightBtn2;


    public AppTitle(Context context) {
        super(context);
        init(context);
    }


    public AppTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mHeader = mInflater.inflate(R.layout.apptitle_common_bar, null);
        addView(mHeader);
        initViews();

    }

    /**
     * 添加logo资源
     *
     * @param resId
     */
    public void setmHeadLogo(int resId) {
        mHeadLogo.setImageResource(resId);
    }

    /**
     * 添加标题内容
     *
     * @param title
     */
    public void setLefttext(CharSequence title) {
        if (title != null) {
            mLeftText.setText(title);
        } else {
            mLeftText.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 添加标题内容
     *
     * @param title
     */
    public void setTitle(CharSequence title) {
        if (title != null) {
            mHeadTitle.setText(title);
        } else {
            mHeadTitle.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 添加右侧文本内容
     *
     * @param title
     */
    public void setRightText(CharSequence title) {
        if (mHeadRightText != null && title != null) {
            mHeadRightText.setText(title);
        }
    }

    /**
     * 添加返回按钮事件
     *
     * @param clickListener
     */
    public void setBackClickClisener(OnClickListener clickListener) {
        mback.setOnClickListener(clickListener);
    }

    /**
     * 添加右侧文本事件
     *
     * @param clickListener
     */
    public void setRightTextClickClisener(OnClickListener clickListener) {
        mHeadRightText.setOnClickListener(clickListener);
    }

    /**
     * 添加最右侧按钮事件
     *
     * @param resId         资源id
     * @param clickListener 点击事件
     */
    public void setRightBtnClickClisener(int resId, OnClickListener clickListener) {

        mHeadRightBtn.setImageResource(resId);
        mHeadRightBtn.setOnClickListener(clickListener);
    }

    /**
     * 添加右侧按钮2的事件
     *
     * @param resId         资源id
     * @param clickListener 点击事件
     */
    public void setRightBtn2ClickClisener(int resId, OnClickListener clickListener) {

        mHeadRightBtn2.setImageResource(resId);
        mHeadRightBtn2.setOnClickListener(clickListener);
    }

    /**
     * 初始化view
     */
    public void initViews() {
        mback = (Button) findViewById(R.id.back_btn);
        mHeadLogo = (ImageView) findViewByHeaderId(R.id.include_head_logo);
        mLeftText = (TextView) findViewByHeaderId(R.id.include_left_text);
        mHeadTitle = (TextView) findViewByHeaderId(R.id.include_head_title);
        mHeadRightText = (TextView) findViewByHeaderId(R.id.include_right_text);
        mHeadRightBtn = (ImageView) findViewById(R.id.include_right_btn);
        mHeadRightBtn2 = (ImageView) findViewByHeaderId(R.id.include_right_btn_two);
    }

    public View findViewByHeaderId(int id) {
        return mHeader.findViewById(id);
    }

    /**
     * 初始化标题栏style
     *
     * @param style
     */
    public void init(AppTitleStyle style) {
        switch (style) {
            case DEFAULT_TITLE:
                DefaultTitle();
                break;
            case BACK_TITLE:
                BackTitle();
                break;
            case ICON_TITLE:
                LogoTitle();
                break;
            case TITLE_RIGHT_TEXT:
                TitleRightText();
                break;
            case TITLE_RIGHT_BTN:
                TitleRightBtn();
                break;
            case TITLE_RBT1_RBT2:
                TITLE_RBT1_RBT2();
                break;
            case BACK_TITLE_RIGHT_T:
                BackTitleRightT();
                break;
            case BACK_TITLE_RIGTH_BTN:
                BackTitleRightBtn();
                break;
            case ICON_TITLE_RBT1_RBT2:
                ICON_TITLE_RBT1_RBT2();
                break;
            case LEFT_TV_RIGHT_BT1:
                LEFT_TV_RIGHT_BT1();
                break;
            case LEFT_TV_RIGHT_BT2:
                LEFT_TV_RIGHT_BT2();
                break;

        }
    }

    /**
     * 默认只显示标题
     */
    private void DefaultTitle() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.INVISIBLE);
        mback.setVisibility(View.INVISIBLE);
        mHeadTitle.setVisibility(View.VISIBLE);
        mHeadRightText.setVisibility(View.INVISIBLE);
        mHeadRightBtn.setVisibility(INVISIBLE);
        mHeadRightBtn2.setVisibility(INVISIBLE);
    }

    /**
     * 只显示logo和标题
     */
    private void LogoTitle() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.VISIBLE);
        mback.setVisibility(View.INVISIBLE);
        mHeadTitle.setVisibility(View.VISIBLE);
        mHeadRightText.setVisibility(View.INVISIBLE);
        mHeadRightBtn.setVisibility(INVISIBLE);
        mHeadRightBtn2.setVisibility(INVISIBLE);
    }

    /**
     * 只显示返回按钮+标题
     */
    private void BackTitle() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.GONE);
        mback.setVisibility(View.VISIBLE);
        mHeadTitle.setVisibility(View.VISIBLE);
        mHeadRightText.setVisibility(View.INVISIBLE);
        mHeadRightBtn.setVisibility(View.INVISIBLE);
        mHeadRightBtn2.setVisibility(INVISIBLE);
    }


    /**
     * 只显示标题以及右边文本
     */
    private void TitleRightText() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.INVISIBLE);
        mback.setVisibility(View.INVISIBLE);
        mHeadTitle.setVisibility(View.VISIBLE);
        mHeadRightText.setVisibility(View.VISIBLE);
        mHeadRightBtn.setVisibility(View.INVISIBLE);
        mHeadRightBtn2.setVisibility(INVISIBLE);
    }

    /**
     * 只显示标题以及最右面右边按钮1
     */
    private void TitleRightBtn() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.INVISIBLE);
        mback.setVisibility(View.INVISIBLE);
        mHeadTitle.setVisibility(View.VISIBLE);
        mHeadRightText.setVisibility(View.INVISIBLE);
        mHeadRightBtn.setVisibility(View.VISIBLE);
        mHeadRightBtn2.setVisibility(INVISIBLE);
    }


    /**
     * 只显示返回按钮，标题，右侧文字
     */
    private void BackTitleRightT() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.GONE);
        mback.setVisibility(View.VISIBLE);
        mHeadTitle.setVisibility(View.VISIBLE);
        mHeadRightText.setVisibility(View.VISIBLE);
        mHeadRightBtn.setVisibility(View.INVISIBLE);
        mHeadRightBtn2.setVisibility(INVISIBLE);
    }

    /**
     * 只显示返回按钮，标题，右侧按钮1
     */
    private void BackTitleRightBtn() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.GONE);
        mback.setVisibility(View.VISIBLE);
        mHeadTitle.setVisibility(View.VISIBLE);
        mHeadRightText.setVisibility(View.INVISIBLE);
        mHeadRightBtn.setVisibility(View.VISIBLE);
        mHeadRightBtn2.setVisibility(INVISIBLE);
    }

    /**
     * 只显示logo，标题，右侧按钮1,和右侧按钮2
     */
    private void ICON_TITLE_RBT1_RBT2() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.VISIBLE);
        mback.setVisibility(View.INVISIBLE);
        mHeadTitle.setVisibility(View.VISIBLE);
        mHeadRightText.setVisibility(View.INVISIBLE);
        mHeadRightBtn.setVisibility(View.VISIBLE);
        mHeadRightBtn2.setVisibility(VISIBLE);
    }

    /**
     * 只显示 标题，右侧按钮1,和右侧按钮2
     */
    private void TITLE_RBT1_RBT2() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.INVISIBLE);
        mback.setVisibility(View.INVISIBLE);
        mHeadTitle.setVisibility(View.VISIBLE);
        mHeadRightText.setVisibility(View.INVISIBLE);
        mHeadRightBtn.setVisibility(View.VISIBLE);
        mHeadRightBtn2.setVisibility(VISIBLE);
    }

    /**
     * 只显示左侧文字和右侧一个功能按钮
     */
    private void LEFT_TV_RIGHT_BT1() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.GONE);
        mLeftText.setVisibility(VISIBLE);
        mback.setVisibility(View.INVISIBLE);
        mHeadTitle.setVisibility(View.INVISIBLE);
        mHeadRightText.setVisibility(View.INVISIBLE);
        mHeadRightBtn.setVisibility(View.VISIBLE);
        mHeadRightBtn2.setVisibility(INVISIBLE);
    }

    /**
     * 只显示左侧文字和右侧两个功能按钮
     */
    private void LEFT_TV_RIGHT_BT2() {
        mLeftText.setVisibility(INVISIBLE);
        mHeadLogo.setVisibility(View.INVISIBLE);
        mLeftText.setVisibility(VISIBLE);
        mback.setVisibility(View.INVISIBLE);
        mHeadTitle.setVisibility(View.INVISIBLE);
        mHeadRightText.setVisibility(View.INVISIBLE);
        mHeadRightBtn.setVisibility(View.VISIBLE);
        mHeadRightBtn2.setVisibility(VISIBLE);
    }

    public enum AppTitleStyle {


        /**
         * @param BACK_TITLE    :带返回按钮和标题的标题栏
         * @param LEFT_TV_RIGHT_BT1     :左侧文字 和右侧 按钮1
         * @param LEFT_TV_RIGHT_BT2     :左侧文字 和右侧 按钮2
         * @param DEFAULT_TITLE :只有标题的标题栏
         * @param ICON_TITLE    :只有ICON和标题的标题栏
         * @param TITLE_RIGHT_TEXT  :带标题和右侧文字的标题栏
         * @param TITLE_RIGHT_BTN   :带标题和右侧按钮的标题栏
         * @param TITLE_RBT1_RBT2 :显示标题和右侧两个按钮
         * @param BACK_TITLE_RIGHT_T    :返回按钮标题栏和右侧文字都有的标题栏
         * @param BACK_TITLE_RIGTH_BTN  :返回按钮标题栏和右侧按钮都有的标题栏
         * @param ICON_TITLE_RBT1_RBT2  :返回按钮标题栏和右侧按钮2都有的标题栏
         */
        BACK_TITLE, LEFT_TV_RIGHT_BT1, LEFT_TV_RIGHT_BT2, DEFAULT_TITLE, ICON_TITLE, TITLE_RIGHT_TEXT, TITLE_RIGHT_BTN,
        BACK_TITLE_RIGHT_T, BACK_TITLE_RIGTH_BTN, ICON_TITLE_RBT1_RBT2, TITLE_RBT1_RBT2
    }

    public View getmHeader() {
        return mHeader;
    }

    public Button getMback() {
        return mback;
    }

    public ImageView getmHeadLogo() {
        return mHeadLogo;
    }

    public TextView getmLeftText() {
        return mLeftText;
    }

    public TextView getmHeadTitle() {
        return mHeadTitle;
    }

    public TextView getmHeadRightText() {
        return mHeadRightText;
    }

    public ImageView getmHeadRightBtn() {
        return mHeadRightBtn;
    }

    public ImageView getmHeadRightBtn2() {
        return mHeadRightBtn2;
    }

}
