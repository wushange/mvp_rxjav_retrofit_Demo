package com.dmcc.dmcc_crm.ui.project;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.base.MvpBaseFragmentV4;
import com.dmcc.dmcc_crm.bean.Project;
import com.dmcc.dmcc_crm.customview.AppTitle;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;

/**
 * Created by wushange on 2016/05/31.
 */
public class ProjectListFragment extends MvpBaseFragmentV4<ProjectListContract.ProjectListView, ProjectListPresenter> implements ProjectListContract.ProjectListView, RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @ViewInject(R.id.recyclerView)
    private EasyRecyclerView recyclerView;
    private PeojectListAdapter adapter;
    List<Project> mList = new ArrayList<Project>();
    @ViewInject(R.id.apptitle)
    AppTitle appTitle;
    private Handler handler = new Handler();

    private int page = 0;

    @Override
    public int bindLayout() {
        return R.layout.fragment_project_main_view;
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView(View view) {
        appTitle.init(AppTitle.AppTitleStyle.LEFT_TV_RIGHT_BT2);
        appTitle.setLefttext("项目");
        appTitle.setRightBtnClickClisener(R.mipmap.jiahao, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastInCenter("添加");
            }
        });
        appTitle.setRightBtn2ClickClisener(R.mipmap.iv_project_filter_icon, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastInCenter("筛选");
            }
        });

    }


    @Override
    public void doBusiness(Context mContext) {
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapterWithProgress(adapter = new PeojectListAdapter(getActivity()));
        recyclerView.setItemAnimator(new ScaleInBottomAnimator());
        adapter.setMore(R.layout.view_more, this);
        adapter.setNoMore(R.layout.view_nomore);
        adapter.setOnItemLongClickListener(new RecyclerArrayAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemClick(int position) {
                adapter.remove(position);
                return true;
            }
        });
        adapter.setError(R.layout.view_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.resumeMore();
            }
        });
        recyclerView.setRefreshListener(this);
        onRefresh();
    }

    @Override
    public void lazyInitBusiness(Context mContext) {

    }

    @Override
    public void onLoadMore() {
        Log.i("EasyRecyclerView", "onLoadMore");
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.loadMoreData(page);

            }
        }, 1000);
    }

    @Override
    public void onRefresh() {
        page = 0;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                presenter.getProjectList();
                page = 1;
            }
        }, 1000);
    }


    @Override
    public void startLoading() {

    }

    @Override
    public void endLoading() {

    }

    @Override
    public ProjectListPresenter initPresenter() {
        return new ProjectListPresenter();
    }

    @Override
    public void OnGetProjectListSuccessView(List<Project> projects) {
        adapter.addAll(projects);
        page++;
    }

    @Override
    public void OnGetProjectListFailView(String errormsg) {

    }
}
