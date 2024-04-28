package com.adm.software.posts.presentation.main;

import android.content.Intent;
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
import com.adm.software.posts.data.model.PostResponse;
import com.adm.software.posts.presentation.adapter.PostsAdapter;
import com.adm.software.posts.presentation.adapter.PostsListener;
import com.adm.software.posts.presentation.post.PostActivity;

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
        setUpAdapterRecyclerView();
        observePosts();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainViewModel.getPosts();
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

    private void setUpAdapterRecyclerView() {


    }

    public void observePosts() {
        mainViewModel.postMutableLiveData.observe(this, postResponses -> {
            if (postResponses != null && !postResponses.isEmpty()) {
                adapter.addItems(postResponses);
            }
        });
    }

    @Override
    public void eventsOnClick(PostResponse post) {
        Intent postIntent = new Intent(this, PostActivity.class);
        Bundle dataBundle = new Bundle();
        dataBundle.putInt("postId", post.getId());
        dataBundle.putString(Constants.VIEW_POST, Constants.VIEW_POST);
        postIntent.putExtras(dataBundle);
        startActivity(postIntent);
    }

    @Override
    public boolean onPreDraw() {
        return false;
    }
}