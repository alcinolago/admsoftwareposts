package com.adm.software.posts.domain.interactor;

import com.adm.software.posts.data.entities.PostEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface PostLocalInteractor {

    Observable<List<PostEntity>> getAll();

    Single<PostEntity> selectPostById(String postId);

    Maybe<Long> addPost(PostEntity postEntity);

    Single<Integer> updatePost(PostEntity postEntity);

    Single<Integer> deletePost(String postId);

    Maybe<List<Long>> addPostListToRoom(List<PostEntity> postEntityList);
}
