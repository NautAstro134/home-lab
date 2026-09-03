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

public class NucActivity extends Activity {

    private TextView nucResult;
    private Button statusButton;
    private Button pingButton;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String NUC_IP = "192.168.1.121";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuc);

        nucResult = findViewById(R.id.nucResult);
        statusButton = findViewById(R.id.buttonNucStatus);
        pingButton = findViewById(R.id.buttonNucPing);

        statusButton.setOnClickListener(v -> checkStatus());
        pingButton.setOnClickListener(v -> pingNuc());
    }

    private void checkStatus() {
        pingNuc();
    }

    private void pingNuc() {
        nucResult.setText("Checking " + NUC_IP + "...");

        executor.execute(() -> {
            boolean reachable = false;

            try {
                InetAddress address = InetAddress.getByName(NUC_IP);
                reachable = address.isReachable(1000);
            } catch (Exception ignored) {
            }

            boolean finalReachable = reachable;

            mainHandler.post(() -> {
                if (finalReachable) {
                    nucResult.setText("NUC " + NUC_IP + "\nONLINE");
                } else {
                    nucResult.setText("NUC " + NUC_IP + "\nNOT REACHABLE");
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
