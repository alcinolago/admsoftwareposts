package com.adm.software.posts.presentation.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adm.software.posts.data.model.PostResponse;
import com.adm.software.posts.domain.interactor.PostRemoteInteractor;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final PostRemoteInteractor postRemoteInteractor;

    @Inject
    MainViewModel(PostRemoteInteractor postRemoteInteractor) {
        this.postRemoteInteractor = postRemoteInteractor;
    }

    MutableLiveData<List<PostResponse>> postMutableLiveData = new MutableLiveData<>();
    Boolean isReady = false;

    void getPosts() {

        postRemoteInteractor.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PostResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<PostResponse> postResponses) {
                        postMutableLiveData.postValue(postResponses);
                        isReady = true;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isReady = false;
                    }

                    @Override
                    public void onComplete() {
                        isReady = true;
                    }
                });
    }
}
