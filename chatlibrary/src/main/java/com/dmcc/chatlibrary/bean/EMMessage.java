package com.dmcc.chatlibrary.bean;

import com.dmcc.chatlibrary.EMCallBack;

import java.io.Serializable;
import java.util.Hashtable;

public class EMMessage implements Serializable {
    private static final String TAG = "msg";
    EMCallBack messageStatusCallBack;
    Type type;
    public Direct direct;
    public Status status = Status.CREATE;
    EMContact from;
    EMContact to;
    MessageBody body;
    String msgId;
    public boolean isAcked = false;
    public boolean isDelivered = false;
    long msgTime;
    ChatType chatType = ChatType.Chat;
    public transient int progress = 0;
    Hashtable<String, Object> attributes = new Hashtable();
    transient boolean unread = true;
    transient boolean offline = false;
    boolean isListened;
    static final String ATTR_ENCRYPTED = "isencrypted";
    private int error = 0;

    public EMMessage(Type paramType) {
        this.type = paramType;
        this.msgTime = System.currentTimeMillis();
    }

    public Type getType() {
        return this.type;
    }

    public MessageBody getBody() {
        return this.body;
    }

    public long getMsgTime() {
        return this.msgTime;
    }

    public void setMsgTime(long paramLong) {
        this.msgTime = paramLong;
    }


    public void addBody(MessageBody paramMessageBody) {
        this.body = paramMessageBody;
    }

    public String getFrom() {
        if (this.from == null)
            return null;
        return this.from.username;
    }

    public void setFrom(String paramString) {
        EMContact localEMContact = new EMContact();
        localEMContact.setUsername(paramString);
        this.from = localEMContact;
    }

    public void setTo(String paramString) {
        EMContact localEMContact = new EMContact();
        localEMContact.setUsername(paramString);
        this.to = localEMContact;
    }

    public String getTo() {
        if (this.to == null)
            return null;
        return this.to.username;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public void setMsgId(String paramString) {
        this.msgId = paramString;
    }


    public void setMessageStatusCallback(EMCallBack paramEMCallBack) {
        this.messageStatusCallBack = paramEMCallBack;
    }

    public String toString() {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("msg{from:" + this.from.username);
        localStringBuffer.append(", to:" + this.to.username);
        localStringBuffer.append(" body:" + this.body.toString());
        return localStringBuffer.toString();
    }


    public ChatType getChatType() {
        return this.chatType;
    }

    public void setChatType(ChatType paramChatType) {
        this.chatType = paramChatType;
    }

    public Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isAcked() {
        return this.isAcked;
    }

    public void setAcked(boolean paramBoolean) {
        this.isAcked = paramBoolean;
    }

    public boolean isDelivered() {
        return this.isDelivered;
    }

    public void setDelivered(boolean paramBoolean) {
        this.isDelivered = paramBoolean;
    }

    public boolean isUnread() {
        return this.unread;
    }

    public void setUnread(boolean paramBoolean) {
        this.unread = paramBoolean;
    }

    public void setType(Type paramType) {
        this.type = paramType;
    }

    public boolean isListened() {
        return this.isListened;
    }

    public void setListened(boolean paramBoolean) {
        this.isListened = paramBoolean;
    }


    void setError(int paramInt) {
        this.error = paramInt;
    }

    public int getError() {
        return this.error;
    }

    public Direct getDirect() {
        return direct;
    }

    public void setDirect(Direct direct) {
        this.direct = direct;
    }

    public enum ChatType {
        Chat, GroupChat, ChatRoom
    }

    public enum Direct {
        SEND, RECEIVE
    }

    public enum Status {
        SUCCESS, FAIL, INPROGRESS, CREATE
    }

    public enum Type {
        TXT, IMAGE, VIDEO, LOCATION, VOICE, FILE, CMD
    }
}