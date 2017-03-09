package info.ginpei.livelog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_home_appbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuItem_openPreferences:
                openPreferencesActivity();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openPreferencesActivity() {
        startActivity(new Intent(this, PreferencesActivity.class));
    }
}
