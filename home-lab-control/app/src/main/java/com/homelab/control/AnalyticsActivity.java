package com.homelab.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AnalyticsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        Button queriesButton = findViewById(R.id.buttonQueries);

        queriesButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnalyticsActivity.this, QueriesActivity.class);
            startActivity(intent);
        });
    }
}
