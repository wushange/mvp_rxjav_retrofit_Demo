package com.dmcc.chatlibrary.widget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dmcc.chatlibrary.R;
import com.dmcc.chatlibrary.bean.EMMessage;
import com.dmcc.chatlibrary.bean.VoiceMessageBody;


public class EaseChatRowVoice extends EaseChatRowFile{

    private ImageView voiceImageView;
    private TextView voiceLengthView;
    private ImageView readStutausView;

    public EaseChatRowVoice(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                R.layout.ease_row_received_voice : R.layout.ease_row_sent_voice, this);
    }

    @Override
    protected void onFindViewById() {
        voiceImageView = ((ImageView) findViewById(R.id.iv_voice));
        voiceLengthView = (TextView) findViewById(R.id.tv_length);
        readStutausView = (ImageView) findViewById(R.id.iv_unread_voice);
    }

    @Override
    protected void onSetUpView() {
        VoiceMessageBody voiceBody = (VoiceMessageBody) message.getBody();
        int len = voiceBody.getLength();
        if(len>0){
            voiceLengthView.setText(voiceBody.getLength() + "\"");
            voiceLengthView.setVisibility(View.VISIBLE);
        }else{
            voiceLengthView.setVisibility(View.INVISIBLE);
        }
        if (EaseChatRowVoicePlayClickListener.playMsgId != null
                && EaseChatRowVoicePlayClickListener.playMsgId.equals(message.getMsgId()) && EaseChatRowVoicePlayClickListener.isPlaying) {
            AnimationDrawable voiceAnimation;
            if (message.direct == EMMessage.Direct.RECEIVE) {
                voiceImageView.setImageResource(R.drawable.voice_from_icon);
            } else {
                voiceImageView.setImageResource(R.drawable.voice_to_icon);
            }
            voiceAnimation = (AnimationDrawable) voiceImageView.getDrawable();
            voiceAnimation.start();
        } else {
            if (message.direct == EMMessage.Direct.RECEIVE) {
                voiceImageView.setImageResource(R.drawable.ease_chatfrom_voice_playing);
            } else {
                voiceImageView.setImageResource(R.drawable.ease_chatto_voice_playing);
            }
        }
        
        if (message.direct == EMMessage.Direct.RECEIVE) {
            if (message.isListened()) {
                // 隐藏语音未听标志
                readStutausView.setVisibility(View.INVISIBLE);
            } else {
                readStutausView.setVisibility(View.VISIBLE);
            }
            if (message.status == EMMessage.Status.INPROGRESS) {
                progressBar.setVisibility(View.VISIBLE);
                setMessageReceiveCallback();
            } else {
                progressBar.setVisibility(View.INVISIBLE);

            }
            return;
        }

        // until here, deal with send voice msg
        handleSendMessage();
    }

    @Override
    protected void onUpdateView() {
        super.onUpdateView();
    }

    @Override
    protected void onBubbleClick() {
        new EaseChatRowVoicePlayClickListener(message, voiceImageView, readStutausView, adapter, activity).onClick(bubbleLayout);
    }
    
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (EaseChatRowVoicePlayClickListener.currentPlayListener != null && EaseChatRowVoicePlayClickListener.isPlaying) {
            // 停止语音播放
            EaseChatRowVoicePlayClickListener.currentPlayListener.stopPlayVoice();
        }
    }
    
}
