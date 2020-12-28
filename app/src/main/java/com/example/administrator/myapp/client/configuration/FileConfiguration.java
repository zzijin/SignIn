package com.example.administrator.myapp.client.configuration;

import android.os.Environment;

import java.io.File;

public class FileConfiguration {
    /**
     * 系统缓存目录
     */
    static final public File CACHE_PATH= Environment.getDownloadCacheDirectory();
    /**
     * 系统相机照片和视频目录
     */
    static final public File CAMERA_PATH=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    /**
     * 系统音乐目录
     */
    static final public File MUSIC_PATH=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
    /**
     * 系统下载目录
     */
    static final public File DOWN_PATH=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    /**
     * 系统图片目录
     */
    static final public File PICTURES_PATH=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
    /**
     * 系统电影目录
     */
    static final public File MOVIES_PATH=Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);
}
