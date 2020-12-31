package com.example.administrator.myapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

public class AccessPermission {

    private static void getNetworkStatePermission(Activity activity,Context context){
        String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.ACCESS_NETWORK_STATE};    //请求状态码
        //判断是否已经赋予权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_NETWORK_STATE))
            {
                //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            }
            else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE, PERMISSIONS_STORAGE.length);
            }
        }
    }

    private static void getWRPermission(Activity activity,Context context)
    {
        String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};    //请求状态码
        //判断是否已经赋予权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            }
            else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE, PERMISSIONS_STORAGE.length);
            }
        }
    }

    private static void getGPSPermission(Activity activity,Context context)
    {
        String[] PERMISSIONS_STORAGE = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION};    //请求状态码
        //判断是否已经赋予权限
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,Manifest.permission.ACCESS_FINE_LOCATION))
            {
                //这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
            }
            else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE, PERMISSIONS_STORAGE.length);
            }
        }
    }
}
