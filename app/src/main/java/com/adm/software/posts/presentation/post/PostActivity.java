package com.adm.software.posts.presentation.post;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.adm.software.posts.Constants;
import com.adm.software.posts.R;
import com.adm.software.posts.data.entities.PostEntity;
import com.adm.software.posts.data.model.PostResponse;
import com.adm.software.posts.domain.mapper.PostMapperToEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PostActivity extends AppCompatActivity {
    private PostViewModel postViewModel;
    Toolbar postToolbar;
    PostResponse postResponse;
    TextInputLayout layoutInputTitle;
    TextInputLayout layoutInputBody;
    TextInputEditText inputTitle;
    TextInputEditText inputBody;
    FloatingActionButton fabAddPost;
    String CREATE_POST;
    String FIELD_TITLE_VALUE;
    String FIELD_BODY_VALUE;

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
        layoutInputTitle = findViewById(R.id.textInputLayoutTitle);
        layoutInputBody = findViewById(R.id.textInputLayoutBody);
        fabAddPost = findViewById(R.id.fabSavePost);
        fabAddPost.setOnClickListener(v -> {
            if (CREATE_POST != null) {
                if (validateDataEntry()) {
                    PostEntity postEntity = PostMapperToEntity.objcetToMapper(prepareData());
                    postViewModel.createPost(postEntity);
                }
            } else {
                if (validateDataEntry()) {
                    PostEntity postEntity = PostMapperToEntity.objcetToMapper(prepareData());
                    postViewModel.updatePost(postEntity);
                }
            }
        });
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
        if (item.getItemId() == R.id.deletePost) {
            if (validateDataEntry()) {
                PostEntity postEntity = PostMapperToEntity.objcetToMapper(postResponse);
                postViewModel.deletePost(postEntity);
            }
            return true;
        }
        return false;
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Long postId = bundle.getLong("postId");
        CREATE_POST = bundle.getString(Constants.CREATE_POST);

        postViewModel.getPostById(String.valueOf(postId));
    }

    private PostResponse prepareData() {
        return new PostResponse(FIELD_TITLE_VALUE, FIELD_BODY_VALUE);
    }

    private void observeViewModel() {
        postViewModel.postSelectedMutableLiveData.observe(this, postSelected -> {
            postResponse = postSelected;
            inputTitle.setText(postSelected.getTitle());
            inputBody.setText(postSelected.getBody());
        });

        postViewModel.addPostToRoomMutableLiveData.observe(this, postCreated -> {
            Toast.makeText(this, "Post criado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });

        postViewModel.updatePostToRoomMutableLiveData.observe(this, updatedPost -> {
            Toast.makeText(this, "Post atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });

        postViewModel.deletePostToRoomMutableLiveData.observe(this, deletedPost -> {
            Toast.makeText(this, "Post removido com sucesso!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private Boolean validateDataEntry() {
        FIELD_TITLE_VALUE = Objects.requireNonNull(inputTitle.getText()).toString().trim();
        FIELD_BODY_VALUE = Objects.requireNonNull(inputBody.getText()).toString().trim();

        if (FIELD_TITLE_VALUE.isEmpty()) {
            layoutInputTitle.setError(getString(R.string.error_input_required));
            inputTitle.requestFocus();
            return false;
        }

        if (FIELD_BODY_VALUE.isEmpty()) {
            layoutInputBody.setError(getString(R.string.error_input_required));
            inputBody.requestFocus();
            return false;
        }

        return true;
    }
}