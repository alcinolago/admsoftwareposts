package com.adm.software.posts.presentation.post;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adm.software.posts.data.model.PostResponse;
import com.adm.software.posts.domain.interactor.PostRemoteInteractor;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PostViewModel extends ViewModel {

    private final PostRemoteInteractor postRemoteInteractor;

    @Inject
    PostViewModel(PostRemoteInteractor postRemoteInteractor) {
        this.postRemoteInteractor = postRemoteInteractor;
    }

    MutableLiveData<PostResponse> postSelectedMutableLiveData = new MutableLiveData<>();

    void getPostById(Integer postId) {

        postRemoteInteractor.getPostById(String.valueOf(postId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PostResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull PostResponse postResponse) {
                        postSelectedMutableLiveData.postValue(postResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }
}
