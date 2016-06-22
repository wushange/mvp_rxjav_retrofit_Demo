package com.dmcc.dmcc_crm.ui.project;

import com.dmcc.dmcc_crm.base.BasePresenter;
import com.dmcc.dmcc_crm.bean.Project;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wushange on 2016/06/20.
 */
public class ProjectListPresenter extends BasePresenter<ProjectListContract.ProjectListView> implements ProjectListContract.ProjectPresenter {
    public ProjectListPresenter() {
    }

    @Override
    public void getProjectList() {
        mSubscription = Observable.create(new Observable.OnSubscribe<List<Project>>() {
            @Override
            public void call(Subscriber<? super List<Project>> subscriber) {
                //刷新
                List<Project> aList = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    aList.add(new Project("北京荣成创新" + i, "北京荣成创新科技有限公司" + i));
                }
                subscriber.onStart();
                subscriber.onNext(aList);
                subscriber.onCompleted();

            }
        }).subscribe(new Subscriber<List<Project>>() {

            @Override
            public void onStart() {
                super.onStart();
                mView.startLoading();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.OnGetProjectListFailView("命令不对");
            }

            @Override
            public void onNext(List<Project> members) {
                mView.OnGetProjectListSuccessView(members);
            }
        });
        mCompositeSubscription.add(mSubscription);

    }

    @Override
    public void loadMoreData(final int page) {
        mSubscription = Observable.create(new Observable.OnSubscribe<List<Project>>() {
            @Override
            public void call(Subscriber<? super List<Project>> subscriber) {
                //刷新
                List<Project> aList = new ArrayList<>();
                for (int i = 0; i < page; i++) {
                    aList.add(new Project("北京荣成创新" + page, "北京荣成创新科技有限公司" + i));
                }
                subscriber.onStart();
                subscriber.onNext(aList);
                subscriber.onCompleted();

            }
        }).subscribe(new Subscriber<List<Project>>() {

            @Override
            public void onStart() {
                super.onStart();
                mView.startLoading();
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mView.OnGetProjectListFailView("命令不对");
            }

            @Override
            public void onNext(List<Project> members) {
                mView.OnGetProjectListSuccessView(members);
            }
        });
        mCompositeSubscription.add(mSubscription);

    }
}
