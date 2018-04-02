package com.hecticus.eleta.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by Edwin on 2017-11-10.
 */

public class PermissionUtil {

    public static final int CAMERA_AND_STORAGE_PERMISSION_REQUEST_CODE = 3477;
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 5465;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 4512;

    public static final int TAKE_PHOTO_REQUEST_CODE = 6421;
    public static final int GALLERY_IMAGE_REQUEST_CODE = 8617;
    public static final int PRINT_REQUEST_CODE = 4512;

    public static boolean isPermissionGranted(Activity activity, String wantedPermission) {
        return ContextCompat.checkSelfPermission(activity, wantedPermission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestStoragePermission(Object context) {
        if (context instanceof Fragment)
            ((Fragment) context).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
        else
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST_CODE);
    }

    public static void requestCameraAndStoragePermissions(Object context) {
        if (context instanceof Fragment)
            ((Fragment) context).requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_AND_STORAGE_PERMISSION_REQUEST_CODE);
        else
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }

    public static void requestLocationPermission(Object context) {
        if (context instanceof Fragment)
            ((Fragment) context).requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        else
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }
}
