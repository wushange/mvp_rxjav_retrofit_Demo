package com.dmcc.chatlibrary.utils;

import android.content.Context;
import android.os.Environment;
import java.io.File;

public class PathUtil
{
  public static String pathPrefix;
  public static final String historyPathName = "/chat/";
  public static final String imagePathName = "/image/";
  public static final String voicePathName = "/voice/";
  public static final String filePathName = "/file/";
  public static final String videoPathName = "/video/";
  public static final String netdiskDownloadPathName = "/netdisk/";
  public static final String meetingPathName = "/meeting/";
  private static File storageDir = null;
  private static PathUtil instance = null;
  private File voicePath = null;
  private File imagePath = null;
  private File historyPath = null;
  private File videoPath = null;
  private File filePath;

  public static PathUtil getInstance()
  {
    if (instance == null)
      instance = new PathUtil();
    return instance;
  }

  public void initDirs(String paramString1, String paramString2, Context paramContext)
  {
    String str = paramContext.getPackageName();
    pathPrefix = "/Android/data/" + str + "/";
    this.voicePath = generateVoicePath(paramString1, paramString2, paramContext);
    if (!this.voicePath.exists())
      this.voicePath.mkdirs();
    this.imagePath = generateImagePath(paramString1, paramString2, paramContext);
    if (!this.imagePath.exists())
      this.imagePath.mkdirs();
    this.historyPath = generateHistoryPath(paramString1, paramString2, paramContext);
    if (!this.historyPath.exists())
      this.historyPath.mkdirs();
    this.videoPath = generateVideoPath(paramString1, paramString2, paramContext);
    if (!this.videoPath.exists())
      this.videoPath.mkdirs();
    this.filePath = generateFiePath(paramString1, paramString2, paramContext);
    if (!this.filePath.exists())
      this.filePath.mkdirs();
  }

  public File getImagePath()
  {
    return this.imagePath;
  }

  public File getVoicePath()
  {
    return this.voicePath;
  }

  public File getFilePath()
  {
    return this.filePath;
  }

  public File getVideoPath()
  {
    return this.videoPath;
  }

  public File getHistoryPath()
  {
    return this.historyPath;
  }

  private static File getStorageDir(Context paramContext)
  {
    if (storageDir == null)
    {
      File localFile = Environment.getExternalStorageDirectory();
      if ((localFile.exists()) && (localFile.canWrite()))
        return localFile;
      storageDir = paramContext.getFilesDir();
    }
    return storageDir;
  }

  private static File generateImagePath(String paramString1, String paramString2, Context paramContext)
  {
    String str = null;
    if (paramString1 == null)
      str = pathPrefix + paramString2 + "/image/";
    else
      str = pathPrefix + paramString1 + "/" + paramString2 + "/image/";
    return new File(getStorageDir(paramContext), str);
  }

  private static File generateVoicePath(String paramString1, String paramString2, Context paramContext)
  {
    String str = null;
    if (paramString1 == null)
      str = pathPrefix + paramString2 + "/voice/";
    else
      str = pathPrefix + paramString1 + "/" + paramString2 + "/voice/";
    return new File(getStorageDir(paramContext), str);
  }

  private static File generateFiePath(String paramString1, String paramString2, Context paramContext)
  {
    String str = null;
    if (paramString1 == null)
      str = pathPrefix + paramString2 + "/file/";
    else
      str = pathPrefix + paramString1 + "/" + paramString2 + "/file/";
    return new File(getStorageDir(paramContext), str);
  }

  private static File generateVideoPath(String paramString1, String paramString2, Context paramContext)
  {
    String str = null;
    if (paramString1 == null)
      str = pathPrefix + paramString2 + "/video/";
    else
      str = pathPrefix + paramString1 + "/" + paramString2 + "/video/";
    return new File(getStorageDir(paramContext), str);
  }

  private static File generateHistoryPath(String paramString1, String paramString2, Context paramContext)
  {
    String str = null;
    if (paramString1 == null)
      str = pathPrefix + paramString2 + "/chat/";
    else
      str = pathPrefix + paramString1 + "/" + paramString2 + "/chat/";
    return new File(getStorageDir(paramContext), str);
  }

  private static File generateMessagePath(String paramString1, String paramString2, Context paramContext)
  {
    File localFile = new File(generateHistoryPath(paramString1, paramString2, paramContext), paramString2 + File.separator + "Msg.db");
    return localFile;
  }

  public static File getTempPath(File paramFile)
  {
    File localFile = new File(paramFile.getAbsoluteFile() + ".tmp");
    return localFile;
  }
}