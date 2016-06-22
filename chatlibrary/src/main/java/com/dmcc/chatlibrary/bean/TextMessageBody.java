package com.dmcc.chatlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class TextMessageBody extends MessageBody
        implements Parcelable {
    String message;

    public TextMessageBody(String paramString) {
        this.message = paramString;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return "txt:\"" + this.message + "\"";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
    }

    protected TextMessageBody(Parcel in) {
        this.message = in.readString();
    }

    public static final Creator<TextMessageBody> CREATOR = new Creator<TextMessageBody>() {
        @Override
        public TextMessageBody createFromParcel(Parcel source) {
            return new TextMessageBody(source);
        }

        @Override
        public TextMessageBody[] newArray(int size) {
            return new TextMessageBody[size];
        }
    };
}