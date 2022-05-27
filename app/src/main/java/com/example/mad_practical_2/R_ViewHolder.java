package com.example.mad_practical_2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class R_ViewHolder extends RecyclerView.ViewHolder {
    TextView Name;
    TextView Description;
    ImageView Img;

    public R_ViewHolder(View item) {
        super(item);
        Name = item.findViewById(R.id.Name);
        Description = item.findViewById(R.id.Description);
        Img = item.findViewById(R.id.Img);
    }
}
