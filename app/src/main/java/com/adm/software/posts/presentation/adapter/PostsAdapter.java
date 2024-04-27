package com.adm.software.posts.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.adm.software.posts.R;
import com.adm.software.posts.data.model.PostResponse;
import java.util.Collections;
import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsViewHolder> {

    List<PostResponse> list = Collections.emptyList();
    Context context;

    public PostsAdapter(List<PostResponse> list, Context context) {
        this.list = list;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, PostResponse data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    public void remove(PostResponse data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }
}
