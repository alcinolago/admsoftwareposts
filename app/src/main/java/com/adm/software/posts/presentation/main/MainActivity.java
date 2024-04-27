package com.adm.software.posts.presentation.main;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adm.software.posts.R;
import com.adm.software.posts.data.model.PostResponse;
import com.adm.software.posts.presentation.adapter.PostsAdapter;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        List<PostResponse> data = fill_with_data();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        PostsAdapter adapter = new PostsAdapter(data, getApplication());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public List<PostResponse> fill_with_data() {

        List<PostResponse> data = new ArrayList<>();

        data.add(new PostResponse(1,1,"Batman vs Superman", "Following the destruction of Metropolis, Batman embarks on a personal vendetta against Superman"));
        data.add(new PostResponse(2,1,"X-Men: Apocalypse", "X-Men: Apocalypse is an upcoming American superhero film based on the X-Men characters that appear in Marvel Comics"));
        data.add(new PostResponse(3,1,"Captain America: Civil War", "A feud between Captain America and Iron Man leaves the Avengers in turmoil."));
        data.add(new PostResponse(4,1,"Kung Fu Panda 3", "After reuniting with his long-lost father, Po  must train a village of pandas"));
        data.add(new PostResponse(5,1,"Warcraft", "Fleeing their dying home to colonize another, fearsome orc warriors invade the peaceful realm of Azeroth."));
        data.add(new PostResponse(6,1,"Alice in Wonderland", "Alice in Wonderland: Through the Looking Glass"));

        return data;
    }
}