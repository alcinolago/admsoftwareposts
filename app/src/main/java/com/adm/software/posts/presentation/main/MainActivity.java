package com.adm.software.posts.presentation.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adm.software.posts.Constants;
import com.adm.software.posts.R;
import com.adm.software.posts.data.entities.PostEntity;
import com.adm.software.posts.domain.mapper.PostMapperToEntity;
import com.adm.software.posts.presentation.adapter.PostsAdapter;
import com.adm.software.posts.presentation.adapter.PostsListener;
import com.adm.software.posts.presentation.post.PostActivity;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity implements PostsListener, ViewTreeObserver.OnPreDrawListener {

    private MainViewModel mainViewModel;
    private View content;
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    PostsAdapter adapter = new PostsAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initViews();
        setupSplashScreen();
        setUpToolbar();
        setUpRecyclerView();
        observePosts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkFirstsSyncData();
    }

    private void initViews() {
        content = findViewById(android.R.id.content);
        toolbar = findViewById(R.id.toolbar_main);
        recyclerView = findViewById(R.id.recyclerview);
    }

    private void setupSplashScreen() {
        content.getViewTreeObserver().addOnPreDrawListener(
                () -> {
                    if (mainViewModel.isReady) {
                        content.getViewTreeObserver().removeOnPreDrawListener(this);
                        return true;
                    }
                    return false;
                });
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemToolbarAddPost) {
            Intent intent = new Intent(this, PostActivity.class);
            Bundle dataBundle = new Bundle();
            dataBundle.putString(Constants.CREATE_POST, Constants.CREATE_POST);
            intent.putExtras(dataBundle);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private void setUpRecyclerView() {
        LinearLayoutManager layout = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);

        recyclerView.setHasFixedSize(true);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setLayoutManager(layout);
        recyclerView.setAdapter(adapter);
    }

    public void observePosts() {
        mainViewModel.postMutableLiveData.observe(this, postResponses -> {
            if (postResponses != null && !postResponses.isEmpty()) {
                List<PostEntity> postEntity = PostMapperToEntity.objectListToMapper(postResponses);
                adapter.addItems(postEntity);
            }
        });

        mainViewModel.postSyncedInRoom.observe(this, postsSynced -> {
            if (postsSynced) {
                addFirstsSyncDataInPreferences();
                mainViewModel.getPostsFromLocal();
            }
        });
    }

    @Override
    public void eventsOnClick(PostEntity post) {
        Intent postIntent = new Intent(this, PostActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("postId", post.getPostId());
        dataBundle.putString(Constants.VIEW_POST, Constants.VIEW_POST);
        postIntent.putExtras(dataBundle);
        startActivity(postIntent);
    }

    private void checkFirstsSyncData() {

        SharedPreferences preferences = getSharedPreferences(Constants.FIRST_SYNC, Context.MODE_PRIVATE);

        if (preferences.contains(Constants.FIRST_SYNC)) {
            mainViewModel.getPostsFromLocal();
        } else {
            mainViewModel.getPostsFromRemote();
        }
    }

    private void addFirstsSyncDataInPreferences() {

        SharedPreferences preferences = getSharedPreferences(Constants.FIRST_SYNC, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Constants.FIRST_SYNC, true);
        editor.apply();
    }

    @Override
    public boolean onPreDraw() {
        return false;
    }
}