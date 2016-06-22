package com.dmcc.dmcc_crm.ui.login;

import android.content.Context;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.base.MvpBaseSwipeBackActivity;
import com.dmcc.dmcc_crm.bean.User;
import com.dmcc.dmcc_crm.customview.AppTitle;
import com.dmcc.dmcc_crm.ui.regist.RegistActivity;
import com.dmcc.dmcc_crm.util.AppUtils;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import rx.Subscription;

/**
 * Created by wushange on 2016/06/01.
 */
public class LoginActivity extends MvpBaseSwipeBackActivity<LoginContract.LoginView, LoginPresenter> implements LoginContract.LoginView {
    @ViewInject(R.id.account)
    private AutoCompleteTextView mAccountView;
    @ViewInject(R.id.password)
    private EditText mPasswordView;
    private Subscription mSubscription;
    @ViewInject(R.id.login_btn)
    Button mLoginView;
    @ViewInject(R.id.go_reg_btn)
    Button mToRegView;
    @ViewInject(R.id.apptitle)
    AppTitle appTitle;

    @Override
    public int bindLayout() {
        return R.layout.activity_login_main_view;
    }

    @Override
    public void initView(View view) {
        appTitle.init(AppTitle.AppTitleStyle.BACK_TITLE);
        appTitle.setTitle("登陆");
        appTitle.setBackClickClisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Event(R.id.login_btn)
    private void setLogin(View v) {
        LogUtil.e("---setLogin----");
        presenter.login(mAccountView.getText().toString(), mPasswordView.getText().toString());

    }

    @Event(R.id.go_reg_btn)
    private void setGo_reg_btn(View v) {
        AppUtils.startAct(getContext(), RegistActivity.class);
    }

    @Override
    public void showTest(String s) {
        LogUtil.e("---" + s);
        mAccountView.setText(s);
    }

    @Override
    public void showUserNameError(String error) {
        mAccountView.setError("账号错误");
    }

    @Override
    public void showPassWordError(String error) {
        mPasswordView.setError("密码错误");
    }

    @Override
    public void onShowSuccessLoginView(User user) {
        LogUtil.e("---onShowSuccessLoginView----" + user.toString());
    }

    @Override
    public void onShowFailedLoginView(int error) {
        LogUtil.e("---onShowFailedLoginView----");
    }

    @Override
    public void startLoading() {
        showSweetLoading(true);
        LogUtil.e("---showLoginView----");
    }


    @Override
    public void endLoading() {
        dissLoading();
        LogUtil.e("---dissLoginView----");
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
