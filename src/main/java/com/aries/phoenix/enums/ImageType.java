package com.aries.phoenix.enums;

public enum ImageType {
    JEPG("jpeg"), JPG("jpg"), PNG("png"), GIF("gif"), BMP("bmp");
    private String type;

    ImageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static ImageType getPhotoType(String type) {
        switch (type) {
            case "jpeg":
                return ImageType.JEPG;
            case "jpg":
                return ImageType.JPG;
            case "png":
                return ImageType.PNG;
            case "gif":
                return ImageType.GIF;
            case "bmp":
                return ImageType.BMP;
            default:
                return null;
        }
    }

    public static boolean isExist(String type) {
        switch (type) {
            case "jpeg":
            case "jpg":
            case "png":
            case "gif":
            case "bmp":
                return true;
            default:
                return false;
        }
    }
}
