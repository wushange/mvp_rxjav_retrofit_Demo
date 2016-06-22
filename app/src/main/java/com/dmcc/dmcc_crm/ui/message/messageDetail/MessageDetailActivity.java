package com.dmcc.dmcc_crm.ui.message.messageDetail;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.dmcc.chatlibrary.EaseChatMessageList;
import com.dmcc.chatlibrary.adapter.EaseMessageAdapter;
import com.dmcc.chatlibrary.bean.EMMessage;
import com.dmcc.chatlibrary.bean.LocationMessageBody;
import com.dmcc.chatlibrary.bean.TextMessageBody;
import com.dmcc.chatlibrary.bean.VoiceMessageBody;
import com.dmcc.chatlibrary.emoji.Constants;
import com.dmcc.chatlibrary.emoji.SimpleCommonUtils;
import com.dmcc.chatlibrary.emoji.adapter.MenusAdapter;
import com.dmcc.chatlibrary.emoji.data.ImMsgBean;
import com.dmcc.chatlibrary.emoji.data.MenuBean;
import com.dmcc.chatlibrary.emoji.widget.SimpleMenusGridView;
import com.dmcc.chatlibrary.utils.DateUtils;
import com.dmcc.chatlibrary.widget.EaseVoiceRecorderView;
import com.dmcc.dmcc_crm.R;
import com.dmcc.dmcc_crm.base.MvpBaseSwipeBackActivity;
import com.dmcc.dmcc_crm.customview.AppTitle;
import com.sj.emoji.EmojiBean;
import com.wushange.commsdk.util.ShowToast;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import sj.keyboard.XhsEmoticonsKeyBoard;
import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.utils.EmoticonsKeyboardUtils;
import sj.keyboard.widget.EmoticonsEditText;
import sj.keyboard.widget.FuncLayout;

/**
 * Created by wushange on 2016/06/03.
 */
public class MessageDetailActivity extends MvpBaseSwipeBackActivity<UploadFileContract.UploadFileStatus, UploadFilePresenter> implements UploadFileContract.UploadFileStatus, FuncLayout.OnFuncKeyBoardListener, MenusAdapter.MenuClickCallBack {
    public static void startActivity(Context mContext) {
        Intent intent = new Intent(mContext, MessageDetailActivity.class);
        mContext.startActivity(intent);
    }

    @ViewInject(R.id.apptitle)
    AppTitle appTitle;
    @ViewInject(R.id.ek_bar)
    XhsEmoticonsKeyBoard ekBar;
    @ViewInject(R.id.lv_chat)
    EaseChatMessageList lvChat;
    private EaseMessageAdapter chattingListAdapter;
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    @ViewInject(R.id.voice_recorder)
    EaseVoiceRecorderView voiceRecorderView;

    @Override
    public int bindLayout() {
        return R.layout.activity_message_chat_main_view;
    }

    @Override
    public void initView(View view) {
        appTitle.init(AppTitle.AppTitleStyle.BACK_TITLE);
        appTitle.setTitle("DMCC");
        appTitle.setBackClickClisener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setAutoDissIm(false);

    }

    @Override
    public void doBusiness(Context mContext) {
        initEmoticonsKeyBoardBar();
        initListView();
    }

    @Override
    public UploadFilePresenter initPresenter() {
        return new UploadFilePresenter();
    }


    private void initEmoticonsKeyBoardBar() {
        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
        ekBar.setAdapter(SimpleCommonUtils.getCommonAdapter(this, emoticonClickListener));
        ekBar.addOnFuncKeyBoardListener(this);
        SimpleMenusGridView simpleMenusGridView = new SimpleMenusGridView(this);
        simpleMenusGridView.setMenuClickCallBack(this);
        ekBar.addFuncView(simpleMenusGridView);
        ekBar.getEtChat().setOnSizeChangedListener(new EmoticonsEditText.OnSizeChangedListener() {
            @Override
            public void onSizeChanged(int w, int h, int oldw, int oldh) {
                scrollToBottom();
            }
        });
        ekBar.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSendBtnClick(ekBar.getEtChat().getText().toString());
                ekBar.getEtChat().setText("");
            }

        });
        ekBar.getBtnVoice().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return voiceRecorderView.onPressToSpeakBtnTouch(v, event, new EaseVoiceRecorderView.EaseVoiceRecorderCallback() {
                    @Override
                    public void onVoiceRecordComplete(String voiceFilePath, int voiceTimeLength) {
                        ShowToast.showToast(getContext(), "录音完毕");
                    }
                });
            }
        });
        lvChat.getListView().setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        ekBar.reset();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

    }

    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick(ekBar.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == Constants.EMOTICON_CLICK_BIGIMAGE) {
                    if (o instanceof EmoticonEntity) {
                        OnSendImage(((EmoticonEntity) o).getIconUri());
                    }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
                    }

                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    int index = ekBar.getEtChat().getSelectionStart();
                    Editable editable = ekBar.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (EmoticonsKeyboardUtils.isFullScreen(this)) {
            boolean isConsum = ekBar.dispatchKeyEventInFullScreen(event);
            return isConsum ? isConsum : super.dispatchKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

    private void initListView() {
        List<ImMsgBean> beanList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            ImMsgBean bean = new ImMsgBean();
            bean.setContent("Test:" + i);
            beanList.add(bean);
        }

        List<EMMessage> messageList = new ArrayList<>();
        int ii = 1465362143;
        int sum = 2000;
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                EMMessage emMessage = new EMMessage(EMMessage.Type.TXT);
                emMessage.setFrom("吴善革form");
                emMessage.setTo("吴善革to");
                emMessage.setDirect(EMMessage.Direct.SEND);
                emMessage.setMsgTime(sum += ii);
                emMessage.addBody(new TextMessageBody("收到的"));
                messageList.add(emMessage);
            } else if (i % 3 == 0) {
                EMMessage emMessage = new EMMessage(EMMessage.Type.LOCATION);
                emMessage.setFrom("LocationMessageBodyform");
                emMessage.setTo("LocationMessageBodyto");
                emMessage.setDirect(EMMessage.Direct.RECEIVE);
                emMessage.setMsgTime(sum += ii);
                emMessage.addBody(new LocationMessageBody("地图消息", 116.459623, 39.92918));
                messageList.add(emMessage);
            } else if (i % 5 == 0) {
                EMMessage emMessage = new EMMessage(EMMessage.Type.VOICE);
                emMessage.setFrom("吴善革form");
                emMessage.setTo("吴善革to");
                emMessage.setDirect(EMMessage.Direct.RECEIVE);
                emMessage.setMsgTime(sum += ii);
                VoiceMessageBody imageMessageBody = new VoiceMessageBody(new File(""), 1000);
                imageMessageBody.setRemoteUrl("http://img.sootuu.com/vector/200801/070/0166.jpg");
                emMessage.addBody(imageMessageBody);
                messageList.add(emMessage);
            }

        }

        lvChat.init(messageList, 1, null);
        setListItemClickListener();

    }

    protected void setListItemClickListener() {
        lvChat.setItemClickListener(new EaseChatMessageList.MessageListItemClickListener() {

            @Override
            public void onUserAvatarClick(String username) {
                showToast("点击头像" + username);
            }

            @Override
            public void onUserAvatarLongClick(String username) {
                showToast("头像长按" + username);
            }

            @Override
            public void onResendClick(final EMMessage message) {
                showToast("onResendClick" + message.getFrom());
            }

            @Override
            public void onBubbleLongClick(EMMessage message) {
                showToast("onBubbleLongClick" + message.getMsgTime());
            }

            @Override
            public boolean onBubbleClick(EMMessage message) {
                showToast("onBubbleClick" + message.getBody().toString());
                return false;
            }
        });
    }

    private void OnSendBtnClick(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            EMMessage emMessage = new EMMessage(EMMessage.Type.TXT);
            emMessage.setFrom("吴善革form");
            emMessage.setTo("吴善革to");
            emMessage.setDirect(EMMessage.Direct.SEND);
            emMessage.setMsgTime(DateUtils.getTimestamp());
            emMessage.addBody(new TextMessageBody(msg));
            lvChat.addMessage(emMessage);
            scrollToBottom();
        }
    }

    private void OnSendImage(String image) {
        if (!TextUtils.isEmpty(image)) {
            OnSendBtnClick("[img]" + image);
        }
    }

    private void scrollToBottom() {
        lvChat.getListView().requestLayout();
        lvChat.getListView().post(new Runnable() {
            @Override
            public void run() {
                lvChat.getListView().setSelection(lvChat.getBottom());
            }
        });
    }

    @Override
    public void OnFuncPop(int height) {
    }

    @Override
    public void OnFuncClose() {
    }


    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("onPause");
        ekBar.reset();
    }

    @Override
    public void MenuItemClick(MenuBean menuBean) {
        switch (menuBean.getFuncName()) {
            case "图片":

                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, 9,
                        mOnHanlderResultCallback);

                break;
            case "拍照":
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA,
                        mOnHanlderResultCallback);

                break;
        }

    }

    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            if (resultList != null) {
                LogUtil.e("---" + resultList.toString());
                //构建要上传的文件
                File file = new File(resultList.get(0).getPhotoPath());
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("application/otcet-stream"), file);
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("imgFile", file.getName(), requestFile);
                String descriptionString = "This is a description";
                RequestBody description =
                        RequestBody.create(
                                MediaType.parse("multipart/form-data"), descriptionString);
                presenter.uploadImg(requestFile, body);
                if (reqeustCode == REQUEST_CODE_CAMERA) {
                } else {
                }
            }

        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
            Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void startLoading() {
        LogUtil.e("正在上传");
        showSweetLoading("正在上传...");
    }

    @Override
    public void endLoading() {

    }


    @Override
    public void OnUploadSuccess(String s) {
        LogUtil.e("上传成功" + s);
        showSweetSuccess("上传成功", s);
    }

    @Override
    public void OnUploadFail(String s) {
        LogUtil.e("上传失败" + s);
        showSweetError("上传失败", s);

    }

}
