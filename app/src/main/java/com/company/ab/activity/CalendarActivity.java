package com.company.ab.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import com.company.ab.R;
import com.company.ab.helper.CalendarViewHelper;

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
                CalendarViewHelper.last_date=year+month*10000+dayOfMonth*1000000;
                CalendarViewHelper.date=date;
                finish();
            }
        });

    }
}
