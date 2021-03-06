package com.example.mad_practical_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private TextInputEditText username, email, password, cnfpassword;
    private String UID;
    private Button regBtn;
    private ProgressBar loadingPB;
    private TextView loginTV;

    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.idUserName);
        email = findViewById(R.id.idEmail);
        password = findViewById(R.id.idPassword);
        cnfpassword = findViewById(R.id.idCnfPwd);
        regBtn = findViewById(R.id.idBtnReg);
        loadingPB = findViewById(R.id.PBloading);
        loginTV = findViewById(R.id.idEUlog);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, Login.class);
                startActivity(i);
            }
        });

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingPB.setVisibility(View.VISIBLE);
                String Username = username.getText().toString();
                String Email = email.getText().toString();
                String Password = password.getText().toString();
                String CnfPwd = cnfpassword.getText().toString();

                if (!Password.equals(CnfPwd)) {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Ensure both passwords are the same", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Username) && TextUtils.isEmpty(Email) && TextUtils.isEmpty(Password) && TextUtils.isEmpty(CnfPwd)) {
                    loadingPB.setVisibility(View.GONE);
                    Toast.makeText(Register.this, "Please enter credentials", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                UID = task.getResult().getUser().getUid();
                                createUserProfile(Username, Password);

                                Toast.makeText(Register.this, "User Registered...", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Register.this, Login.class);
                                startActivity(i);
                                finish();

                            } else {
                                loadingPB.setVisibility(View.GONE);
                                Toast.makeText(Register.this, "Failed to register user...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

    }

    public void createUserProfile(String username, String password) {
        HashMap<String, Object> userAndPassInput = new HashMap<>();
        userAndPassInput.put("username", username);
        userAndPassInput.put("password", password);

        databaseReference.child(username).setValue(userAndPassInput);
    }
}