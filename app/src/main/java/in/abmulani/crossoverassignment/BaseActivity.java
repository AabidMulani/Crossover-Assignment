package in.abmulani.crossoverassignment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by aabid-personal on 3/14/16.
 */
public class BaseActivity extends AppCompatActivity {

    public Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // initialize all receiver's here
    }

    @Override
    protected void onPause() {
        super.onPause();
        // remove all receiver's here

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
