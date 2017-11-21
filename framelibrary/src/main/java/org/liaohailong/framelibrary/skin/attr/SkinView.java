package org.liaohailong.framelibrary.skin.attr;


import android.view.View;

import java.util.List;

/**
 * 需要换肤的视图View
 * Created by LHL on 2017/11/21.
 */

public class SkinView {
    private View mView;
    private List<SkinAttr> mSkinAttrs;//皮肤属性集合

    public SkinView(View view, List<SkinAttr> skinAttrs) {
        this.mView = view;
        this.mSkinAttrs = skinAttrs;
    }

    public void skin() {
        for (SkinAttr mAttr : mSkinAttrs) {
            mAttr.skin(mView);
        }
    }
}
