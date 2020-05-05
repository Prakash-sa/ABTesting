package com.company.ab.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.ab.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ImageView backpressed;
    private EditText emailed,passworded,confirmpassworded;
    private Button continuebt;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        backpressed=findViewById(R.id.backpressedid);
        emailed=findViewById(R.id.nameedtid);
        passworded=findViewById(R.id.passwordedtid);
        continuebt=findViewById(R.id.contentbtid);
        confirmpassworded=findViewById(R.id.confirmpasswordedtid);


        mAuth = FirebaseAuth.getInstance();


        backpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        continuebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailed.getText().toString();
                String password=passworded.getText().toString();
                String confirmpassword=confirmpassworded.getText().toString();
                if(email.isEmpty()||password.isEmpty()||confirmpassword.isEmpty())
                    Toast.makeText(LoginActivity.this,"Please fill Details.",Toast.LENGTH_LONG).show();
                else if(!password.equals(confirmpassword))
                    Toast.makeText(LoginActivity.this,"Please type Correct password",Toast.LENGTH_LONG).show();
                else loginsignin(email,password);
            }
        });
    }

    private void loginsignin(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this,"Welcome Back",Toast.LENGTH_LONG).show();

                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Type Correct 8 digit password",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
