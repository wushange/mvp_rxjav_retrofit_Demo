package com.dmcc.chatlibrary.utils;

import android.content.Context;
import java.text.DecimalFormat;

public class TextFormater
{
  private static final int GB_SP_DIFF = 160;
  private static final int[] secPosvalueList = { 1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600 };
  private static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z' };

  public static String getDataSize(long paramLong)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("###.00");
    if (paramLong < 1024L)
      return paramLong + "bytes";
    if (paramLong < 1048576L)
      return localDecimalFormat.format((float)paramLong / 1024.0F) + "KB";
    if (paramLong < 1073741824L)
      return localDecimalFormat.format((float)paramLong / 1024.0F / 1024.0F) + "MB";
    if (paramLong < 0L)
      return localDecimalFormat.format((float)paramLong / 1024.0F / 1024.0F / 1024.0F) + "GB";
    return "error";
  }

  public static String getKBDataSize(long paramLong)
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("###.00");
    if (paramLong < 1024L)
      return paramLong + "KB";
    if (paramLong < 1048576L)
      return localDecimalFormat.format((float)paramLong / 1024.0F) + "MB";
    if (paramLong < 1073741824L)
      return localDecimalFormat.format((float)paramLong / 1024.0F / 1024.0F) + "GB";
    return "error";
  }

  public static String formatStr(Context paramContext, int paramInt, String paramString)
  {
    String str = paramContext.getText(paramInt).toString();
    return String.format(str, paramString);
  }

  public static String getFirstLetter(String paramString)
  {
    String str = paramString.toLowerCase();
    StringBuffer localStringBuffer = new StringBuffer();
    for (int j = 0; j < str.length(); j++)
    {
      int i = str.charAt(j);
      char[] arrayOfChar = new char[i];
      byte[] arrayOfByte = new String(arrayOfChar).getBytes();
      if ((arrayOfByte[0] < 128) && (arrayOfByte[0] > 0))
        localStringBuffer.append(arrayOfChar);
      else
        localStringBuffer.append(convert(arrayOfByte));
    }
    return localStringBuffer.toString().substring(0, 1);
  }

  private static char convert(byte[] paramArrayOfByte)
  {
    char c = '-';
    int i = 0;
    for (int j = 0; j < paramArrayOfByte.length; j++)
    {
      int tmp12_11 = j;
      paramArrayOfByte[tmp12_11] = ((byte)(paramArrayOfByte[tmp12_11] - 160));
    }
    i = paramArrayOfByte[0] * 100 + paramArrayOfByte[1];
    for (int j = 0; j < 23; j++)
      if ((i >= secPosvalueList[j]) && (i < secPosvalueList[(j + 1)]))
      {
        c = firstLetter[j];
        break;
      }
    return c;
  }
}