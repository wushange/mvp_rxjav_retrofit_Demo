package com.dmcc.dmcc_crm.ui.login;

import android.text.TextUtils;

import com.dmcc.dmcc_crm.base.BasePresenter;

import retrofit2.Response;
import rx.Subscriber;

/**
 * p层
 */
public class LoginPresenter extends BasePresenter<LoginContract.LoginView> implements LoginContract.LoginPresenter {

    /**
     * 登陆界面构造器
     */
    public LoginPresenter() {
    }


    @Override
    public void login(String username, String password) {
        if (TextUtils.isEmpty(username)) {
            mView.showUserNameError("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mView.showPassWordError("请输入密码");
            return;
        }
        mView.startLoading();
        mSubscription = apiHelper.loadMovieList(0, 10).subscribe(new Subscriber<Response<String>>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onCompleted() {
                mView.endLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.onShowFailedLoginView(1);

            }

            @Override
            public void onNext(Response<String> stringResponse) {
                mView.showTest(stringResponse.body().toLowerCase());

            }
        });

        mCompositeSubscription.add(mSubscription);
    }
}
