package com.homelab.control;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkActivity extends Activity {

    private TextView terryzaInfo;
    private TextView linksysInfo;
    private Button scanButton;

    private final ExecutorService executor = Executors.newFixedThreadPool(20);
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        terryzaInfo = findViewById(R.id.terryzaInfo);
        linksysInfo = findViewById(R.id.linksysInfo);
        scanButton = findViewById(R.id.buttonScan);

        scanButton.setOnClickListener(v -> scanNetwork());
    }

    private String getLocalIPv4() {
        try {
            for (NetworkInterface ni :
                    Collections.list(NetworkInterface.getNetworkInterfaces())) {

                for (InetAddress address :
                        Collections.list(ni.getInetAddresses())) {

                    String ip = address.getHostAddress();

                    if (!address.isLoopbackAddress()
                            && ip != null
                            && ip.indexOf(':') < 0
                            && address.isSiteLocalAddress()) {
                        return ip;
                    }
                }
            }
        } catch (Exception ignored) {
        }

        return null;
    }

    private void scanNetwork() {
        scanButton.setEnabled(false);
        scanButton.setText("SCANNING...");

        executor.execute(() -> {

            String localIp = getLocalIPv4();

            if (localIp == null) {
                mainHandler.post(() -> {
                    terryzaInfo.setText("No local network detected");
                    linksysInfo.setText("No local network detected");
                    scanButton.setEnabled(true);
                    scanButton.setText("SCAN NETWORK");
                });
                return;
            }

            int lastDot = localIp.lastIndexOf('.');
            String subnet = localIp.substring(0, lastDot + 1);

            List<String> activeIps = new ArrayList<>();

            for (int i = 1; i <= 254; i++) {
                String ip = subnet + i;

                try {
                    InetAddress address = InetAddress.getByName(ip);

                    if (address.isReachable(200)) {
                        activeIps.add(ip);
                    }
                } catch (Exception ignored) {
                }
            }

            mainHandler.post(() -> {
                StringBuilder result = new StringBuilder();

                result.append("Phone IP: ")
                      .append(localIp)
                      .append("\n\n");

                if (activeIps.isEmpty()) {
                    result.append("No other devices detected.");
                } else {
                    result.append("Active devices:\n");

                    for (String ip : activeIps) {
                        result.append(ip).append("\n");
                    }
                }

                terryzaInfo.setText(result.toString());
                linksysInfo.setText("Network: " + subnet + "0/24");

                scanButton.setEnabled(true);
                scanButton.setText("SCAN NETWORK");
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
