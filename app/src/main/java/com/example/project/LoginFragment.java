package com.example.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private String mLogin;
    private String mPassword;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseReference dataBase;
    private String USER_KEY = "User";

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //dataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        EditText login = view.findViewById(R.id.Login);
        EditText password = view.findViewById(R.id.Password);
        dataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        // Добавляем обработчик нажатия на кнопку "sing up"
        Button singUpButton = view.findViewById(R.id.singIN);
        singUpButton.setOnClickListener(new View.OnClickListener() {
            // Проверяем введенные данные
            @Override
            public void onClick(View v) {
                // Здесь вы можете добавить логику регистрации пользователя
                String loginText = login.getText().toString();
                String passwordText = password.getText().toString();
                if (!loginText.contains("@") || loginText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid login or password", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mLogin = loginText;
                    mPassword = passwordText;
                    getDataBaseInfo(loginText, passwordText);
                }
            }
        });
        // Добавляем обработчик нажатия на TextView "singUp"
        TextView singUpTextView = view.findViewById(R.id.singUp);
        singUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Вызываем метод FragmentManager для замены текущего фрагмента новым
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new SingUpFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void getDataBaseInfo(String loginText, String passwordText) {
        dataBase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userFound = false;
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user.login.equals(loginText) && user.password.equals(passwordText)) {
                        userFound = true;
                        break;
                    }
                }

                if (userFound) {
                    // Передаем логин и пароль в ProfileFragment
                    Bundle args = new Bundle();
                    args.putString("login", loginText);
                    args.putString("password", passwordText);
                    ProfileFragment profileFragment = ProfileFragment.newInstance(args);

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, profileFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                    Toast.makeText(getActivity(), "You are logged in", Toast.LENGTH_SHORT).show();
                    ((MainActivity) getActivity()).changeIcon();
                } else {
                    Toast.makeText(getActivity(), "Invalid login or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getLogin() {
        return mLogin;
    }

    public String getPassword() {
        return mPassword;
    }
}