package com.company.ab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.company.ab.R;
import com.company.ab.adapter.FeedbackAdapter;
import com.company.ab.database.ImageFeatures;

import java.util.ArrayList;
import java.util.List;

public class FeedbackActivity extends AppCompatActivity {


    private List<String> feedback=new ArrayList<>();
    private ListView feedbackListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        feedbackListView=findViewById(R.id.feedback_listview_id);

        Bundle bundle= getIntent().getExtras();
        ImageFeatures imageFeatures = (ImageFeatures) bundle.getSerializable("object");

        feedback=  imageFeatures.getFeedback();

        FeedbackAdapter feedbackAdapter=new FeedbackAdapter(getApplicationContext(),feedback);
        feedbackListView.setAdapter(feedbackAdapter);
    }
}
