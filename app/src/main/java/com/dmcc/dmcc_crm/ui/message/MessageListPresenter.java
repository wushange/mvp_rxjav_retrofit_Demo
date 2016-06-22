package com.dmcc.dmcc_crm.ui.message;

import com.dmcc.dmcc_crm.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by wushange on 2016/06/15.
 */
public class MessageListPresenter extends BasePresenter<MessageListContract.MessageListView> implements MessageListContract.MessageListPresenter {


    public MessageListPresenter() {
    }

    @Override
    public void getRecordDatas(final int a) {
        mSubscription = Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                List<String> mList = new ArrayList<String>();
                for (int i = 0; i < 10; i++) {
                    mList.add("苏宁易购" + i + "号店" + a);
                }
                subscriber.onStart();
                subscriber.onNext(mList);
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<List<String>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<String> mList) {
                mView.OnGetRecordDatas(mList);
            }
        });


        mCompositeSubscription.add(mSubscription);
    }

}
