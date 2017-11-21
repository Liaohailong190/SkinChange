package org.liaohailong.framelibrary.skin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import java.lang.reflect.Method;

/**
 * 皮肤资源控制器
 * Created by LHL on 2017/11/21.
 */

public class SkinResource {
    //资源通过此对象获取
    private Resources mSkinResources;//插件皮肤包的资源管理者
    private String mPackageName;//插件皮肤包的包名

    public SkinResource(Context context, String skinPath) {
        try {
            //读取本地的一个.skin里面的资源
            Resources resources = context.getResources();
            //创建AssetManager
            AssetManager asset = AssetManager.class.newInstance();
            //添加本地下载好的资源皮肤 Native层C和C++怎么搞的
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);

            method.setAccessible(true);
            method.invoke(asset, skinPath);

            mSkinResources = new Resources(asset, resources.getDisplayMetrics(), resources.getConfiguration());

            //获取包名
            mPackageName = context.getPackageManager().
                    getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
                    .packageName;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 通过名字获取Drawable
     */
    public Drawable getDrawableByName(String resName) {
        Drawable drawable = null;
        try {
            int resId = mSkinResources.getIdentifier(resName, "drawable", mPackageName);
            drawable = mSkinResources.getDrawable(resId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return drawable;
    }

    /**
     * 通过名字获取颜色
     */
    public ColorStateList getColorByName(String resName) {
        ColorStateList colorStateList = null;
        try {
            int resId = mSkinResources.getIdentifier(resName, "color", mPackageName);
            colorStateList = mSkinResources.getColorStateList(resId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return colorStateList;
    }
}
