package com.adm.software.posts.di;

import com.adm.software.posts.data.repository.PostRepository;
import com.adm.software.posts.domain.interactor.PostRemoteInteractor;
import com.adm.software.posts.domain.interactor.PostRemoteInteractorImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class RetrofitModule {

    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    PostRepository provideApiService(Retrofit retrofit) {
        return retrofit.create(PostRepository.class);
    }

    @Provides
    PostRemoteInteractor getPostInteractor(PostRepository postRepository) {
        return new PostRemoteInteractorImpl(postRepository);
    }
}