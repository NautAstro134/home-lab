package com.homelab.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ControlActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        Button nucButton = findViewById(R.id.buttonNUC);
        Button sleekbookButton = findViewById(R.id.buttonSleekbook);
        Button openWrtButton = findViewById(R.id.buttonOpenWrt);

        nucButton.setOnClickListener(v -> {
            Intent intent = new Intent(ControlActivity.this, NucActivity.class);
            startActivity(intent);
        });

        sleekbookButton.setOnClickListener(v -> {
            Intent intent = new Intent(ControlActivity.this, SleekbookActivity.class);
            startActivity(intent);
        });

        openWrtButton.setOnClickListener(v -> {
            Intent intent = new Intent(ControlActivity.this, OpenWrtActivity.class);
            startActivity(intent);
        });
    }
}
