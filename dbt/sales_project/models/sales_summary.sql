SELECT
    region,
    rep,
    product,
    COUNT(*)                                                         AS total_orders,
    SUM(quantity)                                                    AS total_units,
    ROUND(SUM(quantity * unit_price * (1 - discount))::numeric, 2)  AS total_revenue
FROM sales_raw
GROUP BY region, rep, product
ORDER BY total_revenue DESC
