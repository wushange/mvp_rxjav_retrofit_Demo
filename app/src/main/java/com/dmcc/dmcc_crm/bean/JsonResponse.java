package com.dmcc.dmcc_crm.bean;

/**
 * Created by aresa on 2016/5/30.
 */
public class JsonResponse<T> {

    public int resultCode;
    public String message;
    public String totalCount;
    public T data;
    public String ext;
}
