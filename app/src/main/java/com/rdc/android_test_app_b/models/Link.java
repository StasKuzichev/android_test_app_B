package com.rdc.android_test_app_b.models;

public class Link {
    private long id;
    private String url;
    private int status;
    private String createdAt;

    public Link(long id, String url, int status, String createdAt) {
        this.id = id;
        this.url = url;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Link() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String toString() {
        return "Link id: " + getId() + "\n" +
                "URL: " + getUrl() + "\n" +
                "Status: " + getStatus() + "\n" +
                "Created At: " + getCreatedAt();
    }
}
