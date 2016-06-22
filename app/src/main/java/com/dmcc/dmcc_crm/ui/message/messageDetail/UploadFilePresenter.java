package com.dmcc.dmcc_crm.ui.message.messageDetail;

import com.dmcc.dmcc_crm.base.BasePresenter;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Subscriber;

/**
 * p层
 */
public class UploadFilePresenter extends BasePresenter<UploadFileContract.UploadFileStatus> implements UploadFileContract.UploadFilePresenter {

    public UploadFilePresenter() {
    }

    @Override
    public void uploadImg(final RequestBody body, final MultipartBody.Part upFile) {
        mSubscription = apiHelper.updateIcon(body, upFile).subscribe(new Subscriber<Response<String>>() {
            @Override
            public void onStart() {
                super.onStart();
                mView.startLoading();
            }

            @Override
            public void onCompleted() {
                mView.endLoading();
            }

            @Override
            public void onError(Throwable e) {
                mView.OnUploadFail(e.getMessage().toLowerCase());
            }

            @Override
            public void onNext(Response<String> stringResponse) {
                mView.OnUploadSuccess(stringResponse.body().toString());

            }
        });
        mCompositeSubscription.add(mSubscription);

    }


}
