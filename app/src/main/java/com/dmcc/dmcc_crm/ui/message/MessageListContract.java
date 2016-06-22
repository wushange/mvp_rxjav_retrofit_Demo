package com.dmcc.dmcc_crm.ui.message;

import com.dmcc.dmcc_crm.base.BaseView;

import java.util.List;

/**
 * Created by sll on 2016/5/11.
 */
public interface MessageListContract {
    interface MessageListView extends BaseView {
        void OnGetRecordDatas(List<String> datas);
    }

    interface MessageListPresenter {
        void getRecordDatas(final int a);
    }
}
