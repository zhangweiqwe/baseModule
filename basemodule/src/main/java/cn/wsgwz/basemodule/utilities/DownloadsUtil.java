package cn.wsgwz.basemodule.utilities;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import cn.wsgwz.basemodule.BaseApplication;

public class DownloadsUtil {

    public static void removeById(Context context, long id) {
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        dm.remove(id);
    }






    public static DownloadInfo getById(Context context, long id) {
        DownloadManager dm = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor c = dm.query(new DownloadManager.Query().setFilterById(id));
        if (!c.moveToFirst()) {
            c.close();
            return null;
        }

        int columnUri = c.getColumnIndexOrThrow(DownloadManager.COLUMN_URI);
        int columnTitle = c.getColumnIndexOrThrow(DownloadManager.COLUMN_TITLE);
        int columnLastMod = c.getColumnIndexOrThrow(
                DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP);
        int columnLocalUri = c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI);
        int columnStatus = c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS);
        int columnTotalSize = c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
        int columnBytesDownloaded = c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
        int columnReason = c.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON);

        int status = c.getInt(columnStatus);
        String localFilename = getFilenameFromUri(c.getString(columnLocalUri));
        if (status == DownloadManager.STATUS_SUCCESSFUL && !new File(localFilename).isFile()) {
            dm.remove(id);
            c.close();
            return null;
        }

        DownloadInfo info = new DownloadInfo(id, c.getString(columnUri),
                c.getString(columnTitle), c.getLong(columnLastMod),
                localFilename, status,
                c.getInt(columnTotalSize), c.getInt(columnBytesDownloaded),
                c.getInt(columnReason));
        c.close();
        return info;
    }

    public static DownloadInfo getLatestForUrl(Context context, String url) {
        List<DownloadInfo> all = getAllForUrl(context, url);
        return all.isEmpty() ? null : all.get(0);
    }
    public static List<DownloadInfo> getAllForUrl(Context context, String url) {
        DownloadManager dm = (DownloadManager) context
                .getSystemService(Context.DOWNLOAD_SERVICE);
        Cursor c = dm.query(new DownloadManager.Query());
        int columnId = c.getColumnIndexOrThrow(DownloadManager.COLUMN_ID);
        int columnUri = c.getColumnIndexOrThrow(DownloadManager.COLUMN_URI);
        int columnTitle = c.getColumnIndexOrThrow(DownloadManager.COLUMN_TITLE);
        int columnLastMod = c.getColumnIndexOrThrow(
                DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP);
        int columnLocalUri = c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI);
        int columnStatus = c.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS);
        int columnTotalSize = c.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
        int columnBytesDownloaded = c.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
        int columnReason = c.getColumnIndexOrThrow(DownloadManager.COLUMN_REASON);

        List<DownloadInfo> downloads = new ArrayList<>();
        while (c.moveToNext()) {
            if (!url.equals(c.getString(columnUri)))
                continue;

            int status = c.getInt(columnStatus);
            String localFilename = getFilenameFromUri(c.getString(columnLocalUri));
            if (status == DownloadManager.STATUS_SUCCESSFUL && !new File(localFilename).isFile()) {
                dm.remove(c.getLong(columnId));
                continue;
            }

            downloads.add(new DownloadInfo(c.getLong(columnId),
                    c.getString(columnUri), c.getString(columnTitle),
                    c.getLong(columnLastMod), localFilename,
                    status, c.getInt(columnTotalSize),
                    c.getInt(columnBytesDownloaded), c.getInt(columnReason)));
        }
        c.close();

        Collections.sort(downloads);
        return downloads;
    }


    private static String getFilenameFromUri(String uriString) {
        if (uriString == null) {
            return null;
        }
        Uri uri = Uri.parse(uriString);
        if (uri.getScheme().equals("file")) {
            return uri.getPath();
        } else if (uri.getScheme().equals("content")) {
            Context context = BaseApplication.getInstance();
            Cursor c = null;
            try {
                c = context.getContentResolver().query(uri, new String[]{MediaStore.Files.FileColumns.DATA}, null, null, null);
                c.moveToFirst();
                return c.getString(c.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA));
            } finally {
                if (c != null) {
                    c.close();
                }
            }
        } else {
            throw new UnsupportedOperationException("Unexpected URI: " + uriString);
        }
    }


    public static boolean downLoadMangerIsEnable(Context context) {
        int state = context.getApplicationContext().getPackageManager()
                .getApplicationEnabledSetting("com.android.providers.downloads");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                    state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED);
        } else {
            return !(state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED ||
                    state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER);
        }
    }


    public static void openDownloadSetting(Context context){
        try {
            //Open the specific App Info page:
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + "com.android.providers.downloads"));
            context.startActivity(intent);

        } catch ( ActivityNotFoundException e ) {
            e.printStackTrace();

            //Open the generic Apps page:
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            context.startActivity(intent);
        }
    }

    public static class DownloadInfo implements Comparable<DownloadInfo> {
        public final long id;
        public final String url;
        public final String title;
        public final long lastModification;
        public final String localFilename;
        public final int status;
        public final int totalSize;
        public final int bytesDownloaded;
        public final int reason;

        private DownloadInfo(long id, String url, String title, long lastModification, String localFilename, int status, int totalSize, int bytesDownloaded, int reason) {
            this.id = id;
            this.url = url;
            this.title = title;
            this.lastModification = lastModification;
            this.localFilename = localFilename;
            this.status = status;
            this.totalSize = totalSize;
            this.bytesDownloaded = bytesDownloaded;
            this.reason = reason;
        }

        @Override
        public int compareTo(@NonNull DownloadInfo another) {
            int compare = (int) (another.lastModification
                    - this.lastModification);
            if (compare != 0)
                return compare;
            return this.url.compareTo(another.url);
        }

        @Override
        public String toString() {
            return "DownloadInfo{" +
                    "id=" + id +
                    ", url='" + url + '\'' +
                    ", title='" + title + '\'' +
                    ", lastModification=" + lastModification +
                    ", localFilename='" + localFilename + '\'' +
                    ", status=" + status +
                    ", totalSize=" + totalSize +
                    ", bytesDownloaded=" + bytesDownloaded +
                    ", reason=" + reason +
                    '}';
        }
    }
}
