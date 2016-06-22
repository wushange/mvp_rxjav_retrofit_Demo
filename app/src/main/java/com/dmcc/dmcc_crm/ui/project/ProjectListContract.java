package com.dmcc.dmcc_crm.ui.project;

import com.dmcc.dmcc_crm.base.BaseView;
import com.dmcc.dmcc_crm.bean.Project;

import java.util.List;

/**
 * Created by sll on 2016/5/11.
 */
public interface ProjectListContract {
    interface ProjectListView extends BaseView {
        void OnGetProjectListSuccessView(List<Project> projects);

        void OnGetProjectListFailView(String errormsg);
    }

    interface ProjectPresenter {
        void getProjectList();

        void loadMoreData(final int page);
    }
}
