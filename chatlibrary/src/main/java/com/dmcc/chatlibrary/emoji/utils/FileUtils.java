package com.dmcc.chatlibrary.emoji.utils;

import android.content.Context;
import android.os.Environment;

import com.sj.emoji.EmojiBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtils {

    public static final String DEF_FILEPATH = "/XhsEmoticonsKeyboard/Emoticons/";

    public static String getFolderPath(String folder) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + DEF_FILEPATH + folder;
    }

    public static String getFolderPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + DEF_FILEPATH;
    }


    public static void unzip(InputStream is, String dir) throws IOException {
        File dest = new File(dir);
        if (!dest.exists()) {
            dest.mkdirs();
        }

        if (!dest.isDirectory())
            throw new IOException("Invalid Unzip destination " + dest);
        if (null == is) {
            throw new IOException("InputStream is null");
        }

        ZipInputStream zip = new ZipInputStream(is);

        ZipEntry ze;
        while ((ze = zip.getNextEntry()) != null) {
            final String path = dest.getAbsolutePath()
                    + File.separator + ze.getName();

            String zeName = ze.getName();
            char cTail = zeName.charAt(zeName.length() - 1);
            if (cTail == File.separatorChar) {
                File file = new File(path);
                if (!file.exists()) {
                    if (!file.mkdirs()) {
                        throw new IOException("Unable to create folder " + file);
                    }
                }
                continue;
            }

            FileOutputStream fout = new FileOutputStream(path);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = zip.read(bytes)) != -1) {
                fout.write(bytes, 0, c);
            }
            zip.closeEntry();
            fout.close();
        }
    }

    public static void unzipEmoji(Context context) {
        String filePath = getFolderPath();
        File dest = new File(filePath);
        if (dest.exists()) {
            return;
        }
        try {
            unzip(context.getAssets().open("emoji.zip"), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<EmojiBean> traverseFolder1(String path) {
        ArrayList<EmojiBean> emojiBeanList = new ArrayList<EmojiBean>();
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    list.add(file2);
                    folderNum++;
                } else {
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        list.add(file2);
                        folderNum++;
                    } else {
                        EmojiBean emojiBean = new EmojiBean(fileNum, file2.getAbsolutePath());
                        emojiBeanList.add(emojiBean);
                        fileNum++;
                    }
                }
            }
        } else {
        }

        return emojiBeanList;
    }
}

