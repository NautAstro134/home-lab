package com.homelab.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button networkButton = findViewById(R.id.buttonNetwork);
        Button controlButton = findViewById(R.id.buttonControl);
        Button analyticsButton = findViewById(R.id.buttonAnalytics);

        networkButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NetworkActivity.class);
            startActivity(intent);
        });

        controlButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ControlActivity.class);
            startActivity(intent);
        });

        analyticsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AnalyticsActivity.class);
            startActivity(intent);
        });
    }
}
