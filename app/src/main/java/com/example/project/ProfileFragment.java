package com.example.project;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

        return view;
    }

    private void logout() {
        // Сбросить логин и пароль
        mLogin = null;
        mPassword = null;

        // Изменить текст пункта меню "Профиль" на "Войти"
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.changeIcon2();

        // Перейти на HomeFragment
        mainActivity.moveToHomeFr();
    }
}
