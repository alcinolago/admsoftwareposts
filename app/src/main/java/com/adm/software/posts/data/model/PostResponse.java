package com.adm.software.posts.data.model;

import com.google.gson.annotations.SerializedName;

public class PostResponse {
    @SerializedName("id") Integer id;
    @SerializedName("userId") Integer userId;
    @SerializedName("title") String title;
    @SerializedName("body") String body;

    public PostResponse(Integer id, Integer userId, String title, String body){
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.body = body;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
