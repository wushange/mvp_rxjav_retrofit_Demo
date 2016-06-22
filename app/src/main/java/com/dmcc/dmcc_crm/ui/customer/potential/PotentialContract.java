package com.dmcc.dmcc_crm.ui.customer.potential;

import com.dmcc.dmcc_crm.base.BaseView;
import com.dmcc.dmcc_crm.bean.User;

import java.util.List;

/**
 * Created by sll on 2016/5/11.
 */
public interface PotentialContract {
    interface PotentialView extends BaseView {
        void OnLoadDataSuccess(List<User> users);
    }


    interface PotentialPresenter {
        void getCustomers(final int a);
    }
}
