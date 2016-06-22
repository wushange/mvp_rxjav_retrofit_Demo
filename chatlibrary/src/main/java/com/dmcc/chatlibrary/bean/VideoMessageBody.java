package com.dmcc.chatlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class VideoMessageBody extends FileMessageBody
        implements Parcelable {
    int length = 0;
    String thumbnailUrl;
    String localThumb;
    String thumbnailSecret = null;
    long file_length = 0L;

    public VideoMessageBody() {
    }

    public VideoMessageBody(File paramFile, String paramString, int paramInt, long paramLong) {
        this.localUrl = paramFile.getAbsolutePath();
        this.fileName = paramFile.getName();
        this.localThumb = paramString;
        this.length = paramInt;
        this.file_length = paramLong;
    }

    VideoMessageBody(String paramString1, String paramString2, String paramString3, int paramInt) {
        this.fileName = paramString1;
        this.remoteUrl = paramString2;
        this.thumbnailUrl = paramString3;
        this.length = paramInt;
    }

    public String getThumbnailUrl() {
        return this.thumbnailUrl;
    }

    public void setThumbnailUrl(String paramString) {
        this.thumbnailUrl = paramString;
    }

    public long getVideoFileLength() {
        return this.file_length;
    }

    public void setVideoFileLength(long paramLong) {
        this.file_length = paramLong;
    }

    public void setLocalThumb(String paramString) {
        this.localThumb = paramString;
    }

    public String getLocalThumb() {
        return this.localThumb;
    }

    public int getLength() {
        return this.length;
    }

    public String toString() {
        return "video:" + this.fileName + ",localUrl:" + this.localUrl + ",remoteUrl:" + this.remoteUrl + ",thumbnailUrl:" + this.thumbnailUrl + ",length:" + this.length;
    }

    public void setThumbnailSecret(String paramString) {
        this.thumbnailSecret = paramString;
    }

    public String getThumbnailSecret() {
        return this.thumbnailSecret;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.length);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.localThumb);
        dest.writeString(this.thumbnailSecret);
        dest.writeLong(this.file_length);
    }

    protected VideoMessageBody(Parcel in) {
        this.length = in.readInt();
        this.thumbnailUrl = in.readString();
        this.localThumb = in.readString();
        this.thumbnailSecret = in.readString();
        this.file_length = in.readLong();
    }

    public static final Creator<VideoMessageBody> CREATOR = new Creator<VideoMessageBody>() {
        @Override
        public VideoMessageBody createFromParcel(Parcel source) {
            return new VideoMessageBody(source);
        }

        @Override
        public VideoMessageBody[] newArray(int size) {
            return new VideoMessageBody[size];
        }
    };
}