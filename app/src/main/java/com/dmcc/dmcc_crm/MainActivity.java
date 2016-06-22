package com.dmcc.dmcc_crm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.dmcc.dmcc_crm.customview.center_btn_view.MoreWindow;
import com.dmcc.dmcc_crm.ui.customer.CustomerFragment;
import com.dmcc.dmcc_crm.ui.me.MeFragment;
import com.dmcc.dmcc_crm.ui.message.MessageListFragment;
import com.dmcc.dmcc_crm.ui.project.ProjectListFragment;
import com.jauker.widget.BadgeView;
import com.wushange.commsdk.centertab.MainNavigateTabBar;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG_PAGE_HOME = "消息";
    private static final String TAG_PAGE_CITY = "客户";
    private static final String TAG_PAGE_PUBLISH = " ";
    private static final String TAG_PAGE_MESSAGE = "项目";
    private static final String TAG_PAGE_PERSON = "我";
//    private final EditorCreateInfo _CreateInfo = new EditorCreateInfo();
    private static int QUPAI_RECORD_REQUEST = 1;
    private MainNavigateTabBar mNavigateTabBar;
    private int beautySkinProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigateTabBar = (MainNavigateTabBar) findViewById(R.id.mainTabBar);

        mNavigateTabBar.onRestoreInstanceState(savedInstanceState);

        mNavigateTabBar.addTab(MessageListFragment.class,
                new MainNavigateTabBar.TabParam(R.mipmap.tab_jilu_normal, R.mipmap.tab_jilu_selected, TAG_PAGE_HOME));
        mNavigateTabBar.addTab(CustomerFragment.class,
                new MainNavigateTabBar.TabParam(R.mipmap.tab_kehu_normal, R.mipmap.tab_kehu_selected, TAG_PAGE_CITY));
        mNavigateTabBar.addTab(null, new MainNavigateTabBar.TabParam(0, 0, TAG_PAGE_PUBLISH));
        mNavigateTabBar.addTab(ProjectListFragment.class,
                new MainNavigateTabBar.TabParam(R.mipmap.tab_xiangmu_normal, R.mipmap.tab_xiangmu_selected, TAG_PAGE_MESSAGE));
        mNavigateTabBar.addTab(MeFragment.class,
                new MainNavigateTabBar.TabParam(R.mipmap.tab_me_normal, R.mipmap.tab_me_selected, TAG_PAGE_PERSON));
        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(mNavigateTabBar.getChildAt(0));
        badgeView.setText("99+");
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigateTabBar.onSaveInstanceState(outState);
    }


    public void onClickPublish(View v) {
//        AppUtils.startAct(MainActivity.this, LoginActivity.class);
//        startRecordActivity();

        MoreWindow mMoreWindow = new MoreWindow(this);
        mMoreWindow.init();
        mMoreWindow.showMoreWindow(v, 100);

    }

    private String videoPath;

    private void startRecordActivity() {
        //美颜参数:1-100.这里不设指定为80,这个值只在第一次设置，之后在录制界面滑动美颜参数之后系统会记住上一次滑动的状态
        beautySkinProgress = Integer.valueOf("80");

//        /**
//         * 压缩参数，可以自由调节
//         */
//        MovieExportOptions movie_options = new MovieExportOptions.Builder()
//                .setVideoProfile("high")
//                .setVideoBitrate(Contant.DEFAULT_BITRATE)
//                .setVideoPreset(Contant.DEFAULT_VIDEO_Preset).setVideoRateCRF(Contant.DEFAULT_VIDEO_RATE_CRF)
//                .setOutputVideoLevel(Contant.DEFAULT_VIDEO_LEVEL)
//                .setOutputVideoTune(Contant.DEFAULT_VIDEO_TUNE)
//                .configureMuxer(Contant.DEFAULT_VIDEO_MOV_FLAGS_KEY, Contant.DEFAULT_VIDEO_MOV_FLAGS_VALUE)
//                .build();
//
//        /**
//         * 界面参数
//         */
//        VideoSessionCreateInfo create_info = new VideoSessionCreateInfo.Builder()
//                .setOutputDurationLimit(Contant.DEFAULT_DURATION_MAX_LIMIT)
//                .setOutputDurationMin(Contant.DEFAULT_DURATION_LIMIT_MIN)
//                .setMovieExportOptions(movie_options)
//                .setBeautyProgress(beautySkinProgress)
//                .setBeautySkinOn(true)
//                .setCameraFacing(
//                        Camera.CameraInfo.CAMERA_FACING_BACK)
//                .setVideoSize(480,
//                        480)
//                .setCaptureHeight(getResources().getDimension(R.dimen.qupai_recorder_capture_height_size))
//                .setBeautySkinViewOn(false)
//                .setFlashLightOn(true)
//                .setTimelineTimeIndicator(true)
//                .build();
//
//        _CreateInfo.setSessionCreateInfo(create_info);
//        _CreateInfo.setNextIntent(null);
//        _CreateInfo.setOutputThumbnailSize(360, 640);//输出图片宽高
//        videoPath = FileUtils.newOutgoingFilePath(this);
//        _CreateInfo.setOutputVideoPath(videoPath);//输出视频路径
//        _CreateInfo.setOutputThumbnailPath(videoPath + ".png");//输出图片路径
//
//        QupaiServiceImpl qupaiService = new QupaiServiceImpl.Builder()
//                .setEditorCreateInfo(_CreateInfo).build();
//        qupaiService.showRecordPage(this, QUPAI_RECORD_REQUEST);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            EditorResult result = new EditorResult(data);
//            //得到视频path，和缩略图path的数组，返回十张缩略图,和视频时长
//            result.getPath();
//            result.getThumbnail();
//            result.getDuration();
//
//            ShowToast.showToast(this, "视频path" + result.getPath());
//            LogUtil.e("得到的视频路径地址---" + result.getPath() + "缩略图path" + result.getThumbnail() + "视频时长" + result.getDuration());
//            //开始上传，上传前请务必确认已调用授权接口
//
//            //删除草稿
//            QupaiDraftManager draftManager = new QupaiDraftManager();
//            draftManager.deleteDraft(data);
//        }
    }

    public interface MyTouchListener {
        void onTouchEvent(MotionEvent event);
    }

    /*
     * 保存MyTouchListener接口的列表
     */
    private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MainActivity.MyTouchListener>();

    /**
     * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void registerMyTouchListener(MyTouchListener listener) {
        myTouchListeners.add(listener);
    }

    /**
     * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
     *
     * @param listener
     */
    public void unRegisterMyTouchListener(MyTouchListener listener) {
        myTouchListeners.remove(listener);
    }

    /**
     * 分发触摸事件给所有注册了MyTouchListener的接口
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        for (MyTouchListener listener : myTouchListeners) {
            listener.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
