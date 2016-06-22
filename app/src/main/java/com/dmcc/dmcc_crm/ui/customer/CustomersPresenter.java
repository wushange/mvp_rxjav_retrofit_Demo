package com.dmcc.dmcc_crm.ui.customer;

import com.dmcc.dmcc_crm.base.BasePresenter;
import com.dmcc.dmcc_crm.bean.User;
import com.dmcc.dmcc_crm.util.PinyinComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wushange on 2016/06/15.
 */
public class CustomersPresenter extends BasePresenter<CustomerContract.CustomerView> implements CustomerContract.CustomerPresenter {

    public CustomersPresenter() {
    }

    public void getCustomers(final int a) {
        mView.startLoading();
        mSubscription =
                Observable.create(new Observable.OnSubscribe<List<User>>() {
                    @Override
                    public void call(Subscriber<? super List<User>> subscriber) {
                        char[] chartable = {'啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈',
                                '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然', '撒', '塌', '塌',
                                '塌', '挖', '昔', '压', '匝',};
                        if (a == 6) {
                            List<User> members = new ArrayList<User>();
                            for (int i = 0; i < 20; i++) {
                                User user = new User();
                                user.setUserName(chartable[(int) (Math.random() * 26)] + "模拟客户");
                                members.add(user);
                            }
                            Collections.sort(members,
                                    new PinyinComparator());
                            subscriber.onStart();
                            subscriber.onNext(members);
                            subscriber.onCompleted();
                        } else {
                            subscriber.onError(null);
                        }

                    }
                }).subscribe(new Subscriber<List<User>>() {

                    @Override
                    public void onStart() {
                        super.onStart();

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.OnGetCustomersErrorView("命令不对");
                    }

                    @Override
                    public void onNext(List<User> members) {
                        mView.OnGetCustomersView(members);
                    }
                });
        mCompositeSubscription.add(mSubscription);

    }


}
