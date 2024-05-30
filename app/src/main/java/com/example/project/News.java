package com.example.project;

public class News {
    private String title;
    private String url;

    public News(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
