package com.dmcc.dmcc_crm;

import android.app.Application;
import android.content.Context;

import com.dmcc.dmcc_crm.util.UILImageLoader;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.leakcanary.LeakCanary;

import org.xutils.x;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * Created by wushange on 2016/06/01.
 */
public class APP extends Application {

    private static APP INSTANCE;
    /**
     * 对外提供整个应用生命周期的Context
     **/
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        initImageLoader();
        initGallyFinal();
        LeakCanary.install(this);
    }

    public static APP getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new APP();
        }
        return INSTANCE;
    }

    /**
     * 对外提供Application Context
     *
     * @return
     */
    public static Context getContext() {
        return context;
    }

    void initGallyFinal() {
        // 建议在application中配置
        // 设置主题 //设置主题
        ThemeConfig theme = new ThemeConfig.Builder().setTitleBarBgColor(getResources().getColor(R.color.appThemeColor)).build();
        // 配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true).setEnableEdit(false)
                .setEnableCrop(false).setEnableRotate(false)
                .setCropSquare(false).setEnablePreview(true).build();
        CoreConfig coreConfig = new CoreConfig.Builder(this,
                new UILImageLoader(), theme).setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

    private void initImageLoader() {
        // This configuration tuning is custom. You can tune every option, you
        // may tune some of them,
        // or you can create default configuration by
        // ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
                getApplicationContext());
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }


    public static void checkPermissionOnlyDendied(Context context, PermissionListener listener, String deniedMessage, String... permissions) {
        new TedPermission(context)
                .setPermissionListener(listener)
                .setDeniedMessage(deniedMessage)
                .setPermissions(permissions)
                .check();

    }

    public static void checkPermission(Context context, PermissionListener listener, String... permissions) {
        new TedPermission(context)
                .setPermissionListener(listener)
                .setPermissions(permissions)
                .check();

    }

    public static void checkPermissionShouldShow(Context context, PermissionListener listener, String rationnaleMsg,
                                                 String deniedMessage, String... permissions) {
        new TedPermission(context)
                .setPermissionListener(listener)
                .setRationaleMessage(rationnaleMsg)
                .setRationaleConfirmText("好的")
                .setDeniedMessage(deniedMessage)
                .setDeniedCloseButtonText("不用了")
                .setGotoSettingButtonText("去设置")
                .setPermissions(permissions)
                .check();
    }

    public static void checkPermissionShouldShow(Context context, PermissionListener listener, String rationnaleMsg, String rationaleBtnMsg,
                                                 String deniedMessage, String deniedBtnCloseMsg, String deniedBtnSettingMsg, String... permissions) {
        new TedPermission(context)
                .setPermissionListener(listener)
                .setRationaleMessage(rationnaleMsg)
                .setRationaleConfirmText(rationaleBtnMsg)
                .setDeniedMessage(deniedMessage)
                .setDeniedCloseButtonText(deniedBtnCloseMsg)
                .setGotoSettingButtonText(deniedBtnSettingMsg)
                .setPermissions(permissions)
                .check();
    }
}
