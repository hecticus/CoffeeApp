package com.hecticus.eleta.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import hugo.weaving.DebugLog;

/**
 * Created by roselyn545 on 16/9/17.
 */

public class FileUtils {
    private static final int MAX_IMAGE_DIMENSION = 400;

    static String nopath = "Select Video Only";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @SuppressLint("NewApi")
    @DebugLog
    public static String getLocalPathGivenUriAndContext(Uri uri, Context contextParam) {
        // check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(contextParam, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(contextParam, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(contextParam, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(contextParam, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return nopath;
    }

    /**
     * Get the value of the data column for this Uri. This is <span id="IL_AD2"
     * class="IL_AD">useful</span> for MediaStore Uris, and other file-based
     * ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return nopath;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    @DebugLog
    public static File getTempFolder() {
        return (new File(getTempFolderPath()));
    }

    @DebugLog
    private static String getTempFolderPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/.eleta_tmp/";
    }

    @DebugLog
    public static String getTempImagePath() {
        return getTempFolderPath() + "temp.jpg";
    }

    public static String writeTempImageAndReturnUri(Bitmap bitmapParam) {
        File tempFolder = getTempFolder();

        if (tempFolder.exists() || tempFolder.mkdirs()) {
            try {
                clearTempImages();

                File tempAvatarFile = new File(tempFolder, "tempavatar.jpg");

                FileOutputStream fos = new FileOutputStream(tempAvatarFile);
                bitmapParam.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();
                return tempAvatarFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void deleteFileRecursive(File fileOrDirectory, boolean isTopLevel) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteFileRecursive(child, false);

        if (!isTopLevel)
            fileOrDirectory.delete();
    }

    public static void clearTempImages() {
        deleteFileRecursive(getTempFolder(), true);
    }

    @DebugLog
    public static String getOptimizedBase64FromImagePath(String takenOrPickedImagePath) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap;

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) MAX_IMAGE_DIMENSION);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) MAX_IMAGE_DIMENSION);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;

        bitmap = BitmapFactory.decodeFile(takenOrPickedImagePath, bmpFactoryOptions);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);

        return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

    }
}