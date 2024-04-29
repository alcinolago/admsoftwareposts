package com.adm.software.posts.data.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PostEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "post_id")
    int postId = 0;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "body")
    String body;

    public PostEntity(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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
