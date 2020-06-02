package com.benceboldi.mymovies;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTitle, mDes;

    View v;

    public MyHolder(@NonNull View itemView){
        super(itemView);

        mImageView = itemView.findViewById(R.id.imageId);
        mTitle = itemView.findViewById(R.id.titleId);
        mDes = itemView.findViewById(R.id.descId);


        v = itemView;
    }
}
