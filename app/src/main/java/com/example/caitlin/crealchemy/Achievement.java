package com.example.caitlin.crealchemy;

public class Achievement {
    private String text;
    private String icon_id;

    public Achievement(String text, String icon_id) {
        this.text = text;
        this.icon_id = icon_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(String icon_id) {
        this.icon_id = icon_id;
    }
}
