package org.liaohailong.framelibrary.skin.attr;

import android.view.View;

/**
 * 皮肤属性
 * Created by LHL on 2017/11/21.
 */

public class SkinAttr {
    private String mResName;//资源名称 将来准备到皮肤资源包里面去读取的
    private SkinType mType;//换肤的类型

    public SkinAttr(String resName, SkinType skinType) {
        this.mResName = resName;
        this.mType = skinType;
    }

    /**
     * 换肤
     *
     * @param view 需要换肤的view
     */
    public void skin(View view) {
        mType.skin(view, mResName);
    }
}
