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

public class TempScreenActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private LineChart lineChart;
    private LineData lineData;
    float Fahrenheit[] = {30f, 24f, 50f, 84f, 25f, 35f, 55f};
    float Celsius[] = {50f, 44f, 30f, 70f, 63f, 66f, 42f};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_screen);

        lineChart = (LineChart)findViewById(R.id.linechart);
        lineData = new LineData(getXvalus(),getLineDataValues());
        lineChart.setData(lineData);
        lineChart.setDescription("Company Performance");
        lineChart.animateY(750);
        lineChart.invalidate();

    }

    private List<ILineDataSet> getLineDataValues() {

        ArrayList<ILineDataSet> lineDataSets = null;
        ArrayList<Entry> entryArrayList = new ArrayList<>();
        ArrayList<Entry> CelsiusArrayList = new ArrayList<>();

        for (int i = 0 ; i<Fahrenheit.length ; i++) {
            entryArrayList.add(new Entry(Fahrenheit[i],i));

        }

        for (int i = 0 ; i<Celsius.length ; i++) {
            CelsiusArrayList.add(new Entry(Celsius[i],i));

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

        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "Fahrenheit");
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.RED);
        lineDataSet.setCircleRadius(7);
        lineDataSet.setLineWidth(4);

        LineDataSet lineDataSet2 = new LineDataSet(CelsiusArrayList, "Celsius");
        lineDataSet2.setColor(Color.BLUE);
        lineDataSet2.setCircleColor(Color.BLUE);
        lineDataSet2.setCircleRadius(7);
        lineDataSet2.setLineWidth(4);

        lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSet);
        lineDataSets.add(lineDataSet2);


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
            AlertDialog.Builder builder = new AlertDialog.Builder(TempScreenActivity.this);
            dialog = builder.setMessage("Are you sure to want to sign out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(TempScreenActivity.this, SignInActivity.class);
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

