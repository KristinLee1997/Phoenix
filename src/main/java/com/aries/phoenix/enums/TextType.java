package com.aries.phoenix.enums;

public enum TextType {
    TXT("txt"), DOC("doc"), DOCX("docx");
    private String type;

    TextType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static TextType getPhotoType(String type) {
        switch (type) {
            case "txt":
                return TextType.TXT;
            case "doc":
                return TextType.DOC;
            case "docx":
                return TextType.DOCX;
            default:
                return null;
        }
    }

    public static boolean isExist(String type) {
        switch (type) {
            case "txt":
            case "doc":
            case "docx":
                return true;
            default:
                return false;
        }
    }
}
