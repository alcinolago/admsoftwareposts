package com.adm.software.posts.presentation.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.adm.software.posts.R;

public class PostsViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout layout;
    TextView title;
    TextView body;

    PostsViewHolder(View itemView) {
        super(itemView);
        layout = itemView.findViewById(R.id.layout_clickable);
        title = itemView.findViewById(R.id.txvTitle);
        body = itemView.findViewById(R.id.txvBody);
    }
}