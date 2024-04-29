package com.adm.software.posts.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.adm.software.posts.data.dao.PostDAO;
import com.adm.software.posts.data.entities.PostEntity;

@Database(entities = {PostEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PostDAO postDAO();
}