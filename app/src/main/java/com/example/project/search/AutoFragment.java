package com.example.project.search;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.project.MainActivity;
import com.example.project.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AutoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutoFragment extends Fragment implements OnImageButtonClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String autoBrand;

    public AutoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AutoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AutoFragment newInstance(String param1, String param2) {
        AutoFragment fragment = new AutoFragment();
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
            autoBrand = getArguments().getString("auto_brand"); // добавлено
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auto, container, false);
        ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();

        int[] imageResources;
        int stringArrayId = 0;
        int horsePowerArrayId = 0; // добавлено
        if (autoBrand.equalsIgnoreCase("BMW")) {
            stringArrayId = R.array.Bmw;
            actionBar.setTitle("BMW");
            imageResources = new int[] {
                    R.drawable.bmwm4,
                    R.drawable.bmwx5,
                    R.drawable.bmwm3s,
            };
            horsePowerArrayId = R.array.BmwHorsePower;
        } else if (autoBrand.equalsIgnoreCase("Audi")) {
            stringArrayId = R.array.Audi;
            actionBar.setTitle("Audi");
            imageResources = new int[] {
                    R.drawable.audirs6,
                    R.drawable.audiq7,
                    R.drawable.audia4,
            };
            horsePowerArrayId = R.array.AudiHorsePower;
        } else if (autoBrand.equalsIgnoreCase("Mercedes-Benz")) {
            stringArrayId = R.array.MercedesBenz;
            actionBar.setTitle("Mercedes-Benz");
            imageResources = new int[] {
                    R.drawable.mbgt,
                    R.drawable.mbgle,
                    R.drawable.mbcclass,
            };
            horsePowerArrayId = R.array.MercedesBenzHorsePower;
        } else {
            Toast.makeText(getActivity(), "Invalid auto brand", Toast.LENGTH_SHORT).show();
            return view;
        }

        String[] textArray = getResources().getStringArray(stringArrayId);
        int[] horsePowerArray = getResources().getIntArray(horsePowerArrayId);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SimpleAdapter(textArray, imageResources, horsePowerArray, this)); // изменено

        return view;
    }


    public void showDialog(String carName, int horsePower) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(carName);
        builder.setMessage("Вы выбрали: " + carName + "\n" + "Лошадиные силы: " + horsePower);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }

    @Override
    public void onImageButtonClick(String carName, int horsePower) {
        showDialog(carName, horsePower);
    }
}