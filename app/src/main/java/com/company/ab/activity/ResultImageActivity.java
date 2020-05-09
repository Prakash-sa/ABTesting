package com.company.ab.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.bumptech.glide.Glide;
import com.company.ab.R;
import com.company.ab.database.ImageFeatures;

import java.util.ArrayList;
import java.util.List;

public class ResultImageActivity extends AppCompatActivity {

    private ImageFeatures imageFeatures;
    private ImageView imageView1, imageView2;
    private TextView imageDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_image);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
            Bundle bundle= getIntent().getExtras();
        imageFeatures = (ImageFeatures) bundle.getSerializable("object");
        imageDescription = findViewById(R.id.image_description_text_id);
        AnyChartView anyChartView = findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(findViewById(R.id.progress_bar));
        Pie pie = AnyChart.pie();
        pie.background().fill("#E55B3C");
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(ResultImageActivity.this, event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        int a = imageFeatures.getaVote();
        if (a==0) a = 1;
        int b = imageFeatures.getbVote();
        if (b==0) b = 1;
        List<DataEntry> data = new ArrayList<>();
        ValueDataEntry version1 = new ValueDataEntry("Version 1", a);
        data.add(version1);
        ValueDataEntry version2 = new ValueDataEntry("Version 2", b);
        data.add(version2);
        pie.data(data);
        pie.labels().fontColor("#ffffff");
        pie.title("Users analytics of campaign");
        pie.title().fontColor("#ffffff");
        pie.labels().position("outside").fontColor("#ffffff");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Uploaded variants")
                .fontColor("#ffffff")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);


        anyChartView.setChart(pie);
        imageDescription.setText(imageFeatures.getImageDesciption());
    }

}
