package com.roydon.niuyin.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class PicSaveUtil {
    private static final String TAG = "PicSaveUtil";
    public final static int SAVE_PATH_TYPE_DCIM = 1001;
    public final static int SAVE_PATH_TYPE_DATA = 1002;

    /**
     * @description //保存图片
     * @param imgurl, context, savePathType 网络地址/ context / 保存到哪里
     * @return void
     * @author yuqingfan
     * @time 2021/9/2 10:48
     */
    public static void savePic(String imgurl, Context context, int savePathType, String fileName) {

        new Thread(() -> url2bitmap(imgurl, context, savePathType, fileName)).start();
    }

    /**
     * url转bitmap对象
     *
     * @param
     * @param context
     * @param savePathType
     * @param fileName
     */
    public static void url2bitmap(String imgPath, Context context, int savePathType, String fileName) {

        HttpURLConnection conn = null;
        InputStream is = null;
        try {
            URL url = new URL(imgPath);
            //开启连接
            conn = (HttpURLConnection) url.openConnection();
            //设置连接超时
            conn.setConnectTimeout(5000);
            //设置请求方式
            conn.setRequestMethod("GET");
            //conn.connect();
            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
                Bitmap b = BitmapFactory.decodeStream(is);
                if (b != null) {
                    //saveImageToGallery(context, b);
                    if (savePathType == SAVE_PATH_TYPE_DCIM) {
                        addBitmapToAlbum(context, b, fileName, "jpg", Bitmap.CompressFormat.JPEG);
                    } else if (savePathType == SAVE_PATH_TYPE_DATA) {
                        saveImageToGallery(context, b, fileName);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //用完记得关闭
                is.close();
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void addBitmapToAlbum(Context context, Bitmap bitmap, String displayName, String mimeType, Bitmap.CompressFormat compressFormat) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);
        } else {
            values.put(MediaStore.MediaColumns.DATA, Environment.getExternalStorageDirectory().getPath() + "/"
                    + Environment.DIRECTORY_DCIM + "/" + displayName);
        }
        ContentResolver resolver = context.getContentResolver();
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            OutputStream outputStream = null;
            try {
                outputStream = resolver.openOutputStream(uri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (outputStream != null) {
                bitmap.compress(compressFormat, 100, outputStream);
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveImageToGallery(Context context, Bitmap bmp, String fileName) {
        Log.d(TAG, "saveImageToGallery: 保存了图片");
        // 首先保存图片
        File appDir = new File(Objects.requireNonNull(context.getExternalFilesDir("")).getAbsoluteFile() + "/pics");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}