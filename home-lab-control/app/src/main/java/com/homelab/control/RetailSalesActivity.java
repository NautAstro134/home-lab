package com.homelab.control;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RetailSalesActivity extends Activity {

    private TextView textRetailResult;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retail_sales);

        Button buttonTotalRevenue = findViewById(R.id.buttonTotalRevenue);
        Button buttonItemsSold = findViewById(R.id.buttonItemsSold);
        Button buttonOrders = findViewById(R.id.buttonOrders);

        textRetailResult = findViewById(R.id.textRetailResult);

        buttonTotalRevenue.setOnClickListener(v -> loadTotalRevenue());
        buttonItemsSold.setOnClickListener(v -> loadMetric("quantity", "ITEMS SOLD"));
        buttonOrders.setOnClickListener(v -> loadMetric("orders", "NUMBER OF ORDERS"));
    }

    private String getBaseUrl() {
        SharedPreferences prefs =
                getSharedPreferences("analytics_settings", MODE_PRIVATE);

        return prefs.getString("api_server", "");
    }

    private void loadTotalRevenue() {
        textRetailResult.setText("Loading...");

        executor.execute(() -> {
            try {
                URL url = new URL(getBaseUrl() + "/total-revenue");

                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                String response = reader.readLine();

                reader.close();
                connection.disconnect();

                JSONObject json = new JSONObject(response);

                double revenue = json.getDouble("total_revenue");
                String sql = json.optString("sql", "");

                mainHandler.post(() ->
                        textRetailResult.setText(
                                "TOTAL REVENUE\n\n" +
                                String.format("$%,.2f", revenue) +
                                "\n\nSQL:\n" +
                                sql
                        )
                );

            } catch (Exception e) {
                showError(e);
            }
        });
    }

    private void loadMetric(String metric, String title) {
        textRetailResult.setText("Loading...");

        executor.execute(() -> {
            try {
                URL url = new URL(
                        getBaseUrl() +
                        "/query?metric=" + metric +
                        "&group_by=none"
                );

                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(3000);
                connection.setReadTimeout(3000);

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );

                String response = reader.readLine();

                reader.close();
                connection.disconnect();

                JSONObject json = new JSONObject(response);

                double value = json.getDouble("value");
                String sql = json.optString("sql", "");

                mainHandler.post(() ->
                        textRetailResult.setText(
                                title + "\n\n" +
                                String.format("%,.0f", value) +
                                "\n\nSQL:\n" +
                                sql
                        )
                );

            } catch (Exception e) {
                showError(e);
            }
        });
    }

    private void showError(Exception e) {
        mainHandler.post(() ->
                textRetailResult.setText("ERROR\n" + e.getMessage())
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
