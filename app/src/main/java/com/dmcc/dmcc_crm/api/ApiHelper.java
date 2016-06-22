package com.dmcc.dmcc_crm.api;

import com.dmcc.dmcc_crm.util.RetrofitUtil;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Sunflower on 2016/1/11.
 */
public class ApiHelper extends RetrofitUtil implements APIService {

    private final int pageSize = 10;

    @Override
    public Observable<Response<String>> loadMovieList(int start, int count) {
        return toSubscribe(getService().loadMovieList(start, count));
    }


    @Override
    public Observable<Response<String>> login(String phone, String password) {
        return toSubscribe(getService().login(phone, password));
    }

    @Override
    public Observable<Response<String>> updateIcon(RequestBody body, MultipartBody.Part upFile) {
        return toSubscribe(getService().updateIcon(body, upFile));
    }


    private static <T> Observable toSubscribe(Observable<T> o) {
        return o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
