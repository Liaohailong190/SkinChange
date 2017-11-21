package org.liaohailong.framelibrary.skin;

import android.app.Activity;
import android.content.Context;
import android.util.ArrayMap;

import org.liaohailong.framelibrary.skin.attr.SkinView;

import java.util.List;
import java.util.Map;

/**
 * 皮肤管理器
 * Created by LHL on 2017/11/21.
 */

public class SkinManager {

    private SkinResource mSkinResource;

    private static class SingletonHolder {
        static SkinManager INSTANCE = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static Map<Activity, List<SkinView>> mSkinViews = new ArrayMap<>();

    /**
     * 加载皮肤
     *
     * @param skinPath 皮肤路径
     * @return 换肤状态
     */
    public int loadSkin(Context context, String skinPath) {
        //校验签名 增量更新再说

        //最好把皮肤包复制到用户删除不了的地方 cache目录下

        //初始化资源管理
        mSkinResource = new SkinResource(context, skinPath);

        //改变皮肤
        for (Map.Entry<Activity, List<SkinView>> entry : mSkinViews.entrySet()) {
            List<SkinView> skinViews = entry.getValue();
            for (SkinView skinView : skinViews) {
                skinView.skin();
            }
        }

        return 0;
    }

    /**
     * 恢复默认皮肤
     *
     * @return 换肤状态
     */
    public int restoreDefault() {

        return 0;
    }

    /**
     * 通过Activity获取所有需要换肤的视图
     *
     * @param activity
     * @return
     */
    public List<SkinView> getSkinViews(Activity activity) {
        return mSkinViews.get(activity);
    }

    /**
     * 注册绑定视图
     *
     * @param activity
     * @param skinViews
     */
    public void register(Activity activity, List<SkinView> skinViews) {
        mSkinViews.put(activity, skinViews);
    }

    /**
     * 注销绑定视图
     *
     * @param activity
     */
    public void unregister(Activity activity) {
        mSkinViews.remove(activity);
    }

    /**
     * 获取当前皮肤资源管理
     *
     * @return 皮肤资源管理
     */
    public SkinResource getSkinResource() {
        return mSkinResource;
    }
}
