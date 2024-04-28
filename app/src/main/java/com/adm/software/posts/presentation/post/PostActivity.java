package com.adm.software.posts.presentation.post;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.adm.software.posts.Constants;
import com.adm.software.posts.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostActivity extends AppCompatActivity {
    private PostViewModel postViewModel;
    Toolbar postToolbar;
    TextInputEditText inputTitle;
    TextInputEditText inputBody;
    FloatingActionButton fabAddPost;
    String CREATE_POST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        initViews();
        setUpToolbar();
        getIntentData();
        observeViewModel();
    }

    private void initViews() {
        postToolbar = findViewById(R.id.toolbar_post);
        inputTitle = findViewById(R.id.textInputEditTextTitle);
        inputBody = findViewById(R.id.textInputEditTextBody);
        fabAddPost = findViewById(R.id.fabSavePost);
    }

    private void setUpToolbar() {
        setSupportActionBar(postToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (CREATE_POST != null) {
            return false;
        }

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Integer postId = bundle.getInt("postId");
        CREATE_POST = bundle.getString(Constants.CREATE_POST);

        postViewModel.getPostById(postId);
    }

    private void observeViewModel() {
        postViewModel.postSelectedMutableLiveData.observe(this, postSelected -> {
            inputTitle.setText(postSelected.getTitle());
            inputBody.setText(postSelected.getBody());
        });
    }
}