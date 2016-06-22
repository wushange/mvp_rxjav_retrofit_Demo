package com.dmcc.chatlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class LatLng
  implements Parcelable
{
  public double latitude;
  public double longitude;

  public LatLng(double paramDouble1, double paramDouble2)
  {
    this.latitude = paramDouble1;
    this.longitude = paramDouble2;
  }

  public LatLng(Parcel paramParcel)
  {
    this.latitude = paramParcel.readDouble();
    this.longitude = paramParcel.readDouble();
  }

  public int describeContents()
  {
    return 0;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.latitude);
    paramParcel.writeDouble(this.longitude);
  }
}