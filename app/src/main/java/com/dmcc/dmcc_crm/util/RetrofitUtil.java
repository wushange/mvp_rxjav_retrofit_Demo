package com.dmcc.dmcc_crm.util;

import com.dmcc.dmcc_crm.api.APIService;
import com.wushange.converterfastjson.FastjsonConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Sunflower on 2015/11/4.
 */
public class RetrofitUtil {

    private static APIService service;
    private static Retrofit retrofit;

    public static APIService getService() {
        if (service == null) {
            service = getRetrofit().create(APIService.class);
        }
        return service;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            File cacheFile = new File(Contanst.Cache_Root_Dir, Contanst.CACHE_DIR);
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
            OkHttpClient client = new OkHttpClient.Builder()
                    .cache(cache).connectTimeout(15, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(Contanst.SERVER)
                    .addConverterFactory(FastjsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
