package com.dmcc.dmcc_crm.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.dmcc.dmcc_crm.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.wushange.commsdk.util.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * 图片加载工具类
 *
 * @version 1.0
 */
public abstract class ToolImage {

    private static ImageLoader imageLoader;
    // 图片在SD卡中的缓存路径
    private static final String IMAGE_PATH = Environment
            .getExternalStorageDirectory().toString()
            + File.separator
            + "YINGYU" + File.separator + "Images" + File.separator;
    // 相册的RequestCode
    public static final int INTENT_REQUEST_CODE_ALBUM = 0;
    // 照相的RequestCode
    public static final int INTENT_REQUEST_CODE_CAMERA = 1;
    // 裁剪照片的RequestCode
    public static final int INTENT_REQUEST_CODE_CROP = 2;
    // 滤镜图片的RequestCode
    public static final int INTENT_REQUEST_CODE_FLITER = 3;

    /**
     * 图片压缩
     *
     * @param srcPath
     * @return
     */
    public static Bitmap getCompressImage(String srcPath, int maxKb) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);// 此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;// 这里设置高度为800f
        float ww = 480f;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        return compressImage(bitmap, maxKb);// 压缩好比例大小后再进行质量压缩
    }

    public static Bitmap compressImage(Bitmap image, int maxKb) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > maxKb) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 初始化ImageLoader
     */
    public static ImageLoader initImageLoader(Context context) {
        // 获取到缓存的目录地址
        File cacheDir = StorageUtils.getOwnCacheDirectory(context,
                "ImageLoader/Cache");
        // 创建配置ImageLoader,可以设定在Application，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .memoryCacheExtraOptions(480, 800)
                // 缓存文件的最大长宽
                // Can slow ImageLoader, use it carefully (Better don't use
                // it)设置缓存的详细信息，最好不要设置这个
                // .discCacheExtraOptions(480, 800, CompressFormat.JPEG,
                // 75,null)
                .threadPoolSize(3)
                // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                // 线程优先级
                /*
				 * When you display an image in a small ImageView and later you
				 * try to display this image (from identical URI) in a larger
				 * ImageView so decoded image of bigger size will be cached in
				 * memory as a previous decoded image of smaller size. So the
				 * default behavior is to allow to cache multiple sizes of one
				 * image in memory. You can deny it by calling this method: so
				 * when some image will be cached in memory then previous cached
				 * size of this image (if it exists) will be removed from memory
				 * cache before.
				 */
                // .denyCacheImageMultipleSizesInMemory()
                // You can pass your own memory cache implementation
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(20 * 1024 * 1024)
                // 硬盘缓存50MB
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator())
                // 将保存的时候的URI名称用MD5
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)// 缓存的File数量
                .discCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                // .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                // .imageDownloader(new BaseImageDownloader(context, 5 * 1000,30
                // * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .build();

        // 全局初始化此配置
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        return imageLoader;
    }

    /**
     * 获取圆角 带默认头像的配置
     *
     * @return
     */
    public static DisplayImageOptions getRaidoOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc()
                .showStubImage(R.drawable.ic_launcher)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnLoading(R.drawable.ic_launcher)
                .cacheInMemory()
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc()
                // 设置图片的解码类型
                .bitmapConfig(Config.RGB_565)
                // 设置图片下载前的延迟
                /**
                 *
                 * 设置图片缩放方式： EXACTLY :图像将完全按比例缩小到目标大小
                 * EXACTLY_STRETCHED:图片会缩放到目标大小完全 IN_SAMPLE_INT:图像将被二次采样的整数倍
                 * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小 NONE:图片不会调整
                 ***/
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .delayBeforeLoading(100)
                .displayer(new RoundedBitmapDisplayer(10)).build();
        return options;
    }

    /**
     * 获取圆角
     *
     * @param defualtid 自定义设置默认图片
     * @return
     */
    public static DisplayImageOptions getRaidoOptions(int defualtid) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showImageForEmptyUri(defualtid)
                .showStubImage(defualtid)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(defualtid)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(defualtid)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory()
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc()
                // 设置图片的解码类型
                .bitmapConfig(Config.RGB_565)
                // 设置图片下载前的延迟
                /**
                 *
                 * 设置图片缩放方式： EXACTLY :图像将完全按比例缩小到目标大小
                 * EXACTLY_STRETCHED:图片会缩放到目标大小完全 IN_SAMPLE_INT:图像将被二次采样的整数倍
                 * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小 NONE:图片不会调整
                 ***/
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .delayBeforeLoading(100)
                .displayer(new RoundedBitmapDisplayer(10)).build();
        return options;
    }

    /**
     * 获取渐现显示选项
     *
     * @param loadingImageResId 加载期间显示的图片
     * @param errorImageResid   加载错误时显示的图片
     * @param emptyImageResId   空图片或者解析图片出错时显示的图片
     * @return
     */
    public static DisplayImageOptions getFadeOptions(int loadingImageResId,
                                                     int errorImageResid, int emptyImageResId) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showStubImage(loadingImageResId)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(errorImageResid)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(emptyImageResId)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory()
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc()
                /**
                 * 设置图片缩放方式： EXACTLY :图像将完全按比例缩小到目标大小
                 * EXACTLY_STRETCHED:图片会缩放到目标大小完全 IN_SAMPLE_INT:图像将被二次采样的整数倍
                 * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小 NONE:图片不会调整
                 ***/
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Config.RGB_565)
                // 设置图片下载前的延迟
                .delayBeforeLoading(100)
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)

                /**
                 * 图片显示方式： RoundedBitmapDisplayer（int roundPixels）设置圆角图片
                 * FakeBitmapDisplayer（）这个类什么都没做 FadeInBitmapDisplayer（int
                 * durationMillis）设置图片渐显的时间 　　　　
                 * *　SimpleBitmapDisplayer()正常显示一张图片
                 **/
                .displayer(new FadeInBitmapDisplayer(1 * 1000))// 渐显--设置图片渐显的时间
                .build();
        return options;
    }

    public static DisplayImageOptions getFadeOptions(int loadingImageResId) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置图片在下载期间显示的图片
                .showStubImage(loadingImageResId)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(loadingImageResId)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(loadingImageResId)
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory()
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc()
                /**
                 * 设置图片缩放方式： EXACTLY :图像将完全按比例缩小到目标大小
                 * EXACTLY_STRETCHED:图片会缩放到目标大小完全 IN_SAMPLE_INT:图像将被二次采样的整数倍
                 * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小 NONE:图片不会调整
                 ***/
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Config.RGB_565)
                // 设置图片下载前的延迟
                .delayBeforeLoading(100)
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)

                /**
                 * 图片显示方式： RoundedBitmapDisplayer（int roundPixels）设置圆角图片
                 * FakeBitmapDisplayer（）这个类什么都没做 FadeInBitmapDisplayer（int
                 * durationMillis）设置图片渐显的时间 　　　　
                 * *　SimpleBitmapDisplayer()正常显示一张图片
                 **/
                .displayer(new FadeInBitmapDisplayer(1 * 1000))// 渐显--设置图片渐显的时间
                .build();
        return options;
    }

    /**
     * 营语圈加载图片配置信息
     *
     * @return
     */
    public static DisplayImageOptions getYingyuCiecleImageOpt() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory().cacheOnDisc()
                .showStubImage(R.drawable.ic_launcher)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)
                // 设置图片Uri为空或是错误的时候显示的图片ic_launcher
                .showImageForEmptyUri(R.drawable.ic_launcher)
                /**
                 * 设置图片缩放方式： EXACTLY :图像将完全按比例缩小到目标大小
                 * EXACTLY_STRETCHED:图片会缩放到目标大小完全 IN_SAMPLE_INT:图像将被二次采样的整数倍
                 * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小 NONE:图片不会调整
                 ***/
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Config.RGB_565)
                // 设置图片下载前的延迟
                .delayBeforeLoading(100)
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)

                /**
                 * 图片显示方式： RoundedBitmapDisplayer（int roundPixels）设置圆角图片
                 * FakeBitmapDisplayer（）这个类什么都没做 FadeInBitmapDisplayer（int
                 * durationMillis）设置图片渐显的时间 　　　　
                 * *　SimpleBitmapDisplayer()正常显示一张图片
                 **/
                .displayer(new SimpleBitmapDisplayer())// 渐显--设置图片渐显的时间
                .build();
        return options;
    }

    /**
     * 一般配置信息 （图片本地缓存） 渐显
     *
     * @return
     */
    public static DisplayImageOptions getFadeOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory()
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc().showStubImage(R.drawable.ic_launcher)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)
                /**
                 * 设置图片缩放方式： EXACTLY :图像将完全按比例缩小到目标大小
                 * EXACTLY_STRETCHED:图片会缩放到目标大小完全 IN_SAMPLE_INT:图像将被二次采样的整数倍
                 * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小 NONE:图片不会调整
                 ***/
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Config.RGB_565)
                // 设置图片下载前的延迟
                .delayBeforeLoading(100)
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)

                /**
                 * 图片显示方式： RoundedBitmapDisplayer（int roundPixels）设置圆角图片
                 * FakeBitmapDisplayer（）这个类什么都没做 FadeInBitmapDisplayer（int
                 * durationMillis）设置图片渐显的时间 　　　　
                 * *　SimpleBitmapDisplayer()正常显示一张图片
                 **/
                .displayer(new SimpleBitmapDisplayer())// 渐显--设置图片渐显的时间
                .build();
        return options;
    }

    /**
     * 获取渐现显示选项
     * <p/>
     * 加载期间显示的图片
     * 加载错误时显示的图片
     * 空图片或者解析图片出错时显示的图片
     *
     * @return
     */
    public static DisplayImageOptions getFadeOptionsUser() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                // 设置下载的图片是否缓存在内存中
                .cacheInMemory()
                // 设置下载的图片是否缓存在SD卡中
                .cacheOnDisc().showStubImage(R.drawable.ic_launcher)
                // 设置图片加载/解码过程中错误时候显示的图片
                .showImageOnFail(R.drawable.ic_launcher)
                // 设置图片Uri为空或是错误的时候显示的图片
                .showImageForEmptyUri(R.drawable.ic_launcher)
                /**
                 * 设置图片缩放方式： EXACTLY :图像将完全按比例缩小到目标大小
                 * EXACTLY_STRETCHED:图片会缩放到目标大小完全 IN_SAMPLE_INT:图像将被二次采样的整数倍
                 * IN_SAMPLE_POWER_OF_2:图片将降低2倍，直到下一减少步骤，使图像更小的目标大小 NONE:图片不会调整
                 ***/
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                // 设置图片的解码类型
                .bitmapConfig(Config.RGB_565)
                // 设置图片下载前的延迟
                .delayBeforeLoading(100)
                // delayInMillis为你设置的延迟时间
                // 设置图片加入缓存前，对bitmap进行设置
                // .preProcessor(BitmapProcessor preProcessor)

                /**
                 * 图片显示方式： RoundedBitmapDisplayer（int roundPixels）设置圆角图片
                 * FakeBitmapDisplayer（）这个类什么都没做 FadeInBitmapDisplayer（int
                 * durationMillis）设置图片渐显的时间 　　　　
                 * *　SimpleBitmapDisplayer()正常显示一张图片
                 **/
                .displayer(new SimpleBitmapDisplayer())// 渐显--设置图片渐显的时间
                .build();
        return options;
    }

    /**
     * 获取默认显示配置选项
     */
    public static DisplayImageOptions getDefaultOptions() {
        // 设置图片Uri为空或是错误的时候显示的图片
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_launcher).build();
        return options;
    }

    /**
     * 从内存卡中异步加载本地图片
     *
     * @param uri
     * @param imageView
     */
    public static void displayFromSDCard(Context context, String uri,
                                         ImageView imageView) {
        // String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
        initImageLoader(context).displayImage("file://" + uri, imageView);
    }

    /**
     * 从assets文件夹中异步加载图片
     *
     * @param imageName 图片名称，带后缀的，例如：1.png
     * @param imageView
     */
    public static void dispalyFromAssets(Context context, String imageName,
                                         ImageView imageView) {
        // String imageUri = "assets://image.png"; // from assets
        initImageLoader(context).displayImage("assets://" + imageName,
                imageView);
    }

    /**
     * 从drawable中异步加载本地图片
     *
     * @param imageId
     * @param imageView
     */
    public static void displayFromDrawable(Context context, int imageId,
                                           ImageView imageView) {
        // String imageUri = "drawable://" + R.drawable.image; // from drawables
        // (only images, non-9patch)
        initImageLoader(context).displayImage("drawable://" + imageId,
                imageView);
    }

    /**
     * 从内容提提供者中抓取图片
     */
    public static void displayFromContent(Context context, String uri,
                                          ImageView imageView) {
        // String imageUri = "content://media/external/audio/albumart/13"; //
        // from content provider
        initImageLoader(context).displayImage("content://" + uri, imageView);
    }

    /**
     * 清除缓存
     */
    public static void clearCache() {
        imageLoader.clearMemoryCache();
        imageLoader.clearDiscCache();
    }

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * 显示gif动画 如果不是gif就用imageloader加载
     *
     * @param context
     * @param imgName
     * @param imgPath
     * @param view
     */
    public static void showGif(final Context context, final String imgName,
                               final String imgPath, final ImageView view) {
        if (".gif".equals(imgName.substring(imgName.length() - 4,
                imgName.length()))) {
            // 如果加载出来的图片是gif后缀的话 则使用glide去加载动态图片
            view.post(new Runnable() {
                @Override
                public void run() {
                    Glide.with(context).load(imgPath).asGif()
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(view);
                }
            });
        } else {
            // 如果是静态图片的话直接使用 imageloader在首页缓存好的图片资源加载photoview
            view.post(new Runnable() {
                @Override
                public void run() {
                    ToolImage.initImageLoader(context).displayImage(imgPath,
                            view, ToolImage.getRaidoOptions());
                }
            });

        }
    }


    /**
     * 通过手机相册获取图片
     *
     * @param activity
     */
    public static void selectPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_ALBUM);
    }

    /**
     * 通过手机照相获取图片
     *
     * @param activity
     * @return 照相后图片的路径
     */
    public static String takePicture(Activity activity) {
        FileUtils.createDirFile(IMAGE_PATH);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String path = IMAGE_PATH + UUID.randomUUID().toString() + "jpg";
        File file = FileUtils.createNewFile(path);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_CAMERA);
        return path;
    }

    /**
     * 删除图片缓存目录
     */
    public static void deleteImageFile() {
        File dir = new File(IMAGE_PATH);
        if (dir.exists()) {
            FileUtils.delFolder(IMAGE_PATH);
        }
    }

    /**
     * 从文件中获取图片
     *
     * @param path 图片的路径
     * @return
     */
    public static Bitmap getBitmapFromFile(String path) {
        return BitmapFactory.decodeFile(path);
    }

    /**
     * 从Uri中获取图片
     *
     * @param cr  ContentResolver对象
     * @param uri 图片的Uri
     * @return
     */
    public static Bitmap getBitmapFromUri(ContentResolver cr, Uri uri) {
        try {
            return BitmapFactory.decodeStream(cr.openInputStream(uri));
        } catch (FileNotFoundException e) {

        }
        return null;
    }

    /**
     * 根据宽度和长度进行缩放图片
     *
     * @param path 图片的路径
     * @param w    宽度
     * @param h    长度
     * @return
     */
    public static Bitmap createBitmap(String path, int w, int h) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            // 这里是整个方法的关键，inJustDecodeBounds设为true时将不为图片分配内存。
            BitmapFactory.decodeFile(path, opts);
            int srcWidth = opts.outWidth;// 获取图片的原始宽度
            int srcHeight = opts.outHeight;// 获取图片原始高度
            int destWidth = 0;
            int destHeight = 0;
            // 缩放的比例
            double ratio = 0.0;
            if (srcWidth < w || srcHeight < h) {
                ratio = 0.0;
                destWidth = srcWidth;
                destHeight = srcHeight;
            } else if (srcWidth > srcHeight) {// 按比例计算缩放后的图片大小，maxLength是长或宽允许的最大长度
                ratio = (double) srcWidth / w;
                destWidth = w;
                destHeight = (int) (srcHeight / ratio);
            } else {
                ratio = (double) srcHeight / h;
                destHeight = h;
                destWidth = (int) (srcWidth / ratio);
            }
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            // 缩放的比例，缩放是很难按准备的比例进行缩放的，目前我只发现只能通过inSampleSize来进行缩放，其值表明缩放的倍数，SDK中建议其值是2的指数值
            newOpts.inSampleSize = (int) ratio + 1;
            // inJustDecodeBounds设为false表示把图片读进内存中
            newOpts.inJustDecodeBounds = false;
            // 设置大小，这个一般是不准确的，是以inSampleSize的为准，但是如果不设置却不能缩放
            newOpts.outHeight = destHeight;
            newOpts.outWidth = destWidth;
            // 获取缩放后图片
            return BitmapFactory.decodeFile(path, newOpts);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }

    /**
     * 获取图片的长度和宽度
     *
     * @param bitmap 图片bitmap对象
     * @return
     */
    public static Bundle getBitmapWidthAndHeight(Bitmap bitmap) {
        Bundle bundle = null;
        if (bitmap != null) {
            bundle = new Bundle();
            bundle.putInt("width", bitmap.getWidth());
            bundle.putInt("height", bitmap.getHeight());
            return bundle;
        }
        return null;
    }

    /**
     * 判断图片高度和宽度是否过大
     *
     * @param bitmap 图片bitmap对象
     * @return
     */
    public static boolean bitmapIsLarge(Bitmap bitmap) {
        final int MAX_WIDTH = 60;
        final int MAX_HEIGHT = 60;
        Bundle bundle = getBitmapWidthAndHeight(bitmap);
        if (bundle != null) {
            int width = bundle.getInt("width");
            int height = bundle.getInt("height");
            if (width > MAX_WIDTH && height > MAX_HEIGHT) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据比例缩放图片
     *
     * @param screenWidth 手机屏幕的宽度
     * @param filePath    图片的路径
     * @param ratio       缩放比例
     * @return
     */
    public static Bitmap CompressionPhoto(float screenWidth, String filePath,
                                          int ratio) {
        Bitmap bitmap = ToolImage.getBitmapFromFile(filePath);
        Bitmap compressionBitmap = null;
        float scaleWidth = screenWidth / (bitmap.getWidth() * ratio);
        float scaleHeight = screenWidth / (bitmap.getHeight() * ratio);
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        try {
            compressionBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (Exception e) {
            return bitmap;
        }
        return compressionBitmap;
    }

    /**
     * 保存图片到SD卡
     *
     * @param bitmap 图片的bitmap对象
     * @return
     */
    public static String savePhotoToSDCard(Bitmap bitmap) {
        if (!FileUtils.isSdcardExist()) {
            return null;
        }
        FileOutputStream fileOutputStream = null;
        FileUtils.createDirFile(IMAGE_PATH);

        String fileName = UUID.randomUUID().toString() + ".jpg";
        String newFilePath = IMAGE_PATH + fileName;
        File file = FileUtils.createNewFile(newFilePath);
        if (file == null) {
            return null;
        }
        try {
            fileOutputStream = new FileOutputStream(newFilePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (FileNotFoundException e1) {
            return null;
        } finally {

            try {
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                return null;
            }
        }
        return newFilePath;
    }

    /**
     * 滤镜效果--LOMO
     *
     * @param bitmap
     * @return
     */
    public static Bitmap lomoFilter(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int dst[] = new int[width * height];
        bitmap.getPixels(dst, 0, width, 0, 0, width, height);

        int ratio = width > height ? height * 32768 / width : width * 32768
                / height;
        int cx = width >> 1;
        int cy = height >> 1;
        int max = cx * cx + cy * cy;
        int min = (int) (max * (1 - 0.8f));
        int diff = max - min;

        int ri, gi, bi;
        int dx, dy, distSq, v;

        int R, G, B;

        int value;
        int pos, pixColor;
        int newR, newG, newB;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pos = y * width + x;
                pixColor = dst[pos];
                R = Color.red(pixColor);
                G = Color.green(pixColor);
                B = Color.blue(pixColor);

                value = R < 128 ? R : 256 - R;
                newR = (value * value * value) / 64 / 256;
                newR = (R < 128 ? newR : 255 - newR);

                value = G < 128 ? G : 256 - G;
                newG = (value * value) / 128;
                newG = (G < 128 ? newG : 255 - newG);

                newB = B / 2 + 0x25;

                // ==========边缘黑暗==============//
                dx = cx - x;
                dy = cy - y;
                if (width > height)
                    dx = (dx * ratio) >> 15;
                else
                    dy = (dy * ratio) >> 15;

                distSq = dx * dx + dy * dy;
                if (distSq > min) {
                    v = ((max - distSq) << 8) / diff;
                    v *= v;

                    ri = newR * v >> 16;
                    gi = newG * v >> 16;
                    bi = newB * v >> 16;

                    newR = ri > 255 ? 255 : (ri < 0 ? 0 : ri);
                    newG = gi > 255 ? 255 : (gi < 0 ? 0 : gi);
                    newB = bi > 255 ? 255 : (bi < 0 ? 0 : bi);
                }
                // ==========边缘黑暗end==============//

                dst[pos] = Color.rgb(newR, newG, newB);
            }
        }

        Bitmap acrossFlushBitmap = Bitmap.createBitmap(width, height,
                Config.RGB_565);
        acrossFlushBitmap.setPixels(dst, 0, width, 0, 0, width, height);
        return acrossFlushBitmap;
    }

    /**
     * @return 返回指定笔和指定字符串的长度
     */
    public static float getFontlength(Paint paint, String str) {
        return paint.measureText(str);
    }

    /**
     * @return 返回指定笔的文字高度
     */
    public static float getFontHeight(Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return fm.descent - fm.ascent;
    }

    /**
     * @return 返回指定笔离文字顶部的基准距离
     */
    public static float getFontLeading(Paint paint) {
        FontMetrics fm = paint.getFontMetrics();
        return fm.leading - fm.ascent;
    }

    /**
     * 获取圆角图片
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 获取颜色的圆角bitmap
     *
     * @param context
     * @param color
     * @return
     */
    public static Bitmap getRoundBitmap(Context context, int color) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int width = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 12.0f, metrics));
        int height = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 4.0f, metrics));
        int round = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 2.0f, metrics));
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawRoundRect(new RectF(0.0F, 0.0F, width, height), round,
                round, paint);
        return bitmap;
    }
}
