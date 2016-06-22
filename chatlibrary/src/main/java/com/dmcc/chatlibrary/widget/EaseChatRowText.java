package com.dmcc.chatlibrary.widget;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dmcc.chatlibrary.R;
import com.dmcc.chatlibrary.bean.EMMessage;
import com.dmcc.chatlibrary.bean.TextMessageBody;
import com.dmcc.chatlibrary.emoji.SimpleCommonUtils;


public class EaseChatRowText extends EaseChatRow {

    private TextView contentView;

    public EaseChatRowText(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
    }

    @Override
    protected void onFindViewById() {
        contentView = (TextView) findViewById(R.id.tv_chatcontent);
    }

    @Override
    public void onSetUpView() {
        TextMessageBody txtBody = (TextMessageBody) message.getBody();
        // 设置内容
        SimpleCommonUtils.spannableEmoticonFilter(contentView, txtBody.getMessage());
//        contentView.setText(txtBody.toString(), BufferType.SPANNABLE);
        if (message.direct == EMMessage.Direct.SEND) {
            contentView.setTextColor(getResources().getColor(R.color.white));
        } else {
            contentView.setTextColor(getResources().getColor(R.color.black_deep));
        }

        handleTextMessage();
    }

    protected void handleTextMessage() {
        if (message.direct == EMMessage.Direct.SEND) {
            setMessageSendCallback();
            switch (message.status) {
                case CREATE:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.GONE);
                    // 发送消息
//                sendMsgInBackground(message);
                    break;
                case SUCCESS: // 发送成功
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.GONE);
                    break;
                case FAIL: // 发送失败
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS: // 发送中
                    progressBar.setVisibility(View.VISIBLE);
                    statusView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        } else {
            if (!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat) {
                message.isAcked = true;
            }
        }
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
        // TODO Auto-generated method stub

    }


}
