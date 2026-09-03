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

public class OpenWrtActivity extends Activity {

    private TextView openWrtResult;
    private Button statusButton;
    private Button pingButton;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    private static final String OPENWRT_IP = "192.168.1.1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_openwrt);

        openWrtResult = findViewById(R.id.openWrtResult);
        statusButton = findViewById(R.id.buttonOpenWrtStatus);
        pingButton = findViewById(R.id.buttonOpenWrtPing);

        statusButton.setOnClickListener(v -> checkStatus());
        pingButton.setOnClickListener(v -> pingOpenWrt());
    }

    private void checkStatus() {
        pingOpenWrt();
    }

    private void pingOpenWrt() {
        openWrtResult.setText("Checking " + OPENWRT_IP + "...");

        executor.execute(() -> {
            boolean reachable = false;

            try {
                InetAddress address = InetAddress.getByName(OPENWRT_IP);
                reachable = address.isReachable(1000);
            } catch (Exception ignored) {
            }

            boolean finalReachable = reachable;

            mainHandler.post(() -> {
                if (finalReachable) {
                    openWrtResult.setText("OPENWRT " + OPENWRT_IP + "\nONLINE");
                } else {
                    openWrtResult.setText("OPENWRT " + OPENWRT_IP + "\nNOT REACHABLE");
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
