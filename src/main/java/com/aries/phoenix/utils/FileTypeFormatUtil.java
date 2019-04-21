package com.aries.phoenix.utils;

import com.aries.phoenix.enums.FileType;

public class FileTypeFormatUtil {
    public static String getNameByOriginFileName(String name) {
        String[] strName = name.split("\\.");
        FileType photoType = FileType.getPhotoType(strName[0]);
        if (photoType != null) {
            return photoType.getType();
        }
        return null;
    }

    public static String getTypeByOriginFileName(String name) {
        String[] strName = name.split("\\.");
        FileType photoType = FileType.getPhotoType(strName[strName.length - 1]);
        if (photoType != null) {
            return photoType.getType();
        }
        return null;
    }

    public static String getContentTypeByName(String name) {
        String type = getTypeByOriginFileName(name);
        if (type != null) {
            return "image/" + type;
        } else {
            return type;
        }
    }
}
