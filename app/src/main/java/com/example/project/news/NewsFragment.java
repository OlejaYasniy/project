package com.example.project.news;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.project.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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

    private Button buttonAddNews;
    private List<News> mNews;
    private NewsAdapter mAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromButton;
    private Animation toButton;
    private View edit;
    private View add;
    private View delete;

    private Boolean clicked = false;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        rotateOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close_anim);
        toButton = AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom_anim);
        fromButton = AnimationUtils.loadAnimation(getActivity(), R.anim.from_botton_anim);

        edit = view.findViewById(R.id.floatingActionButton);
        add = view.findViewById(R.id.floatingActionButtonAdd);
        delete = view.findViewById(R.id.floatingActionButtonDelete);

        mListView = view.findViewById(R.id.listView);

        mNews = new ArrayList<>();
        mAdapter = new NewsAdapter(getActivity(), mNews, this);
        mListView.setAdapter(mAdapter);

        loadNewsFromSharedPreferences();
        if (isAdmin()) {
            edit.setVisibility(View.VISIBLE);
            edit.setEnabled(true);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditButtonClicked();
                    mAdapter.toggleCheckBoxVisibility();
                }
            });
        } else {
            edit.setVisibility(View.GONE);
            edit.setEnabled(false);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddNewsDialog();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Integer> selectedPositions = mAdapter.getSelectedPositions();
                for (int position : selectedPositions) {
                    if (position < mNews.size()) {
                        mNews.remove(position);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }
        });



        // TODO: Загрузить новости в фоновом потоке при запуске фрагмента
        new LoadNewsTask(this).execute();

        return view;
    }

    private void onEditButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        setClicked(clicked);
        clicked = !clicked;
    }

    private void setAnimation(Boolean clicked) {
        if(!clicked){
            delete.startAnimation(fromButton);
            add.startAnimation(fromButton);
            edit.startAnimation(rotateOpen);
        }else {
            delete.startAnimation(toButton);
            add.startAnimation(toButton);
            edit.startAnimation(rotateClose);
        }
    }

    private void setVisibility(Boolean clicked) {
        if(!clicked){
            add.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.INVISIBLE);
            add.setVisibility(View.INVISIBLE);

        }

    }

    private void setClicked(Boolean clicked) {
        if (!clicked) {
            add.setClickable(true);
            delete.setClickable(true);
        } else {
            add.setClickable(false);
            delete.setClickable(false);
        }
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

    private boolean isAdmin() {
        String userId = getUserId();
        return userId.equals("Admin");
    }

    private String getUserId() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        return sharedPreferences.getString("user_id", "");
    }

    public void showAddNewsDialog() {
        final View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.add_news, null);
        final EditText editTextTitle = dialogView.findViewById(R.id.editTextTitle);
        final EditText editTextUrl = dialogView.findViewById(R.id.editTextUrl);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(dialogView);
        builder.setTitle("Добавить новость");

        builder.setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String title = editTextTitle.getText().toString().trim();
                String url = editTextUrl.getText().toString().trim();
                if (!title.isEmpty() && !url.isEmpty()) {
                    mNews.add(new News(title, url));
                    mAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Заполните все поля", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Отмена", null);
        builder.show();
    }

    private void saveNewsToSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String jsonNews = new Gson().toJson(mNews);
        editor.putString("saved_news", jsonNews);
        editor.apply();
    }

    private void loadNewsFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String jsonNews = sharedPreferences.getString("saved_news", null);
        if (jsonNews != null) {
            Type type = new TypeToken<List<News>>() {}.getType();
            List<News> loadedNews = new Gson().fromJson(jsonNews, type);
            if (loadedNews != null) {
                mNews.clear();
                mNews.addAll(loadedNews);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveNewsToSharedPreferences();
    }

    @Override
    public void onStop() {
        super.onStop();
        saveNewsToSharedPreferences();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveNewsToSharedPreferences();
    }
}