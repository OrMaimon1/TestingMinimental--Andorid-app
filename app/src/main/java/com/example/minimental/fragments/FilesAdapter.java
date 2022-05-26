package com.example.minimental.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;
import com.example.minimental.R;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private List<Pictures> pictures;
    public RecyclerViewAdapter(List<Pictures>pictures){
        this.pictures = pictures;
    }

    interface PictureAdapterListener{
        void onTakePhotoPress(int position);
    }

    PictureAdapterListener listener;

    public void setListener(PictureAdapterListener listener){
        this.listener = listener;
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            Button takePhotoBtn = itemView.findViewById(R.id.take_photo_btn);
            takePhotoBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onTakePhotoPress(getAdapterPosition());

                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_row_files,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Pictures picture = pictures.get(position);
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }
}