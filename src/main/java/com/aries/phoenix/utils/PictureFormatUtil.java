package com.aries.phoenix.utils;

import com.aries.phoenix.enums.PhotoType;

public class PictureFormatUtil {
    public static String getContentTypeByName(String name) {
        String[] strName = name.split(".");
        PhotoType photoType = PhotoType.getPhotoType(strName[strName.length - 1]);
        if (photoType != null) {
            return "image/" + photoType.getType();
        }
        return null;
    }

    public static String getTypeByName(String name) {
        String[] strName = name.split(".");
        PhotoType photoType = PhotoType.getPhotoType(strName[strName.length - 1]);
        if (photoType != null) {
            return photoType.getType();
        }
        return null;
    }
}
