package com.adm.software.posts.presentation.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.adm.software.posts.R;

public class PostsViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    TextView body;

    PostsViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.txvTitle);
        body = (TextView) itemView.findViewById(R.id.txvBody);
    }
}