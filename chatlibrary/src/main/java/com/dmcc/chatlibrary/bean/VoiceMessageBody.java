package com.dmcc.chatlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.File;

public class VoiceMessageBody extends FileMessageBody
        implements Parcelable {
    int length = 0;


    public VoiceMessageBody(File paramFile, int paramInt) {
        this.localUrl = paramFile.getAbsolutePath();
        this.fileName = paramFile.getName();
        this.length = paramInt;
    }

    VoiceMessageBody(String paramString1, String paramString2, int paramInt) {
        this.fileName = paramString1;
        this.remoteUrl = paramString2;
        this.length = paramInt;
    }

    public int getLength() {
        return this.length;
    }

    public String toString() {
        return "voice:" + this.fileName + ",localurl:" + this.localUrl + ",remoteurl:" + this.remoteUrl + ",length:" + this.length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.length);
    }

    protected VoiceMessageBody(Parcel in) {
        this.length = in.readInt();
    }

    public static final Creator<VoiceMessageBody> CREATOR = new Creator<VoiceMessageBody>() {
        @Override
        public VoiceMessageBody createFromParcel(Parcel source) {
            return new VoiceMessageBody(source);
        }

        @Override
        public VoiceMessageBody[] newArray(int size) {
            return new VoiceMessageBody[size];
        }
    };
}