package com.adm.software.posts.domain.interactor;

import com.adm.software.posts.data.model.PostResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface PostRemoteInteractor {
    Observable<List<PostResponse>> getPosts();

    Single<PostResponse> getPostById(String postId);
}
