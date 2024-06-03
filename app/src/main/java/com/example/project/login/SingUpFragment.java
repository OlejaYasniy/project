package com.example.project.login;

import android.os.Bundle;

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

import com.example.project.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DatabaseReference dataBase;
    private String USER_KEY = "User";

    public SingUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SingUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SingUpFragment newInstance(String param1, String param2) {
        SingUpFragment fragment = new SingUpFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sing_up, container, false);
        EditText password = view.findViewById(R.id.Password);
        EditText login = view.findViewById(R.id.Login);
        TextView singInTextView = view.findViewById(R.id.singIn);
        Button singUpButton = view.findViewById(R.id.singIN);
        dataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        singUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loginText = login.getText().toString();
                String passwordText = password.getText().toString();
                if (!loginText.contains("@") || loginText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getActivity(), "Invalid login or password", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    dataBaseSave(login, password);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, new LoginFragment());
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    Toast.makeText(getActivity(), "Вы зарегистрировались", Toast.LENGTH_SHORT).show();
                }
            }
        });
        singInTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, new LoginFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    public void dataBaseSave(EditText login, EditText password){
        String id = dataBase.getKey();
        String Login = login.getText().toString();
        String Password = password.getText().toString();
        User newUser = new User(id, Login, Password);
        dataBase.push().setValue(newUser);
    }
}