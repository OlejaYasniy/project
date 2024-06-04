package com.example.project.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
    private final String[] textArray;
    private final int[] imageResources;

    private final int[] horsePowerArray;

    private OnImageButtonClickListener onImageButtonClickListener;

    public SimpleAdapter(String[] textArray, int[] imageResources, int[] horsePowerArray, OnImageButtonClickListener onImageButtonClickListener) {
        this.textArray = textArray;
        this.imageResources = imageResources;
        this.horsePowerArray = horsePowerArray;
        this.onImageButtonClickListener = onImageButtonClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.auto_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(imageResources[position]);
        holder.textView.setText(textArray[position]);
        final int horsePower = horsePowerArray[position];
        holder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onImageButtonClickListener != null) {
                    onImageButtonClickListener.onImageButtonClick(textArray[position], horsePower);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return textArray.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public Button buttonAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textView = itemView.findViewById(R.id.text_view);
            buttonAdd = itemView.findViewById(R.id.button_add);
        }
    }

}
