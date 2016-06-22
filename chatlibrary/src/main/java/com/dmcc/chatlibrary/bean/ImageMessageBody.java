package com.dmcc.chatlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class ImageMessageBody extends FileMessageBody
        implements Parcelable {
    String thumbnailUrl;
    String thumbnailSecret = null;
    int width;
    int height;
    private boolean sendOriginalImage;

    public ImageMessageBody() {
    }

    public ImageMessageBody(File paramFile) {
        this.localUrl = paramFile.getAbsolutePath();
        this.fileName = paramFile.getName();
    }

    ImageMessageBody(String paramString1, String paramString2, String paramString3) {
        this.fileName = paramString1;
        this.remoteUrl = paramString2;
        this.thumbnailUrl = paramString3;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String paramString) {
        this.thumbnailUrl = paramString;
    }

    public String toString() {
        return "image:" + this.fileName + ",localurl:" + this.localUrl + ",remoteurl:" + this.remoteUrl + ",thumbnial:" + this.thumbnailUrl;
    }

    public void setThumbnailSecret(String paramString) {
        this.thumbnailSecret = paramString;
    }

    public String getThumbnailSecret() {
        return this.thumbnailSecret;
    }

    public void setSendOriginalImage(boolean paramBoolean) {
        this.sendOriginalImage = paramBoolean;
    }

    public boolean isSendOriginalImage() {
        return this.sendOriginalImage;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.thumbnailSecret);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeByte(this.sendOriginalImage ? (byte) 1 : (byte) 0);
    }

    protected ImageMessageBody(Parcel in) {
        this.thumbnailUrl = in.readString();
        this.thumbnailSecret = in.readString();
        this.width = in.readInt();
        this.height = in.readInt();
        this.sendOriginalImage = in.readByte() != 0;
    }

    public static final Creator<ImageMessageBody> CREATOR = new Creator<ImageMessageBody>() {
        @Override
        public ImageMessageBody createFromParcel(Parcel source) {
            return new ImageMessageBody(source);
        }

        @Override
        public ImageMessageBody[] newArray(int size) {
            return new ImageMessageBody[size];
        }
    };
}