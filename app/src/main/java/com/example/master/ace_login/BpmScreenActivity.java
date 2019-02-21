package com.example.master.ace_login;

import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BpmScreenActivity extends AppCompatActivity {

    private LineChart lineChart;
    private LineData lineData;
    float salesv[] = {30f, 24f, 50f, 84f, 26f, 35f, 22f};
    Timer wakeupTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpm_screen);

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

        for (int i = 0 ; i<salesv.length ; i++) {
            entryArrayList.add(new Entry(salesv[i],i));

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

        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "BPM");
        lineDataSet.setColor(Color.RED);
        lineDataSet.setCircleColor(Color.RED);
        lineDataSet.setCircleRadius(7);
        lineDataSet.setLineWidth(4);


        lineDataSets = new ArrayList<>();
        lineDataSets.add(lineDataSet);


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

    // bpm 초수
    @Override
    public void onResume() {
        super.onResume();
        wakeupTimer = new Timer();
        wakeupTimer.schedule(new WakeupTask(),
                0,        //initial delay
                2000);  //subsequent rate

    }

    class WakeupTask extends TimerTask {
        int numWarningBeeps = 3;

        public void run() {
            AppController app = AppController.getInstance();
            Log.w("##################", "My Heart Rate: " + app.hr);
        }
    }

}
