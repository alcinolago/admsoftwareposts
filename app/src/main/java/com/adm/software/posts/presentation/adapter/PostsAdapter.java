package com.adm.software.posts.presentation.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adm.software.posts.R;
import com.adm.software.posts.data.model.PostResponse;

import java.util.ArrayList;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsViewHolder> {

    PostsListener listener;
    List<PostResponse> list = new ArrayList<>();

    public PostsAdapter(PostsListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_layout, parent, false);
        return new PostsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(PostsViewHolder viewHolder, final int position) {
        viewHolder.title.setText(list.get(position).getTitle());
        viewHolder.body.setText(list.get(position).getBody());
        viewHolder.layout.setOnClickListener(v -> listener.eventsOnClick(list.get(position)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItems(List<PostResponse> data) {
        this.list.clear();
        this.list.addAll(data);
        notifyDataSetChanged();
    }
}
