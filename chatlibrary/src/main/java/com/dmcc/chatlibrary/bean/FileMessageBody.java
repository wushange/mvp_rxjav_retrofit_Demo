package com.dmcc.chatlibrary.bean;


import com.dmcc.chatlibrary.EMCallBack;

public abstract class FileMessageBody extends MessageBody
{
  public transient EMCallBack downloadCallback = null;
  public transient boolean downloaded = false;
  String fileName = null;
  String localUrl = null;
  String remoteUrl = null;
  String secret = null;

  public void setDownloadCallback(EMCallBack paramEMCallBack)
  {
    if (this.downloaded)
    {
      paramEMCallBack.onProgress(100, null);
      paramEMCallBack.onSuccess();
      return;
    }
    this.downloadCallback = paramEMCallBack;
  }

  public String getFileName()
  {
    return this.fileName;
  }

  public void setFileName(String paramString)
  {
    this.fileName = paramString;
  }

  public String getLocalUrl()
  {
    return this.localUrl;
  }

  public void setLocalUrl(String paramString)
  {
    this.localUrl = paramString;
  }

  public String getRemoteUrl()
  {
    return this.remoteUrl;
  }

  public void setRemoteUrl(String paramString)
  {
    this.remoteUrl = paramString;
  }

  public void setSecret(String paramString)
  {
    this.secret = paramString;
  }

  public String getSecret()
  {
    return this.secret;
  }
}