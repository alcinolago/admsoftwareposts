package com.adm.software.posts.data.repository;

import com.adm.software.posts.data.model.PostResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostRepository {
    @GET("posts")
    Observable<List<PostResponse>> getPosts();

    @GET("posts/{postId}")
    Single<PostResponse> getPostById(@Path("postId") String postId);
}
