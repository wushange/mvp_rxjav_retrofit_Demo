package com.dmcc.dmcc_crm.util;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by wushange on 2016/06/01.
 */
public class RequestUtils {
    public static void getWithAuthContext(RequestParams requestParams, Callback.CommonCallback<String> callback) {
        requestParams.addBodyParameter("authContext", "");
        x.http().get(requestParams, callback);
    }

    public static void postWithAuthContext(RequestParams requestParams, Callback.CommonCallback<String> callback) {
        requestParams.addBodyParameter("authContext", "");
        x.http().post(requestParams, callback);
    }

    public static void get(RequestParams requestParams, Callback.CommonCallback<String> callback) {
        x.http().get(requestParams, callback);
    }

    public static void post(RequestParams requestParams, Callback.CommonCallback<String> callback) {
        x.http().post(requestParams, callback);
    }
}
