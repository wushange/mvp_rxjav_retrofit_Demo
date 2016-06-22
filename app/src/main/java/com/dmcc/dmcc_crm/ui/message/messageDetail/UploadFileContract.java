package com.dmcc.dmcc_crm.ui.message.messageDetail;

import com.dmcc.dmcc_crm.base.BaseView;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by sll on 2016/5/11.
 */
public interface UploadFileContract {
    interface UploadFileStatus extends BaseView {

        void OnUploadSuccess(String s);

        void OnUploadFail(String s);
    }

    interface UploadFilePresenter {
      void  uploadImg(final RequestBody body, final MultipartBody.Part upFile);
    }
}
