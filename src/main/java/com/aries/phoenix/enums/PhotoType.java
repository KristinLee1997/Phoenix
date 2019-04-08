package com.aries.phoenix.enums;


public enum PhotoType {
    JEPG("jpeg"), JPG("jpg"), PNG("png"), GIF("gif"), BMP("bmp");
    private String type;

    PhotoType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static PhotoType getPhotoType(String type) {
        switch (type) {
            case "jpeg":
                return PhotoType.JEPG;
            case "jpg":
                return PhotoType.JPG;
            case "png":
                return PhotoType.PNG;
            case "gif":
                return PhotoType.GIF;
            case "bmp":
                return PhotoType.BMP;
            default:
                return null;
        }
    }
}
