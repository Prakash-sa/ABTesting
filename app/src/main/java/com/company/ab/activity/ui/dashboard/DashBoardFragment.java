package com.company.ab.activity.ui.dashboard;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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
import com.company.ab.R;

import java.util.ArrayList;
import java.util.List;

public class DashBoardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private DashBoardViewModel mViewModel;

    public static DashBoardFragment newInstance() {
        return new DashBoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dash_board_fragment, container, false);
        AnyChartView anyChartView = root.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(root.findViewById(R.id.progress_bar));
        Pie pie = AnyChart.pie();
        pie.background().fill("#E55B3C");
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });

        List<DataEntry> data = new ArrayList<>();
        ValueDataEntry version1 = new ValueDataEntry("Version 1", 30);
        data.add(version1);
        ValueDataEntry version2 = new ValueDataEntry("Version 2", 60);
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
        Spinner spin = root.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        return root;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        // TODO: Use the ViewModel
    }

}
