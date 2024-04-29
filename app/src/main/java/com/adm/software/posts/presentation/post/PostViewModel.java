package com.adm.software.posts.presentation.post;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adm.software.posts.data.entities.PostEntity;
import com.adm.software.posts.data.model.PostResponse;
import com.adm.software.posts.domain.interactor.PostLocalInteractor;
import com.adm.software.posts.domain.mapper.PostMapperToEntity;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PostViewModel extends ViewModel {

    private final PostLocalInteractor postLocalInteractor;

    MutableLiveData<PostResponse> postSelectedMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Long> addPostToRoomMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> updatePostToRoomMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Integer> deletePostToRoomMutableLiveData = new MutableLiveData<>();

    @Inject
    PostViewModel(PostLocalInteractor postLocalInteractor) {
        this.postLocalInteractor = postLocalInteractor;
    }

    void getPostById(String postId) {

        postLocalInteractor.selectPostById(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PostEntity>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull PostEntity postEntity) {
                        postSelectedMutableLiveData.postValue(PostMapperToEntity.entityToMapper(postEntity));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    void createPost(PostEntity postEntity) {
        postLocalInteractor.addPost(postEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Long aLong) {
                        addPostToRoomMutableLiveData.postValue(aLong);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void updatePost(PostEntity postEntity) {
        postLocalInteractor.updatePost(postEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        updatePostToRoomMutableLiveData.postValue(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    void deletePost(PostEntity postEntity) {
        postLocalInteractor.deletePost(String.valueOf(postEntity.getPostId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {
                        deletePostToRoomMutableLiveData.postValue(integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
