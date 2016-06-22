package com.dmcc.dmcc_crm.ui.regist;

import com.dmcc.dmcc_crm.base.BaseView;

/**
 * Created by sll on 2016/5/11.
 */
public interface RegistContract {
    interface RegistView extends BaseView {
        void OnShowSuccessGetCodeView();

        void OnshowFailGetCodeView();

        void onShowSuccessRegistView(String user);

        void onShowFailedRegistView();


    }

    interface RegistPresenter {
        void getCode(final String phone);

        void regist(final String username, final String password, final String code);
    }
}
