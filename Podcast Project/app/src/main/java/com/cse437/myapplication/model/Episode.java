package com.cse437.myapplication.model;

public class Episode {
    public final String title;
    public final String description;
    public final String url;
    public final String length;

    public Episode(String title, String description, String url, String length) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.length = length;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getLength() {
        return length;
    }
}
