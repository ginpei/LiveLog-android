package info.ginpei.livelog;

import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.MultiSelectListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Set;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment())
                .commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        private SharedPreferences preferences;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        }

        @Override
        public void onResume() {
            super.onResume();
            preferences.registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            preferences.unregisterOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            updateSummary(key);
        }

        /**
         * Update view specified by preference key to current preference value.
         *
         * @param key Preference key.
         */
        private void updateSummary(String key) {
            Preference pref = findPreference(key);

            String value;
            if (pref instanceof EditTextPreference || pref instanceof ListPreference) {
                value = preferences.getString(key, "");
            } else if (pref instanceof MultiSelectListPreference) {
                // TODO review
                Set<String> strings = preferences.getStringSet("whatIPrefer", null);
                if (strings == null) {
                    value = "";
                } else {
                    value = strings.toString();
                    value = value.substring(1, value.length() - 1);
                }
            } else {
                value = null;
            }

            if (value != null) {
                pref.setSummary(value);
            }
        }
    }
}
