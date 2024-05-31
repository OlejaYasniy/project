package com.example.project.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.project.MainActivity;
import com.example.project.R;

public class ProfileFragment extends Fragment {

    private String mLogin;
    private String mPassword;
    private TextView mLoginTextView;
    private TextView mPasswordTextView;
    private Button mLogoutButton;

    public static ProfileFragment newInstance(String login, String password) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("login", login);
        args.putString("password", password);
        fragment.setArguments(args);
        return fragment;
    }

    public static ProfileFragment newInstance(Bundle args) {
        ProfileFragment fragment = new ProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLogin = getArguments().getString("login");
            mPassword = getArguments().getString("password");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mLoginTextView = view.findViewById(R.id.login_text);
        mPasswordTextView = view.findViewById(R.id.password_text);
        mLogoutButton = view.findViewById(R.id.logout_button);

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        // Установить текст логина и пароля
        mLoginTextView.setText(mLogin);
        mPasswordTextView.setText(mPassword);
        saveUserData();

        return view;
    }

    private void logout() {
        // Сбросить логин и пароль
        mLogin = null;
        mPassword = null;

        // Изменить текст пункта меню "Профиль" на "Войти"
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.changeIcon2();

        // Перейти на SearchFragment
        mainActivity.moveToSearchFr();
    }

    public void saveUserData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", mLogin);
        editor.putString("password", mPassword);
        editor.apply();
    }

}
