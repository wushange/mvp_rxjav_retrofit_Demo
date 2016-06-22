package com.dmcc.chatlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class NormalFileMessageBody extends FileMessageBody
        implements Parcelable {
    long fileSize;

    public NormalFileMessageBody(File paramFile) {
        this.localUrl = paramFile.getAbsolutePath();
        this.fileName = paramFile.getName();
        this.fileSize = paramFile.length();
    }

    NormalFileMessageBody(String paramString1, String paramString2) {
        this.fileName = paramString1;
        this.remoteUrl = paramString2;
    }

    public NormalFileMessageBody() {
    }

    public String toString() {
        return "normal file:" + this.fileName + ",localUrl:" + this.localUrl + ",remoteUrl:" + this.remoteUrl + ",file size:" + this.fileSize;
    }

    public long getFileSize() {
        return this.fileSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.fileSize);
    }

    protected NormalFileMessageBody(Parcel in) {
        this.fileSize = in.readLong();
    }

    public static final Creator<NormalFileMessageBody> CREATOR = new Creator<NormalFileMessageBody>() {
        @Override
        public NormalFileMessageBody createFromParcel(Parcel source) {
            return new NormalFileMessageBody(source);
        }

        @Override
        public NormalFileMessageBody[] newArray(int size) {
            return new NormalFileMessageBody[size];
        }
    };
}