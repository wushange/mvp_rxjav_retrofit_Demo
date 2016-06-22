package com.dmcc.chatlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationMessageBody extends MessageBody
  implements Parcelable
{
  String address;
  double latitude;
  double longitude;

  public LocationMessageBody(String paramString, double paramDouble1, double paramDouble2)
  {
    this.address = paramString;
    this.latitude = paramDouble1;
    this.longitude = paramDouble2;
  }

  public String getAddress()
  {
    return this.address;
  }

  public double getLatitude()
  {
    return this.latitude;
  }

  public double getLongitude()
  {
    return this.longitude;
  }

  public String toString()
  {
    return "location:" + this.address + ",lat:" + this.latitude + ",lng:" + this.longitude;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.address);
    dest.writeDouble(this.latitude);
    dest.writeDouble(this.longitude);
  }

  protected LocationMessageBody(Parcel in) {
    this.address = in.readString();
    this.latitude = in.readDouble();
    this.longitude = in.readDouble();
  }

  public static final Creator<LocationMessageBody> CREATOR = new Creator<LocationMessageBody>() {
    @Override
    public LocationMessageBody createFromParcel(Parcel source) {
      return new LocationMessageBody(source);
    }

    @Override
    public LocationMessageBody[] newArray(int size) {
      return new LocationMessageBody[size];
    }
  };
}