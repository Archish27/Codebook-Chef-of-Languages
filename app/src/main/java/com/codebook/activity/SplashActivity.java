package com.codebook.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codebook.MainActivity;
import com.codebook.R;
import com.codebook.loaders.CustomFontLoader;

/**
 * Created by Narayan Acharya on 08/11/2015.
 */
public class SplashActivity extends AppCompatActivity  {

    private static int SPLASH_TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.black_light));
        }


        setContentView(R.layout.activity_splash);

        ((TextView) findViewById(R.id.tvAppName)).setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_REGULAR));
        ((TextView) findViewById(R.id.tvAppQuote)).setTypeface(CustomFontLoader.getTypeface(this, CustomFontLoader.RALEWAY_REGULAR));
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashActivity.this, CategoryActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}
