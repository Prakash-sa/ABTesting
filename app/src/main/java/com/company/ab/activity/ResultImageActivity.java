package com.company.ab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.company.ab.R;

public class ResultImageActivity extends AppCompatActivity {

    private ImageView imageView1,imageView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_image);
        imageView1=findViewById(R.id.image_button1_id);
        imageView2=findViewById(R.id.image_button2_id);
        Log.i("String is",getIntent().getStringExtra("imageurl1"));
        Glide.with(this).load(getIntent().getStringExtra("imageurl1")).into(imageView1);
        Glide.with(this).load(getIntent().getStringExtra("imageurl2")).into(imageView2);

    }
}
