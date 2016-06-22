package com.dmcc.chatlibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FileUtils
{
  public static String[] fileTypes = { "apk", "avi", "bmp", "chm", "dll", "doc", "docx", "dos", "gif", "html", "jpeg", "jpg", "movie", "mp3", "dat", "mp4", "mpe", "mpeg", "mpg", "pdf", "png", "ppt", "pptx", "rar", "txt", "wav", "wma", "wmv", "xls", "xlsx", "xml", "zip" };

  public static File[] loadFiles(File paramFile)
  {
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null)
      arrayOfFile = new File[0];
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    for (int i = 0; i < arrayOfFile.length; i++)
    {
      File localObject = arrayOfFile[i];
      if (localObject.isDirectory())
        localArrayList1.add(localObject);
      else if (localObject.isFile())
        localArrayList2.add(localObject);
    }
    MyComparator localMyComparator = new MyComparator();
    Collections.sort(localArrayList1, localMyComparator);
    Collections.sort(localArrayList2, localMyComparator);
    Object localObject = new File[localArrayList1.size() + localArrayList2.size()];
    System.arraycopy(localArrayList1.toArray(), 0, localObject, 0, localArrayList1.size());
    System.arraycopy(localArrayList2.toArray(), 0, localObject, localArrayList1.size(), localArrayList2.size());
    return (File[]) localObject;
  }

  public static String getMIMEType(File paramFile)
  {
    String str1 = "";
    String str2 = paramFile.getName();
    String str3 = str2.substring(str2.lastIndexOf(".") + 1, str2.length()).toLowerCase();
    str1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str3);
    return str1;
  }

  public static String getMIMEType(String paramString)
  {
    String str1 = "";
    String str2 = paramString.substring(paramString.lastIndexOf(".") + 1, paramString.length()).toLowerCase();
    str1 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str2);
    return str1;
  }

  public static void openFile(File paramFile, Activity paramActivity)
  {
    Intent localIntent = new Intent();
    localIntent.addFlags(268435456);
    localIntent.setAction("android.intent.action.VIEW");
    String str = getMIMEType(paramFile);
    localIntent.setDataAndType(Uri.fromFile(paramFile), str);
    try
    {
      paramActivity.startActivity(localIntent);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Toast.makeText(paramActivity, "没有找到打开此类文件的程序", 1).show();
    }
  }

  public static void openFile(Uri paramUri, String paramString, Activity paramActivity)
  {
    Intent localIntent = new Intent();
    localIntent.addFlags(268435456);
    localIntent.setAction("android.intent.action.VIEW");
    localIntent.setDataAndType(paramUri, paramString);
    try
    {
      paramActivity.startActivity(localIntent);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Toast.makeText(paramActivity, "没有找到打开此类文件的程序", 1).show();
    }
  }

  public static synchronized void saveObjectToFile(Object paramObject, File paramFile)
    throws Exception
  {
    ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(new FileOutputStream(paramFile));
    localObjectOutputStream.writeObject(paramObject);
    localObjectOutputStream.flush();
    localObjectOutputStream.close();
  }

  public static synchronized Object readObjectFromFile(File paramFile)
    throws Exception
  {
    ObjectInputStream localObjectInputStream = new ObjectInputStream(new FileInputStream(paramFile));
    Object localObject = localObjectInputStream.readObject();
    return localObject;
  }

  public static class MyComparator
    implements Comparator<File>
  {
    public int compare(File paramFile1, File paramFile2)
    {
      return paramFile1.getName().compareTo(paramFile2.getName());
    }
  }
}