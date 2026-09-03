package com.homelab.control;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SleekbookActivity extends Activity {

    private TextView sleekbookResult;
    private Button statusButton;
    private Button pingButton;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String SLEEKBOOK_IP = "192.168.1.130";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleekbook);

        sleekbookResult = findViewById(R.id.sleekbookResult);
        statusButton = findViewById(R.id.buttonSleekbookStatus);
        pingButton = findViewById(R.id.buttonSleekbookPing);

        statusButton.setOnClickListener(v -> checkStatus());
        pingButton.setOnClickListener(v -> pingSleekbook());
    }

    private void checkStatus() {
        pingSleekbook();
    }

    private void pingSleekbook() {
        sleekbookResult.setText("Checking " + SLEEKBOOK_IP + "...");

        executor.execute(() -> {
            boolean reachable = false;

            try {
                InetAddress address = InetAddress.getByName(SLEEKBOOK_IP);
                reachable = address.isReachable(1000);
            } catch (Exception ignored) {
            }

            boolean finalReachable = reachable;

            mainHandler.post(() -> {
                if (finalReachable) {
                    sleekbookResult.setText("SLEEKBOOK " + SLEEKBOOK_IP + "\nONLINE");
                } else {
                    sleekbookResult.setText("SLEEKBOOK " + SLEEKBOOK_IP + "\nNOT REACHABLE");
                }
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
