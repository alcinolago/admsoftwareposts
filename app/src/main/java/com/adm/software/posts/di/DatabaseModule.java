package com.adm.software.posts.di;

import android.content.Context;

import androidx.room.Room;

import com.adm.software.posts.data.dao.PostDAO;
import com.adm.software.posts.data.database.AppDatabase;
import com.adm.software.posts.domain.interactor.PostLocalInteractor;
import com.adm.software.posts.domain.interactor.PostLocalInteractorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class DatabaseModule {

    @Provides
    PostLocalInteractor getLocalPostInteractor(AppDatabase db) {
        return new PostLocalInteractorImpl(db);
    }

    @Provides
    PostDAO provideChannelDao(AppDatabase appDatabase) {
        return appDatabase.postDAO();
    }

    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(@ApplicationContext Context appContext) {
        return Room.databaseBuilder(
                appContext,
                AppDatabase.class,
                "Post"
        ).build();
    }
}
