package com.adm.software.posts.presentation.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adm.software.posts.data.entities.PostEntity;
import com.adm.software.posts.data.model.PostResponse;
import com.adm.software.posts.domain.interactor.PostLocalInteractor;
import com.adm.software.posts.domain.interactor.PostRemoteInteractor;
import com.adm.software.posts.domain.mapper.PostMapperToEntity;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final PostRemoteInteractor postRemoteInteractor;
    private final PostLocalInteractor postLocalInteractor;

    @Inject
    MainViewModel(PostRemoteInteractor postRemoteInteractor, PostLocalInteractor postLocalInteractor) {
        this.postRemoteInteractor = postRemoteInteractor;
        this.postLocalInteractor = postLocalInteractor;
    }

    MutableLiveData<List<PostResponse>> postMutableLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> postSyncedInRoom = new MutableLiveData<>();
    Boolean isReady = false;

    void getPostsFromRemote() {

        postRemoteInteractor.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PostResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<PostResponse> postResponses) {
                        addPostsToRoom(postResponses);
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

    void addPostsToRoom(List<PostResponse> postResponses) {

        List<PostEntity> postEntityList = PostMapperToEntity.objectListToMapper(postResponses);

        postLocalInteractor.addPostListToRoom(postEntityList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<List<Long>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        String t1 = "nada";
                    }

                    @Override
                    public void onSuccess(@NonNull List<Long> longs) {
                        postSyncedInRoom.postValue(true);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        String t2 = "nada";
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    void getPostsFromLocal(){
        postLocalInteractor.getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<PostEntity>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<PostEntity> postEntityList) {
                        postMutableLiveData.postValue(PostMapperToEntity.entityListToMapper(postEntityList));
                        isReady = true;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
