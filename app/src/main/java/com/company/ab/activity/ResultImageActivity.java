package com.company.ab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.company.ab.R;
import com.company.ab.database.ImageFeatures;

public class ResultImageActivity extends AppCompatActivity {

    private ImageFeatures imageFeatures;
    private ImageView imageView1,imageView2;
    private TextView imageDescription,resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_image);
        imageView1=findViewById(R.id.image_button1_id);
        imageView2=findViewById(R.id.image_button2_id);
        imageDescription=findViewById(R.id.image_decription_text_id);
        resultTextView=findViewById(R.id.result_flag_id);

        Bundle b=getIntent().getExtras();
        imageFeatures= (ImageFeatures) b.getSerializable("object");

        Glide.with(this).load(imageFeatures.getImageurl1()).into(imageView1);
        Glide.with(this).load(imageFeatures.getImageurl2()).into(imageView2);
        imageDescription.setText(imageFeatures.getImageDesciption());

        resultTextView.setText("A: "+imageFeatures.getaVote()+" B: "+imageFeatures.getbVote());
    }
}
