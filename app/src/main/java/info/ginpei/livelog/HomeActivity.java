package info.ginpei.livelog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        LineChart chart = (LineChart) findViewById(R.id.chart);

        int[] dataObjects = new int[] {
                10,
                11,
                13,
                9,
                11
        };

        List<Entry> entries = new ArrayList<Entry>();

        for (int i = 0; i < dataObjects.length; i++) {
            entries.add(new Entry(i, dataObjects[i]));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label");

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh
    }
}
