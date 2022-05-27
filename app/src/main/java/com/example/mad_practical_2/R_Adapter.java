package com.example.mad_practical_2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.jar.Attributes;

public class R_Adapter extends RecyclerView.Adapter<R_ViewHolder> {

    Context c;
    ArrayList<User> data;
    DbHandler db;

    public R_Adapter(Context c, ArrayList<User> data, DbHandler dbHandler) {
        this.c = c;
        this.data = data;
        this.db = dbHandler;
    }

    @Override
    public int getItemViewType(int position) {
        return (data.get(position).name.charAt(data.get(position).name.length()-1)=='7')? 0:1;
    }

    @NonNull
    @Override
    public R_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate((viewType==1)? R.layout.r_layout:R.layout.r_layout2, parent, false);

        return new R_ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull R_ViewHolder holder, int position) {
        User user = data.get(position);
        String name = user.name;
        String Desc = user.description;

        holder.Name.setText(name);
        holder.Description.setText(Desc);
        holder.Img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Profile");
                builder.setMessage("MADness");

                builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent VB = new Intent(c, MainActivity.class);
                        User userFromDatabase = db.updateUserOnClick(user.name);
                        VB.putExtra("Obj", userFromDatabase);

                        c.startActivity(VB);
                    }
                });

                builder.setNegativeButton("Close", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                    }
                });


                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
