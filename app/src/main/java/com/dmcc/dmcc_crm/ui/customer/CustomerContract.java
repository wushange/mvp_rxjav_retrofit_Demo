package com.dmcc.dmcc_crm.ui.customer;

import com.dmcc.dmcc_crm.base.BaseView;
import com.dmcc.dmcc_crm.bean.User;

import java.util.List;

/**
 * Created by sll on 2016/5/11.
 */
public interface CustomerContract {
    interface CustomerView extends BaseView {
        void OnGetCustomersView(List<User> users);

        void OnGetCustomersErrorView(String errormsg);

    }

    interface CustomerPresenter {
        void getCustomers(final int a);
    }
}
