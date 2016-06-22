package com.dmcc.chatlibrary.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EMContact
  implements Parcelable
{
  protected String eid;
  protected String username;
  protected String nick;


  public void setUsername(String paramString)
  {
    this.username = paramString;
  }

  public String getUsername()
  {
    return this.username;
  }

  public void setNick(String paramString)
  {
    this.nick = paramString;
  }

  public String getNick()
  {
    if (this.nick == null)
      return this.username;
    return this.nick;
  }

  public int compare(EMContact paramEMContact)
  {
    return getNick().compareTo(paramEMContact.getNick());
  }

  public String toString()
  {
    return "<contact jid:" + this.eid + ", username:" + this.username + ", nick:" + this.nick + ">";
  }

  public String getEid()
  {
    return this.eid;
  }

  public void setEid(String paramString)
  {
    this.eid = paramString;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.eid);
    dest.writeString(this.username);
    dest.writeString(this.nick);
  }

  public EMContact() {
  }

  protected EMContact(Parcel in) {
    this.eid = in.readString();
    this.username = in.readString();
    this.nick = in.readString();
  }

  public static final Creator<EMContact> CREATOR = new Creator<EMContact>() {
    @Override
    public EMContact createFromParcel(Parcel source) {
      return new EMContact(source);
    }

    @Override
    public EMContact[] newArray(int size) {
      return new EMContact[size];
    }
  };
}