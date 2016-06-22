package com.dmcc.dmcc_crm.ui.customer.myfollow;

import com.dmcc.dmcc_crm.base.BaseView;
import com.dmcc.dmcc_crm.bean.User;

import java.util.List;

/**
 * Created by sll on 2016/5/11.
 */
public interface MyFollowContract {
    interface MyFollowView extends BaseView {
        void OnLoadDataSuccess(List<User> users);
    }

    interface MyFolloePresenter {
        void  getCustomers(final int a);
    }
}
