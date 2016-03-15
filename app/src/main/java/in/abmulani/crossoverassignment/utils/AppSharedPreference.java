package in.abmulani.crossoverassignment.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppSharedPreference {

    private final String PREF_DUMMY_DATA = "dummy_data_added";
    private final String APP_PREF = "crossover_assign";
    private SharedPreferences sharedPreferences;

    private Context context;

    public AppSharedPreference(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(APP_PREF, Context.MODE_PRIVATE);
    }

    public void setDummyDataAdded() {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(PREF_DUMMY_DATA, true);
        editor.commit();
    }

    public boolean isDummyDataAdded() {
        return sharedPreferences.getBoolean(PREF_DUMMY_DATA, false);
    }


}
