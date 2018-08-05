package com.uowee.fastadapter.demo;


public class HomeItem {
    private int resId;
    private String content;

    public HomeItem() {
    }

    public HomeItem(int resId, String content) {
        this.resId = resId;
        this.content = content;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "HomeItem{" +
                "resId=" + resId +
                ", content='" + content + '\'' +
                '}';
    }
}
