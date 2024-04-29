package com.adm.software.posts.domain.interactor;

import com.adm.software.posts.data.database.AppDatabase;
import com.adm.software.posts.data.entities.PostEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class PostLocalInteractorImpl implements PostLocalInteractor {

    private final AppDatabase db;

    @Inject
    public PostLocalInteractorImpl(AppDatabase db) {
        this.db = db;
    }

    @Override
    public Observable<List<PostEntity>> getAll() {
        return db.postDAO().getAll();
    }

    @Override
    public Single<PostEntity> selectPostById(String postId) {
        return db.postDAO().selectPostById(postId);
    }

    @Override
    public Maybe<Long> addPost(PostEntity postEntity) {
        return db.postDAO().addPost(postEntity);
    }

    @Override
    public Single<Integer> updatePost(PostEntity postEntity) {
        return db.postDAO().updatePost(postEntity);
    }

    @Override
    public Single<Integer> deletePost(String postId) {
        return db.postDAO().deletePost(postId);
    }

    @Override
    public Maybe<List<Long>> addPostListToRoom(List<PostEntity> postEntityList) {
        return db.postDAO().addAll(postEntityList);
    }
}
