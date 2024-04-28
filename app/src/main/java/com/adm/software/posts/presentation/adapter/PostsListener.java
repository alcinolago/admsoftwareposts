package com.adm.software.posts.presentation.adapter;

import com.adm.software.posts.data.model.PostResponse;

public interface PostsListener {
    void eventsOnClick(PostResponse post);
}
