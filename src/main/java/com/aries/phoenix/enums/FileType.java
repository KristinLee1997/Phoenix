package com.aries.phoenix.enums;


public enum FileType {
    JEPG("jpeg"), JPG("jpg"), PNG("png"), GIF("gif"), BMP("bmp"), TXT("txt"), DOC("doc"), DOCX("docx");
    private String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static FileType getPhotoType(String type) {
        switch (type) {
            case "jpeg":
                return FileType.JEPG;
            case "jpg":
                return FileType.JPG;
            case "png":
                return FileType.PNG;
            case "gif":
                return FileType.GIF;
            case "bmp":
                return FileType.BMP;
            case "txt":
                return FileType.TXT;
            case "doc":
                return FileType.DOC;
            case "docx":
                return FileType.DOCX;
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
            case "txt":
            case "doc":
            case "docx":
                return true;
            default:
                return false;
        }
    }
}
