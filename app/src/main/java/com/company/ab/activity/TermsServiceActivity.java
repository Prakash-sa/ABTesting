package com.company.ab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.ab.R;

public class TermsServiceActivity extends AppCompatActivity {

    private ImageView backpressed;
    private TextView textView,titletext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_service);
        backpressed=findViewById(R.id.backpressedid);
        titletext=findViewById(R.id.titleid);;

        backpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        String title=getIntent().getStringExtra("title");

        titletext.setText(title);

        textView=findViewById(R.id.texttermid);
        textView.setText("This is "+title);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
