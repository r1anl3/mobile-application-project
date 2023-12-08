package com.example.myapplication.Fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.myapplication.Activity.DashboardActivity;
import com.example.myapplication.R;
import com.example.myapplication.Service.ForegroundService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class ChartFragment extends Fragment {
    DashboardActivity parentActivity;
    LineChart lineChart;
    ProgressBar pg_loading;
    LineDataSet lineDataSet;
    ArrayList<ILineDataSet> iLineDataSets;
    LineData lineData;
    ImageView btn_sync;
    private final int[] lColor = {R.color.cerise, R.color.some_blue, R.color.wind, R.color.rain};
    private final String[] lLabel = {"Temperature", "Humidity", "Wind speed", "Rain fall"};

    public ChartFragment() {
        // Required empty public constructor
    }

    public ChartFragment(DashboardActivity activity) {
        this.parentActivity = activity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        InitialView(view);
        InitialEvent();
        super.onViewCreated(view, savedInstanceState);
    }

    private void InitialView(View view) {
        lineChart = view.findViewById(R.id.lineChart);
        pg_loading = view.findViewById(R.id.pg_loading);
        btn_sync = view.findViewById(R.id.btn_sync);
    }

    private void InitialEvent() {
        ArrayList<Float> sourceData = new ArrayList<>();

        loadingAnimation();
        btn_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingAnimation();
            }
        });


        int chosenData = 3;
        switch (chosenData) {
            case 0:
                sourceData = ForegroundService.aTemp;
                break;
            case 1:
                sourceData = ForegroundService.aHumid;
                break;
            case 2:
                sourceData = ForegroundService.aSpeed;
                break;
            case 3:
                sourceData = ForegroundService.aRain;
                break;
        }

        int color = ContextCompat.getColor(parentActivity.getApplicationContext(),  lColor[chosenData]);
        String label = lLabel[chosenData];

        chartSetup(sourceData, label);
        chartUI(color);
    }

    private void chartSetup(ArrayList<Float> sourceData, String label) {
        lineDataSet = new LineDataSet(lineChartDataSet(sourceData), label);
        iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
    }

    private void loadingAnimation() {
        pg_loading.setVisibility(View.VISIBLE);
        lineChart.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(() -> {
            pg_loading.setVisibility(View.INVISIBLE);
            lineChart.setVisibility(View.VISIBLE);
        }, 1000);
    }

    private void chartUI(int color) {
        lineChart.setNoDataText("Data not Available");

        lineDataSet.setColor(color);
        lineDataSet.setCircleColor(color);
        lineDataSet.setDrawCircles(true);
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setLineWidth(5);
        lineDataSet.setCircleRadius(10);
        lineDataSet.setCircleHoleRadius(10);
        lineDataSet.setValueTextSize(10);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineChart.invalidate();
    }

    private ArrayList<Entry> lineChartDataSet(ArrayList<Float> sourceData){
        ArrayList<Entry> dataSet = new ArrayList<>();

        for (int i = 0; i < sourceData.size(); i++) {
            dataSet.add(new Entry(i, sourceData.get(i)));
        }

        return dataSet;
    }}