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
import java.util.HashMap;
import java.util.List;

public class DashBoardFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private DashBoardViewModel mViewModel;
    private HashMap<String, ArrayList<Integer>> map = new HashMap<>();
    private AnyChartView anyChartView;
    private int a = 1;
    private int b = 3;
    private Pie pie;

    public static DashBoardFragment newInstance() {
        return new DashBoardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dash_board_fragment, container, false);
        anyChartView = root.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(root.findViewById(R.id.progress_bar));
        pie = AnyChart.pie();
        pie.background().fill("#E55B3C");
        pie.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
            @Override
            public void onClick(Event event) {
                Toast.makeText(getActivity(), event.getData().get("x") + ":" + event.getData().get("value"), Toast.LENGTH_SHORT).show();
            }
        });
        setupChart();
        Spinner spin = root.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Tweet");
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(1);
        temp.add(3);
        map.put("Tweet", temp);
        temp = new ArrayList<>();
        temp.add(5);
        temp.add(3);
        categories.add("New Style");
        map.put("New Style", temp);
        temp = new ArrayList<>();
        temp.add(8);
        temp.add(6);
        categories.add("Icon change");
        map.put("Icon change",temp);
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
        ArrayList<Integer> list = map.get(item);
        a = list.get(0);
        b = list.get(1);
        setupChart();
        // Showing selected spinner ite
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }


    private void setupChart(){
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
