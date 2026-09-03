from flask import Flask, jsonify, request
import psycopg

app = Flask(__name__)

def get_connection():
    return psycopg.connect("dbname=labdb user=ram2")


@app.route("/total-revenue")
def total_revenue():
    with get_connection() as conn:
        with conn.cursor() as cur:
            cur.execute("""
                SELECT ROUND(
                    SUM(quantity * unit_price * (1 - discount))::numeric,
                    2
                )
                FROM sales_raw
            """)
            revenue = cur.fetchone()[0]

    return jsonify({"total_revenue": float(revenue)})


@app.route("/revenue-by-product")
def revenue_by_product():
    with get_connection() as conn:
        with conn.cursor() as cur:
            cur.execute("""
                SELECT
                    product,
                    ROUND(
                        SUM(quantity * unit_price * (1 - discount))::numeric,
                        2
                    ) AS revenue
                FROM sales_raw
                GROUP BY product
                ORDER BY revenue DESC
            """)
            rows = cur.fetchall()

    return jsonify([
        {"product": product, "revenue": float(revenue)}
        for product, revenue in rows
    ])


@app.route("/revenue-by-rep")
def revenue_by_rep():
    with get_connection() as conn:
        with conn.cursor() as cur:
            cur.execute("""
                SELECT
                    rep,
                    ROUND(
                        SUM(quantity * unit_price * (1 - discount))::numeric,
                        2
                    ) AS revenue
                FROM sales_raw
                GROUP BY rep
                ORDER BY revenue DESC
            """)
            rows = cur.fetchall()

    return jsonify([
        {"rep": rep, "revenue": float(revenue)}
        for rep, revenue in rows
    ])


@app.route("/query-options")
def query_options():
    from profile_loader import load_profile
    from analytics_adapters import get_adapter
    from query_engine import build_options_queries

    profile = load_profile("labdb")
    adapter = get_adapter(profile["database_type"])
    queries = build_options_queries(profile)

    with adapter.connect(profile) as conn:
        with conn.cursor() as cur:

            cur.execute(queries["reps"])
            reps = [row[0] for row in cur.fetchall()]

            cur.execute(queries["products"])
            products = [row[0] for row in cur.fetchall()]

            cur.execute(queries["regions"])
            regions = [row[0] for row in cur.fetchall()]

            cur.execute(queries["discounts"])
            discounts = [float(row[0]) for row in cur.fetchall()]

            cur.execute(queries["date_range"])
            min_date, max_date = cur.fetchone()

    return jsonify({
        "reps": reps,
        "products": products,
        "regions": regions,
        "discounts": discounts,
        "date_min": str(min_date),
        "date_max": str(max_date)
    })


@app.route("/query")
def universal_query():
    from profile_loader import load_profile
    from analytics_adapters import get_adapter
    from query_engine import build_query

    profile = load_profile("labdb")
    adapter = get_adapter(profile["database_type"])

    metric = request.args.get("metric", "revenue")
    group_by = request.args.get("group_by", "none")
    rep = request.args.get("rep", "All")
    product = request.args.get("product", "All")
    region = request.args.get("region", "All")
    discount = request.args.get("discount", "All")
    start_date = request.args.get("start_date")
    end_date = request.args.get("end_date")
    sort = request.args.get("sort", "desc")
    limit = request.args.get("limit", "All")

    filters = {
        "rep": rep,
        "product": product,
        "region": region,
        "discount": discount,
        "start_date": start_date,
        "end_date": end_date
    }

    try:
        sql, params = build_query(
            profile,
            metric,
            group_by,
            filters,
            sort,
            limit
        )

        with adapter.connect(profile) as conn:
            with conn.cursor() as cur:
                cur.execute(sql, params)
                rows = cur.fetchall()

    except Exception as e:
        return jsonify({"error": str(e)}), 400

    if group_by == "none":
        value = rows[0][0] if rows else None

        return jsonify({
            "sql": sql,
            "metric": metric,
            "group_by": "none",
            "value": round(float(value), 4)
            if value is not None else None
        })

    results = []

    for group_value, value in rows:
        results.append({
            "group": group_value,
            "value": round(float(value), 4)
        })

    return jsonify({
        "sql": sql,
        "metric": metric,
        "group_by": group_by,
        "results": results
    })


app.run(host="0.0.0.0", port=5000)
