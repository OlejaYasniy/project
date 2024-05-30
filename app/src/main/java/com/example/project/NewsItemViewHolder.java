package com.example.project;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewsItemViewHolder {

    public TextView title;
    public Button openNews;

    public NewsItemViewHolder(View itemView) {
        title = itemView.findViewById(R.id.title);
        openNews = itemView.findViewById(R.id.openNews);
    }
}

