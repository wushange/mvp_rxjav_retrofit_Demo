package com.dmcc.dmcc_crm.ui.login;

import com.dmcc.dmcc_crm.base.*;
import com.dmcc.dmcc_crm.bean.User;

/**
 * Created by sll on 2016/5/11.
 */
public interface LoginContract {
    interface LoginView extends BaseView {
        void showTest(String s);

        void showUserNameError(String error);

        void showPassWordError(String error);

        void onShowSuccessLoginView(User user);

        void onShowFailedLoginView(int error);

    }

    interface LoginPresenter {
        void login(String username, String password);
    }
}
