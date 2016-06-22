package com.dmcc.chatlibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dmcc.chatlibrary.R;
import com.dmcc.chatlibrary.bean.EMMessage;
import com.dmcc.chatlibrary.bean.ImageMessageBody;
import com.dmcc.chatlibrary.utils.EaseCommonUtils;
import com.dmcc.chatlibrary.utils.EaseImageCache;
import com.dmcc.chatlibrary.utils.EaseImageUtils;
import com.dmcc.chatlibrary.utils.ImageUtils;

import java.io.File;

public class EaseChatRowImage extends EaseChatRowFile {

    protected ImageView imageView;
    private ImageMessageBody imgBody;

    public EaseChatRowImage(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ? R.layout.ease_row_received_picture : R.layout.ease_row_sent_picture, this);
    }

    @Override
    protected void onFindViewById() {
        percentageView = (TextView) findViewById(R.id.percentage);
        imageView = (ImageView) findViewById(R.id.image);
    }


    @Override
    protected void onSetUpView() {
        imgBody = (ImageMessageBody) message.getBody();
        // 接收方向的消息
        if (message.direct == EMMessage.Direct.RECEIVE) {
            Glide.with(context).load(imgBody.getThumbnailUrl()).asBitmap().override(160, 160).centerCrop().into(imageView);
            setMessageReceiveCallback();
//            if (message.status == EMMessage.Status.INPROGRESS) {
//                imageView.setImageResource(R.drawable.ease_default_image);
//                setMessageReceiveCallback();
//            } else {
//                progressBar.setVisibility(View.GONE);
//                percentageView.setVisibility(View.GONE);
//                imageView.setImageResource(R.drawable.ease_default_image);
//                if (imgBody.getLocalUrl() != null) {
//                    // String filePath = imgBody.getLocalUrl();
//                    String remotePath = imgBody.getRemoteUrl();
//                    String filePath = EaseImageUtils.getImagePath(remotePath);
//                    String thumbRemoteUrl = imgBody.getThumbnailUrl();
//                    String thumbnailPath = EaseImageUtils.getThumbnailImagePath(thumbRemoteUrl);
//                    showImageView(thumbnailPath, imageView, filePath, message);
//                }
//            }
            return;
        }

        String filePath = imgBody.getLocalUrl();
        if (filePath != null) {
            showImageView(EaseImageUtils.getThumbnailImagePath(filePath), imageView, filePath, message);
        }
        handleSendMessage();
    }

    @Override
    protected void onUpdateView() {
        super.onUpdateView();
    }

    @Override
    protected void onBubbleClick() {
    }

    /**
     * load image into image view
     *
     * @param thumbernailPath
     * @param iv
     * @return the image exists or not
     */
    private boolean showImageView(final String thumbernailPath, final ImageView iv, final String localFullSizePath, final EMMessage message) {
        // first check if the thumbnail image already loaded into cache
        Bitmap bitmap = EaseImageCache.getInstance().get(thumbernailPath);
        if (bitmap != null) {
            // thumbnail image is already loaded, reuse the drawable
            iv.setImageBitmap(bitmap);
            return true;
        } else {
            new AsyncTask<Object, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Object... args) {
                    File file = new File(thumbernailPath);
                    if (file.exists()) {
                        return ImageUtils.decodeScaleImage(thumbernailPath, 160, 160);
                    } else {
                        if (message.direct == EMMessage.Direct.SEND) {
                            return ImageUtils.decodeScaleImage(localFullSizePath, 160, 160);
                        } else {
                            return null;
                        }
                    }
                }

                @Override
                protected void onPostExecute(Bitmap image) {
                    if (image != null) {
                        iv.setImageBitmap(image);
                        EaseImageCache.getInstance().put(thumbernailPath, image);
                    } else {
                        if (message.status == EMMessage.Status.FAIL) {
                            if (EaseCommonUtils.isNetWorkConnected(activity)) {
                                new Thread(new Runnable() {

                                    @Override
                                    public void run() {
                                        Toast.makeText(context, "网络图片", 1).show();
                                    }
                                }).start();
                            }
                        }

                    }
                }
            }.execute();

            return true;
        }
    }

}
