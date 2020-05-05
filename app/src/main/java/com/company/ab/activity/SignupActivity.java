package com.company.ab.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.company.ab.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private ImageView backpressed;
    private EditText emailed,passworded;
    private Button continuebt;
    private TextView loginactivity;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        backpressed=findViewById(R.id.backpressedid);
        emailed=findViewById(R.id.nameedtid);
        passworded=findViewById(R.id.passwordedtid);
        loginactivity=findViewById(R.id.loginbtid);
        continuebt=findViewById(R.id.contentbtid);

        mAuth = FirebaseAuth.getInstance();


        loginactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
            }
        });
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
                if(email.isEmpty()||password.isEmpty())Toast.makeText(SignupActivity.this,"Please Fill Details.",Toast.LENGTH_LONG).show();
                else
                    createsignitfinal(email,password);
            }
        });

    }

    private void createsignitfinal(String email,String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this,"Welcome ",Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
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
