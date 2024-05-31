package com.example.project.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.project.R;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<News> mNews;
    private NewsFragment mNewsFragment;

    public NewsAdapter(Context context, List<News> news, NewsFragment newsFragment) {
        mContext = context;
        mNews = news;
        mNewsFragment = newsFragment;
    }

    @Override
    public int getCount() {
        return mNews.size();
    }

    @Override
    public Object getItem(int position) {
        return mNews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsItemViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
            viewHolder = new NewsItemViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NewsItemViewHolder) convertView.getTag();
        }

        final News news = mNews.get(position);
        viewHolder.title.setText(news.getTitle());
        viewHolder.openNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsFragment.openWebView(news.getUrl());
            }
        });
        return convertView;
    }
}
