package com.company.ab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.company.ab.R;

public class ImageViewActivity extends AppCompatActivity {

    private ImageView imageView1,imageView2;
    private LinearLayout linearLayout1,linearLayout2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);


        imageView1=findViewById(R.id.image_button1_id);
        imageView2=findViewById(R.id.image_button2_id);
        linearLayout1=findViewById(R.id.lineraLayout1_id);
        linearLayout2=findViewById(R.id.lineraLayout2_id);

        Glide.with(this).load(getIntent().getStringExtra("imageurl1")).into(imageView1);
        Glide.with(this).load(getIntent().getStringExtra("imageurl2")).into(imageView2);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
