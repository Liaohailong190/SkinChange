package org.liaohailong.framelibrary.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.liaohailong.framelibrary.skin.SkinManager;
import org.liaohailong.framelibrary.skin.SkinResource;

/**
 * 皮肤类型
 * Created by LHL on 2017/11/21.
 */

public enum SkinType {
    TEXT_COLOR("textColor") {
        @Override
        public void skin(View view, String resName) {
            SkinResource skinResource = getSkinResource();
            ColorStateList colorStateList = skinResource.getColorByName(resName);
            if (colorStateList == null) {
                return;
            }
            TextView textView = (TextView) view;
            textView.setTextColor(colorStateList);
        }
    },
    BACKGROUND("background") {
        @Override
        public void skin(View view, String resName) {
            //背景可能是图片，也可能是颜色
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable != null) {
                ImageView imageView = (ImageView) view;
                imageView.setBackground(drawable);
                return;
            }
            //可能是颜色
            ColorStateList colorStateList = skinResource.getColorByName(resName);
            if (colorStateList != null) {
                ImageView imageView = (ImageView) view;
                imageView.setBackgroundColor(colorStateList.getDefaultColor());
            }
        }
    },
    SRC("src") {
        @Override
        public void skin(View view, String resName) {
            //获取资源
            SkinResource skinResource = getSkinResource();
            Drawable drawable = skinResource.getDrawableByName(resName);
            if (drawable == null) {
                return;
            }
            ImageView imageView = (ImageView) view;
            imageView.setImageDrawable(drawable);
        }
    };
    // 会根据名字调对应的方法
    private final String mResName;

    SkinType(String resName) {
        this.mResName = resName;
    }

    public abstract void skin(View view, String resName);

    public String getResName() {
        return mResName;
    }


    public SkinResource getSkinResource() {
        return SkinManager.getInstance().getSkinResource();
    }
}
