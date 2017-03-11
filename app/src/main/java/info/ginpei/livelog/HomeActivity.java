package info.ginpei.livelog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import info.ginpei.livelog.views.SpiralView;

public class HomeActivity extends AppCompatActivity {

    private SpiralView spiralView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        spiralView = (SpiralView) findViewById(R.id.spiral);
        spiralView.setRollings(3);
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
