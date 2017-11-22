package org.liaohailong.framelibrary.skin.support;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

import org.liaohailong.framelibrary.skin.attr.SkinAttr;
import org.liaohailong.framelibrary.skin.attr.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * 皮肤属性解析器
 * Created by LHL on 2017/11/21.
 */

public class SkinAttrSupport {
    /**
     * 获取SkinAttr的属性
     *
     * @param context 上下文
     * @param attrs   视图属性
     * @return SkinAttr的属性
     */
    public static List<SkinAttr> getSkinView(Context context, AttributeSet attrs) {
        //background src textColor
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int attrLength = attrs.getAttributeCount();
        for (int index = 0; index < attrLength; index++) {
            //获取名称
            String attrName = attrs.getAttributeName(index);
            //获取属性值
            String attrValue = attrs.getAttributeValue(index);
            //Log.e(TAG, " attrName = " + attrName + " !!!!  attrValue = " + attrValue);
            //只获取重要的
            SkinType skinType = getSkinType(attrName);
            if (skinType != null) {
                //  资源名称 目前只有attrValue 是一个@int类型
                String resName = getResName(context, attrValue);
                if (TextUtils.isEmpty(resName)) {
                    continue;
                }
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                skinAttrs.add(skinAttr);
            }
        }
        return skinAttrs;
    }

    /**
     * 获取资源的名称
     *
     * @param context
     * @param attrValue
     * @return
     */
    private static String getResName(Context context, String attrValue) {
        if (attrValue.startsWith("@")) {
            attrValue = attrValue.substring(1);
            int resId = Integer.parseInt(attrValue);
            //根据id获取到资源名称
            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    /**
     * 通过名称获取SkinType
     *
     * @param attrName 属性名称
     * @return
     */
    private static SkinType getSkinType(String attrName) {
        SkinType[] values = SkinType.values();
        for (SkinType skinType : values) {
            if (skinType.getResName().equalsIgnoreCase(attrName)) {
                return skinType;
            }
        }
        return null;
    }
}
