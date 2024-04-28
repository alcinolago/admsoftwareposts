package com.adm.software.posts.domain.interactor;

import com.adm.software.posts.data.model.PostResponse;
import com.adm.software.posts.data.repository.PostRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Singleton
public class PostRemoteInteractorImpl implements PostRemoteInteractor {

    @Inject
    PostRepository postRepository;

    @Inject
    public PostRemoteInteractorImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Observable<List<PostResponse>> getPosts() {
        return postRepository.getPosts();
    }

    @Override
    public Single<PostResponse> getPostById(String postId) {
        return postRepository.getPostById(postId);
    }
}
