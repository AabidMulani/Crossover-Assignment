package in.abmulani.crossoverassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import in.abmulani.crossoverassignment.BaseActivity;
import in.abmulani.crossoverassignment.R;
import in.abmulani.crossoverassignment.utils.AppConstants;


/**
 * Created by aabid-personal on 3/10/16.
 */
public class CrossoverSplashActivity extends BaseActivity {

    private boolean backKeyPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        backKeyPressed = false;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!backKeyPressed) {
                    // go to the map screen
                    Intent intent = new Intent(activity, CrossoverMapActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, AppConstants.SPLASH_SCREEN_DELAY);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backKeyPressed = true;
    }
}
