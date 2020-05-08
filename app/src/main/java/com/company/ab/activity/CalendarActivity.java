package com.company.ab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.company.ab.R;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView mCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/"+ dayOfMonth ;
                Log.d("Calendar Activity", "onSelectedDayChange: yyyy/mm/dd:" + date);
                Intent intent = new Intent(CalendarActivity.this,MainActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("intdate",year+month*10000+dayOfMonth*1000000);
                startActivity(intent);
            }
        });

    }
}
