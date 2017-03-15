package com.opens.android.opensource.discover;

/**
 * Created by ttc on 2017/3/14.
 */

public class Software {
    private  String tag;
    private String name;
    private String description;
    private String url ;


    public String getTag() {
        return tag;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setDescription(String abs) {
        this.description = abs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
