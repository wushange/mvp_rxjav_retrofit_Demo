package com.dmcc.chatlibrary.utils;

public class EaseMobException extends Exception
{
  protected int errorCode = -1;
  private static final long serialVersionUID = 1L;

  public EaseMobException()
  {
  }

  public EaseMobException(String paramString)
  {
    super(paramString);
  }

  public EaseMobException(String paramString, Throwable paramThrowable)
  {
    super(paramString);
    super.initCause(paramThrowable);
  }

  public EaseMobException(int paramInt, String paramString)
  {
    super(paramString);
    this.errorCode = paramInt;
  }

  public int getErrorCode()
  {
    return this.errorCode;
  }

  public void setErrorCode(int paramInt)
  {
    this.errorCode = paramInt;
  }
}