package com.adm.software.posts.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.adm.software.posts.data.entities.PostEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import kotlin.jvm.JvmSuppressWildcards;

@Dao
public interface PostDAO {
    @Query("SELECT * FROM PostEntity")
    Observable<List<PostEntity>> getAll();

    @Query("SELECT * FROM PostEntity WHERE post_id = :postId")
    Single<PostEntity> selectPostById(String postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Maybe<Long> addPost(PostEntity postEntity);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    Single<Integer> updatePost(PostEntity postEntity);

    @Query("DELETE FROM PostEntity WHERE post_id = :postId")
    Single<Integer> deletePost(String postId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    Maybe<List<Long>> addAll(List<PostEntity> postEntityList);
}
