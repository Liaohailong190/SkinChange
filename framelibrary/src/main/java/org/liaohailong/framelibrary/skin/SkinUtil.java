package org.liaohailong.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.util.DisplayMetrics;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 换肤过程中需要用到的工具类
 * Created by LHL on 2017/11/22.
 */

public class SkinUtil {

    /**
     * 获取当前apk的签名
     *
     * @param context 上下文
     * @return 当前apk的签名
     */
    public static String getSignature(Context context) {
        // 通过Context获取当前包名
        String currentApkPackageName = context.getApplicationInfo().packageName;

        // 通过PackageManager获取所有应用的PackageInfo信息
        PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(PackageManager.GET_SIGNATURES);
        for (PackageInfo packageInfo : packageInfos) {
            if (packageInfo.packageName.equals(currentApkPackageName)) {
                // 获取签名
                return packageInfo.signatures[0].toCharsString();
            }
        }
        return null;
    }


    /**
     * 获取该目录下的apk签名
     *
     * @param path 当前apk路径
     * @return Apk签名
     */
    public static String getSignature(String path) throws Exception {
        // 1.反射实例化PackageParser对象
        Object packageParser = getPackageParser(path);

        // 2.反射获取parsePackage方法
        Object packageObject = getPackageInfo(path, packageParser);

        // 3.调用collectCertificates方法
        Method collectCertificatesMethod = packageParser.getClass().
                getDeclaredMethod("collectCertificates", packageObject.getClass(), int.class);
        collectCertificatesMethod.invoke(packageParser, packageObject, 0);

        // 4.获取mSignatures属性
        Field signaturesField = packageObject.getClass().getDeclaredField("mSignatures");
        signaturesField.setAccessible(true);
        Signature[] mSignatures = (Signature[]) signaturesField.get(packageObject);
        return mSignatures[0].toCharsString();
    }

    /**
     * 创建PackageParser.Package类，兼容5.0
     *
     * @param path 当前apk路径
     */
    private static Object getPackageInfo(String path, Object packageParser) throws Exception {
        if (Build.VERSION.SDK_INT >= 21) {
            Class<?>[] paramClass = new Class[2];
            paramClass[0] = File.class;
            paramClass[1] = int.class;
            Method parsePackageMethod = packageParser.getClass().getDeclaredMethod("parsePackage", paramClass);

            // 3.反射执行parsePackage方法
            Object[] paramObject = new Object[2];
            paramObject[0] = new File(path);
            paramObject[1] = 0;
            parsePackageMethod.setAccessible(true);
            return parsePackageMethod.invoke(packageParser, paramObject);
        } else {
            Class<?>[] paramClass = new Class[4];
            paramClass[0] = File.class;
            paramClass[1] = String.class;
            paramClass[2] = DisplayMetrics.class;
            paramClass[3] = int.class;
            Method parsePackageMethod = packageParser.getClass().getDeclaredMethod("parsePackage", paramClass);

            // 3.反射执行parsePackage方法
            Object[] paramObject = new Object[4];
            paramObject[0] = new File(path);
            paramObject[1] = path;
            DisplayMetrics metrics = new DisplayMetrics();
            metrics.setToDefaults();
            paramObject[2] = metrics;
            paramObject[3] = 0;
            parsePackageMethod.setAccessible(true);
            return parsePackageMethod.invoke(packageParser, paramObject);
        }
    }

    /**
     * 创建PackageParser类
     *
     * @param path 当前apk路径
     */
    private static Object getPackageParser(String path) throws Exception {
        Class<?> packageParserClazz = Class.forName("android.content.pm.PackageParser");
        // 版本兼容
        if (Build.VERSION.SDK_INT >= 21) {
            Constructor<?> packageParserConstructor = packageParserClazz.getDeclaredConstructor();
            return packageParserConstructor.newInstance();
        } else {
            Constructor<?> packageParserConstructor = packageParserClazz.getDeclaredConstructor(String.class);
            return packageParserConstructor.newInstance(path);
        }
    }
}
