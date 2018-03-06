package com.pingxundata.answerliu.pxshare;

import java.io.Serializable;

/**
 * Created by LH on 2017/11/29.
 * Describe:分享数据源1.0
 */

public class ShareBean implements Serializable {


    private String text;
    private String title;
    private String titleUrl;
    private String imageUrl;
    private String url;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleUrl() {
        return titleUrl;
    }

    public void setTitleUrl(String titleUrl) {
        this.titleUrl = titleUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
