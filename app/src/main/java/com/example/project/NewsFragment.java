package com.example.project;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView mListView;
    private List<News> mNews;
    private NewsAdapter mAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mListView = view.findViewById(R.id.listView);

        mNews = new ArrayList<>();
        mAdapter = new NewsAdapter(getActivity(), mNews, this);
        mListView.setAdapter(mAdapter);

        // TODO: Загрузить новости в фоновом потоке при запуске фрагмента
        new LoadNewsTask(this).execute();

        return view;
    }


    public void openWebView(String url) {
        WebView webView = new WebView(getActivity());
        webView.loadUrl(url);
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Новости");
        dialog.setView(webView);
        dialog.setNegativeButton("Закрыть", null);
        dialog.show();
    }

    public void updateNews(List<News> news) {
        mNews.clear();
        mNews.addAll(news);
        mAdapter.notifyDataSetChanged();
    }
}