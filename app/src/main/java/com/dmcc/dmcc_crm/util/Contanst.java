package com.dmcc.dmcc_crm.util;


public class Contanst {
    /**
     * 服务器地址
     */

    private static final String API_HOST = "https://api.douban.com/v2/movie";
//    private static final String API_HOST = "http://192.168.31.209/file";
    public static final String SERVER = API_HOST + "/";
    public static final String HOST = "HOST";
    public static final String PORT = "PORT";
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    /**
     * 项目根SD卡目录
     **/
    public static final String PROJECT_ROOT = "CRM";
    public static final String SDPATH = SDCardUtils.getSDCardPath() + "/"
            + PROJECT_ROOT + "/";
    public static String Cache_Root_Dir;

    public static final String CACHE_DIR = "http_cache_dir";
    public static final String SHAREROOT = "CRM";// Share根名称
    /**
     * 是否是第一次进入
     **/
    public static final String ISFIRST = "IS_FIRST";// 是否是第一次进入
    /**
     * 自动登陆
     **/
    public static final String AUTO_LOGIN = "AUTO_LOGIN";// 是否是第一次进入
    /**
     * 登录的帐号
     */
    public static String PS_KEY_LOGIN_ACCOUNT = "PS_KEY_LOGIN_ACCOUNT";

    /**
     * 登录的密码
     */
    public static String PS_KEY_LOGIN_PWD = "PS_KEY_IS_AUTO_PWD";
    /**
     * 是否自动登录
     */
    public static String PS_KEY_IS_AUTO_LOGIN = "PS_KEY_IS_AUTO_LOGIN";
    /**
     * 是否记住密码
     */
    public static String PS_KEY_IS_REMEMBER_PWD = "PS_KEY_IS_REMEMBER_PWD";
    /**
     * 是否推送
     */
    public static String PS_KEY_IS_PUSH_MSG = "PS_KEY_IS_PUSH_MSG";
    /**
     * 是否提醒
     */
    public static String PS_KEY_IS_REMIND = "PS_KEY_IS_REMIND";


    public static final String LOCAL_ROOT_DIR = "/ll_dir/";

    /**
     * sdcard + LOCAL_ROOT_DIR + User.mobile;
     */
    public static String LOCAL_DIR = null;


}
