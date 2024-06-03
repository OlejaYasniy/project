package com.example.project.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.project.R;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context mContext;
    private List<News> mNews;
    private NewsFragment mNewsFragment;
    private boolean mIsCheckBoxVisible = false;
    private List<Integer> mSelectedPositions = new ArrayList<>();

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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.news_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.title = convertView.findViewById(R.id.title);
            viewHolder.checkBox = convertView.findViewById(R.id.checkBox);
            viewHolder.openNews = convertView.findViewById(R.id.openNews);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final News news = mNews.get(position);
        viewHolder.title.setText(news.getTitle());
        viewHolder.checkBox.setVisibility(mIsCheckBoxVisible ? View.VISIBLE : View.INVISIBLE);
        viewHolder.checkBox.setEnabled(mIsCheckBoxVisible);
        viewHolder.checkBox.setChecked(mSelectedPositions.contains(position));
        viewHolder.checkBox.setOnClickListener(v -> {
            if (((CheckBox) v).isChecked()) {
                mSelectedPositions.add(position);
            } else {
                mSelectedPositions.remove(Integer.valueOf(position));
            }
        });

        viewHolder.openNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewsFragment.openWebView(news.getUrl());
            }
        });

        return convertView;
    }

    public void toggleCheckBoxVisibility() {
        mIsCheckBoxVisible = !mIsCheckBoxVisible;
        notifyDataSetChanged();
    }

    public List<Integer> getSelectedPositions() {
        return mSelectedPositions;
    }

    private static class ViewHolder {
        TextView title;
        CheckBox checkBox;
        Button openNews;
    }
}