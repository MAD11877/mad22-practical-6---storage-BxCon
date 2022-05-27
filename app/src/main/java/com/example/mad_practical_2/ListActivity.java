package com.example.mad_practical_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        DbHandler dbHandler = new DbHandler(this, "userDB.db", null, 1);
        dbHandler.addUsers();

        ArrayList<User> Userlist = new ArrayList<>();
        Userlist = dbHandler.getUser();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        R_Adapter rAdapter = new R_Adapter(ListActivity.this, Userlist, dbHandler);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(rAdapter);

    }

    public void Alert_d(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Profile");
        builder.setMessage("MADness");

        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent VB = new Intent(ListActivity.this, MainActivity.class);

                Random rnd = new Random();
                Integer PN = rnd.nextInt(999999999);
                VB.putExtra("PN", PN);
                startActivity(VB);
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });


        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Intent i = new Intent(ListActivity.this, Login.class);

                startActivity(i);
                this.finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

}