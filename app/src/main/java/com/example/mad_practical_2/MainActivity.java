package com.example.mad_practical_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView Name = findViewById(R.id.Heading);
        TextView Description = findViewById(R.id.Main_Text);
        Button Follow_Button = findViewById(R.id.Follow_Button);

        Intent recievingEnd = getIntent();
        User Obj = (User)getIntent().getSerializableExtra("Obj");

        Name.setText(Obj.name);
        Description.setText(Obj.description);
        Follow_Button.setText(Obj.followed ? "Follow": "Unfollow");

        Button F_but = findViewById(R.id.Follow_Button);
        F_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Obj.followed = !Obj.followed;
            DbHandler dbHandler = new DbHandler(MainActivity.this, "userDB.db", null, 1);
            dbHandler.updateUser(Obj);

            F_but.setText(Obj.followed ? "Follow": "Unfollow");
            Toast.makeText(getApplicationContext(),Obj.followed ? "Unfollowed": "Followed", Toast.LENGTH_SHORT ).show();
            }
        });

        Button M_But = findViewById(R.id.Message_Button);
        M_But.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MB = new Intent(MainActivity.this, MessageGroup.class);
                startActivity(MB);
            }
        });

    }

}