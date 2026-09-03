package com.homelab.control;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.content.SharedPreferences;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QueriesActivity extends Activity {

    private Spinner spinnerMetric;
    private Spinner spinnerGroupBy;
    private Spinner spinnerRep;
    private Spinner spinnerProduct;
    private Spinner spinnerRegion;
    private Spinner spinnerDiscount;
    private Spinner spinnerSort;
    private Spinner spinnerLimit;

    private EditText editApiServer;
    private EditText editStartDate;
    private EditText editEndDate;

    private TextView queryResult;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);

        spinnerMetric = findViewById(R.id.spinnerMetric);
        spinnerGroupBy = findViewById(R.id.spinnerGroupBy);
        spinnerRep = findViewById(R.id.spinnerRep);
        spinnerProduct = findViewById(R.id.spinnerProduct);
        spinnerRegion = findViewById(R.id.spinnerRegion);
        spinnerDiscount = findViewById(R.id.spinnerDiscount);
        spinnerSort = findViewById(R.id.spinnerSort);
        spinnerLimit = findViewById(R.id.spinnerLimit);

        editApiServer = findViewById(R.id.editApiServer);

        SharedPreferences prefs =
                getSharedPreferences("analytics_settings", MODE_PRIVATE);

        editApiServer.setText(
                prefs.getString("api_server", "")
        );
        editStartDate = findViewById(R.id.editStartDate);
        editEndDate = findViewById(R.id.editEndDate);

        Button buttonConnectApi = findViewById(R.id.buttonConnectApi);
        Button buttonRunQuery = findViewById(R.id.buttonRunQuery);
        queryResult = findViewById(R.id.queryResult);

        setFixedOptions();

        if (!editApiServer.getText().toString().trim().isEmpty()) {
            loadVariableOptions();
        }

        buttonConnectApi.setOnClickListener(v -> {
            prefs.edit()
                    .putString(
                            "api_server",
                            editApiServer.getText().toString().trim()
                    )
                    .apply();

            loadVariableOptions();
        });
        buttonRunQuery.setOnClickListener(v -> runUniversalQuery());
    }

    private void setFixedOptions() {
        setSpinner(
                spinnerMetric,
                Arrays.asList(
                        "Revenue",
                        "Quantity",
                        "Orders",
                        "Average Revenue",
                        "Average Discount"
                )
        );

        setSpinner(
                spinnerGroupBy,
                Arrays.asList(
                        "None",
                        "Rep",
                        "Product",
                        "Region"
                )
        );

        setSpinner(
                spinnerSort,
                Arrays.asList(
                        "Top N",
                        "Bottom N"
                )
        );

        setSpinner(
                spinnerLimit,
                Arrays.asList(
                        "5",
                        "10",
                        "20",
                        "50",
                        "All"
                )
        );
    }

    private void loadVariableOptions() {
        queryResult.setText("Loading query options...");

        executor.execute(() -> {
            try {
                String response = getApiResponse("/query-options");
                JSONObject json = new JSONObject(response);

                List<String> reps =
                        jsonArrayToList(json.getJSONArray("reps"), "All");

                List<String> products =
                        jsonArrayToList(json.getJSONArray("products"), "All");

                List<String> regions =
                        jsonArrayToList(json.getJSONArray("regions"), "All");

                List<String> discounts = new ArrayList<>();
                discounts.add("All");

                JSONArray discountJson = json.getJSONArray("discounts");

                for (int i = 0; i < discountJson.length(); i++) {
                    double value = discountJson.getDouble(i);
                    discounts.add(String.format("%.0f%%", value * 100));
                }

                String minDate = json.getString("date_min");
                String maxDate = json.getString("date_max");

                mainHandler.post(() -> {
                    setSpinner(spinnerRep, reps);
                    setSpinner(spinnerProduct, products);
                    setSpinner(spinnerRegion, regions);
                    setSpinner(spinnerDiscount, discounts);

                    editStartDate.setText(minDate);
                    editEndDate.setText(maxDate);

                    queryResult.setText("Choose filters and run a query");
                });

            } catch (Exception e) {
                mainHandler.post(() ->
                        queryResult.setText("ERROR\n" + e.getMessage())
                );
            }
        });
    }

    private void runUniversalQuery() {
        queryResult.setText("Loading...");

        executor.execute(() -> {
            try {
                String metric = mapMetric(
                        spinnerMetric.getSelectedItem().toString()
                );

                String groupBy = spinnerGroupBy
                        .getSelectedItem()
                        .toString()
                        .toLowerCase();

                String rep = spinnerRep.getSelectedItem().toString();
                String product = spinnerProduct.getSelectedItem().toString();
                String region = spinnerRegion.getSelectedItem().toString();

                String discountText =
                        spinnerDiscount.getSelectedItem().toString();

                String discount = "All";

                if (!discountText.equals("All")) {
                    discount = String.valueOf(
                            Double.parseDouble(
                                    discountText.replace("%", "")
                            ) / 100.0
                    );
                }

                String startDate = editStartDate.getText().toString();
                String endDate = editEndDate.getText().toString();

                String sort = spinnerSort
                        .getSelectedItem()
                        .toString()
                        .equals("Bottom N")
                        ? "asc"
                        : "desc";

                String limit = spinnerLimit.getSelectedItem().toString();

                String endpoint =
                        "/query?metric=" + encode(metric) +
                        "&group_by=" + encode(groupBy) +
                        "&rep=" + encode(rep) +
                        "&product=" + encode(product) +
                        "&region=" + encode(region) +
                        "&discount=" + encode(discount) +
                        "&start_date=" + encode(startDate) +
                        "&end_date=" + encode(endDate) +
                        "&sort=" + encode(sort) +
                        "&limit=" + encode(limit);

                String response = getApiResponse(endpoint);

                JSONObject json = new JSONObject(response);
                String sql = json.optString("sql", "");

                if (groupBy.equals("none")) {
                    double value = json.getDouble("value");

                    mainHandler.post(() ->
                            queryResult.setText(
                                    formatSingleResult(metric, value) + "\n\nSQL:\n" + sql
                            )
                    );

                    return;
                }

                JSONArray results = json.getJSONArray("results");
                StringBuilder output = new StringBuilder();

                output.append(
                        spinnerMetric.getSelectedItem().toString()
                                .toUpperCase()
                );

                output.append(" BY ");

                output.append(
                        spinnerGroupBy.getSelectedItem().toString()
                                .toUpperCase()
                );

                output.append("\n\n");

                for (int i = 0; i < results.length(); i++) {
                    JSONObject item = results.getJSONObject(i);

                    String group = item.getString("group");
                    double value = item.getDouble("value");

                    output.append(i + 1)
                            .append(". ")
                            .append(group)
                            .append("  ")
                            .append(formatValue(metric, value))
                            .append("\n");
                }

                mainHandler.post(() ->
                       queryResult.setText(output.toString() + "\nSQL:\n" + sql)
                );

            } catch (Exception e) {
                mainHandler.post(() ->
                        queryResult.setText("ERROR\n" + e.getMessage())
                );
            }
        });
    }

    private String mapMetric(String selected) {
        switch (selected) {
            case "Quantity":
                return "quantity";

            case "Orders":
                return "orders";

            case "Average Revenue":
                return "average_revenue";

            case "Average Discount":
                return "average_discount";

            default:
                return "revenue";
        }
    }

    private String formatSingleResult(String metric, double value) {
        return spinnerMetric.getSelectedItem().toString().toUpperCase()
                + "\n\n"
                + formatValue(metric, value);
    }

    private String formatValue(String metric, double value) {
        if (metric.equals("revenue") ||
                metric.equals("average_revenue")) {

            return String.format("$%,.2f", value);
        }

        if (metric.equals("average_discount")) {
            return String.format("%.2f%%", value * 100);
        }

        return String.format("%,.0f", value);
    }

    private String encode(String value) throws Exception {
        return URLEncoder.encode(value, "UTF-8");
    }

    private List<String> jsonArrayToList(
            JSONArray json,
            String firstValue
    ) throws Exception {

        List<String> values = new ArrayList<>();
        values.add(firstValue);

        for (int i = 0; i < json.length(); i++) {
            values.add(json.getString(i));
        }

        return values;
    }

    private void setSpinner(
            Spinner spinner,
            List<String> values
    ) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                values
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        spinner.setAdapter(adapter);
    }

    private String getApiResponse(String endpoint) throws Exception {
        String baseUrl = editApiServer.getText().toString().trim();

        URL url = new URL(
                baseUrl + endpoint
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

        return response;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdownNow();
    }
}
