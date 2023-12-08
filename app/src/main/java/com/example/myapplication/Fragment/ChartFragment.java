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
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;

public class ChartFragment extends Fragment {
    DashboardActivity parentActivity;
    LineChart lineChart;
    ProgressBar pg_loading;
    LineDataSet lineDataSet;
    ArrayList<ILineDataSet> iLineDataSets;
    LineData lineData;
    ImageView btn_sync;
    TextInputLayout inputLayout;
    MaterialButton btn_showChart;
    MaterialAutoCompleteTextView inputTv;
    private ArrayList<Float> sourceData;
    private final int[] lColor = {R.color.cerise, R.color.some_blue, R.color.wind, R.color.rain};
    private final String[] lLabel = {"Temperature", "Humidity", "Wind speed", "Rain fall"};
    private int chosenData = 0;
    private String[] androidStrings;

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
        btn_showChart = view.findViewById(R.id.btn_showChart);
        inputTv = view.findViewById(R.id.inputTV);
        inputLayout = view.findViewById(R.id.inputLayout);
        androidStrings = getResources().getStringArray(R.array.options_list);
        sourceData = new ArrayList<>();
    }

    private void InitialEvent() {
        updateChart();
        btn_showChart.setOnClickListener(view -> {
            String inputText = inputTv.getText().toString();

            inputLayout.setError(null);

            if (inputText.isEmpty()) {
                inputLayout.setError(getString(R.string.please_select_an_option));
            } else {
                chosenData = Arrays.asList(androidStrings).indexOf(inputText);
                updateChart();
            }

            inputTv.getText().clear();
        });
        btn_sync.setOnClickListener(view -> updateChart());
    }

    private void updateChart() {
        loadingAnimation();

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

        chartSetup(label, color);
        chartUI(color);
    }

    private void chartSetup(String label, int color) {
        lineDataSet = new LineDataSet(lineChartDataSet(sourceData), label);
        lineDataSet.setColor(color);
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
        lineChart.getData().setDrawValues(false);

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