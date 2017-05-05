package com.lvzhongyi.mvp;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.jiongbull.jlog.JLog;

/**
 * @author lvzhongyi
 *         <p>
 *         description
 *         date 16/7/23
 *         email lvzhongyiforchrome@gmail.com
 *         </p>
 */
public class BaseApp extends Application {
    /**
     * 版本号.
     */
    private static int versionNumber;
    /**
     * 版本名称
     */
    private static String versionName;

    /**
     * SD卡更目录
     */
    private static final String SD_PATH = Environment.getExternalStorageDirectory().getPath() + "/";
    /**
     * Global application context.
     */
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initVersion();
    }

    /**
     * 得到版本号
     *
     * @return 版本号
     */
    public static int getVersionNumber() {
        return versionNumber;
    }

    /**
     * 得到SD卡根目录
     *
     * @return sd根目录
     */
    public static String getSdPath() {
        return SD_PATH;
    }

    /**
     * 得到版本名
     *
     * @return 版本名
     */
    public static String getVersionName() {
        return versionName;
    }


    public static void initialize(Context context) {
        sContext = context;
    }

    /**
     * Get the global application context.
     *
     * @return Application context
     */
    public static Context getContext() {
        return sContext;
    }

    /**
     * 初始化版本号和版本名称
     */
    private void initVersion() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionNumber = packageInfo.versionCode;
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

}
