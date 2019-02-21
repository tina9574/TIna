package com.example.master.ace_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;


public class AirScreenActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private LineChart lineChart;
    private LineData lineData;
    float SO_2[] = {150f, 124f, 150f, 184f, 136f, 154f, 138f};
    float O_3[] = {38f, 42f, 39f, 51f, 25f, 22f, 49f};
    float C_O[] = {40f, 30f, 20f, 35f, 36f, 32f, 34f};
    float NO_2[] = {70f, 85f, 85f, 75f, 65f, 77f, 94f};
    float FineDust[] = {50f, 44f, 30f, 34f, 25f, 21f, 45f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_screen);

        lineChart = (LineChart)findViewById(R.id.linechart);
        lineData = new LineData(getXvalus(),getLineDataValues());
        lineChart.setData(lineData);
        lineChart.setDescription("Company Performance");
        lineChart.animateY(750);
        lineChart.invalidate();

    }

    private List<ILineDataSet> getLineDataValues() {

        ArrayList<ILineDataSet> lineDataSets = null;
        ArrayList<Entry> SO2 = new ArrayList<>();
        ArrayList<Entry> O3 = new ArrayList<>();
        ArrayList<Entry> CO = new ArrayList<>();
        ArrayList<Entry> NO2 = new ArrayList<>();
        ArrayList<Entry> FD = new ArrayList<>();

        for (int i = 0 ; i<SO_2.length ; i++) {
            SO2.add(new Entry(SO_2[i],i));

        }

        for (int i = 0 ; i<O_3.length ; i++) {
            O3.add(new Entry(O_3[i],i));

        }

        for (int i = 0 ; i<C_O.length ; i++) {
            CO.add(new Entry(C_O[i],i));

        }

        for (int i = 0 ; i<NO_2.length ; i++) {
            NO2.add(new Entry(NO_2[i],i));

        }

        for (int i = 0 ; i<FineDust.length ; i++) {
            FD.add(new Entry(FineDust[i],i));

        }

        /*ArrayList<Entry> entryArrayList = new ArrayList<>();
        Entry e1 = new Entry(2000f,0);
        Entry e2 = new Entry(3500f,1);
        Entry e3 = new Entry(5000f,2);
        Entry e4 = new Entry(8500f,3);

        entryArrayList.add(e1);
        entryArrayList.add(e2);
        entryArrayList.add(e3);
        entryArrayList.add(e4); */

        LineDataSet lineDataSet = new LineDataSet(SO2, "SO2");
        lineDataSet.setColor(Color.MAGENTA);
        lineDataSet.setCircleColor(Color.MAGENTA);
        lineDataSet.setCircleRadius(7);
        lineDataSet.setLineWidth(4);

        LineDataSet lineDataSet2 = new LineDataSet(O3, "O3");
        lineDataSet2.setColor(Color.GREEN);
        lineDataSet2.setCircleColor(Color.GREEN);
        lineDataSet2.setCircleRadius(7);
        lineDataSet2.setLineWidth(4);



        LineDataSet lineDataSet3 = new LineDataSet(CO, "CO");
        lineDataSet3.setColor(Color.BLUE);
        lineDataSet3.setCircleColor(Color.BLUE);
        lineDataSet3.setCircleRadius(7);
        lineDataSet3.setLineWidth(4);



        LineDataSet lineDataSet4 = new LineDataSet(NO2, "NO2");
        lineDataSet4.setColor(Color.YELLOW);
        lineDataSet4.setCircleColor(Color.YELLOW);
        lineDataSet4.setCircleRadius(7);
        lineDataSet4.setLineWidth(4);



        LineDataSet lineDataSet5 = new LineDataSet(FD, "Fine Dust");
        lineDataSet5.setColor(Color.BLACK);
        lineDataSet5.setCircleColor(Color.BLACK);
        lineDataSet5.setCircleRadius(7);
        lineDataSet5.setLineWidth(4);




        lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSet);
        lineDataSets.add(lineDataSet2);
        lineDataSets.add(lineDataSet3);
        lineDataSets.add(lineDataSet4);
        lineDataSets.add(lineDataSet5);


        return lineDataSets;
    }


    private List<String> getXvalus() {

        ArrayList<String> xvalues = new ArrayList<>();
        xvalues.add("-7days");
        xvalues.add("-6");
        xvalues.add("-5");
        xvalues.add("-4");
        xvalues.add("-3");
        xvalues.add("-2");
        xvalues.add("-1");



        return xvalues;
    }

    public void onClick_signout(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AirScreenActivity.this);
        dialog = builder.setMessage("Are you sure to want to sign out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(AirScreenActivity.this, SignInActivity.class);
                        startActivity(intent);

                        finishAffinity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();

                    }
                })
                .create();
        dialog.show();
    }


}