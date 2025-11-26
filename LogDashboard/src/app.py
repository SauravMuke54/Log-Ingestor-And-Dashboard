import streamlit as st
from db.mongointerface import MongoInterface
import pandas as pd
import plotly.express as px
import json

# =========================================================
# PAGE CONFIG
# =========================================================
st.set_page_config(page_title="Log Analytics Dashboard", layout="wide")
st.title("📊 Log Analytics & Monitoring Dashboard")
st.markdown("A modern, selective, application-focused log monitoring dashboard.")

# =========================================================
# FETCH DATA
# =========================================================
mongo = MongoInterface()
logs = mongo.get_all_logs()

df = pd.DataFrame(logs)
df["timestamp"] = pd.to_datetime(df["timestamp"])
df["date"] = df["timestamp"].dt.date

# =========================================================
# SIDEBAR FILTERS
# =========================================================
st.sidebar.header("🔎 Filters")

all_levels = sorted(df["level"].unique().tolist())
all_services = sorted(df["serviceName"].unique().tolist())
all_dates = sorted(df["date"].unique())

level_filter = st.sidebar.multiselect("Log Levels", all_levels, default=all_levels)
service_filter = st.sidebar.multiselect("Applications", all_services, default=all_services)

date_range = st.sidebar.date_input(
    "Date Range",
    [min(all_dates), max(all_dates)]
)

search_text = st.sidebar.text_input("Search Text")

# =========================================================
# APPLY FILTERS
# =========================================================
filtered_df = df.copy()

filtered_df = filtered_df[filtered_df["level"].isin(level_filter)]
filtered_df = filtered_df[filtered_df["serviceName"].isin(service_filter)]
filtered_df = filtered_df[(filtered_df["date"] >= date_range[0]) & (filtered_df["date"] <= date_range[1])]

if search_text:
    filtered_df = filtered_df[
        filtered_df.apply(lambda r: search_text.lower() in str(r).lower(), axis=1)
    ]

# =========================================================
# METRICS ROW
# =========================================================
st.markdown("### 📌 Overview Metrics")

col1, col2, col3, col4 = st.columns(4)
col1.metric("📜 Total Logs", len(filtered_df))
col2.metric("❌ Error Logs", len(filtered_df[filtered_df["level"] == "ERROR"]))
col3.metric("⚠️ Warnings", len(filtered_df[filtered_df["level"] == "WARN"]))
col4.metric("🔥 Critical Logs", len(filtered_df[filtered_df["level"] == "FATAL"]))

st.markdown("---")

# =========================================================
# DATE-WISE TREND GRAPH
# =========================================================
st.subheader("📅 Log Trend (Date-wise)")

if not filtered_df.empty:
    trend = filtered_df.groupby(["date", "level"]).size().reset_index(name="count")

    fig = px.line(
        trend,
        x="date",
        y="count",
        color="level",
        markers=True,
        title="Log Trend Over Time"
    )
    st.plotly_chart(fig, use_container_width=True)
else:
    st.info("No logs available for selected filters.")

st.markdown("---")

# =========================================================
# SERVICE-WISE DISTRIBUTION
# =========================================================
st.subheader("🧩 Logs by Application / Service")

if not filtered_df.empty:
    svc_count = filtered_df.groupby("serviceName").size().reset_index(name="count")

    fig2 = px.bar(
        svc_count,
        x="serviceName",
        y="count",
        text="count",
        title="Logs per Application",
    )
    fig2.update_traces(textposition="outside")
    st.plotly_chart(fig2, use_container_width=True)
else:
    st.info("No logs available.")

st.markdown("---")

# =========================================================
# LOG TABLE
# =========================================================
st.subheader("📄 Log Records (Filtered)")

st.dataframe(
    filtered_df[["timestamp", "level", "serviceName", "message"]],
    use_container_width=True,
    height=350
)

st.markdown("---")

# =========================================================
# APPLICATION-SPECIFIC DETAILED VIEWER + NEW CHART
# =========================================================
st.subheader("🧩 Detailed Logs — Application Specific")

app_choice = st.selectbox(
    "Select Application",
    ["All"] + all_services,
    index=0
)

if app_choice != "All":
    detail_df = filtered_df[filtered_df["serviceName"] == app_choice]
else:
    detail_df = filtered_df

if detail_df.empty:
    st.info("No logs available for this selection.")
else:

    # =========================================================
    # NEW: APPLICATION LOG DISTRIBUTION PIE/BAR CHART
    # =========================================================
    st.markdown("### 📊 Log Distribution for Selected Application")

    chart_type = st.radio("Chart Type", ["Pie Chart", "Bar Chart"], horizontal=True)

    level_count = detail_df["level"].value_counts().reset_index()
    level_count.columns = ["level", "count"]

    if chart_type == "Pie Chart":
        fig3 = px.pie(
            level_count,
            names="level",
            values="count",
            title=f"Log Level Distribution — {app_choice}"
        )
    else:
        fig3 = px.bar(
            level_count,
            x="level",
            y="count",
            text="count",
            title=f"Log Levels — {app_choice}"
        )
        fig3.update_traces(textposition="outside")

    st.plotly_chart(fig3, use_container_width=True)
    st.markdown("---")

    # =========================================================
    # LEVEL-WISE TABS OF LOGS
    # =========================================================
    levels_in_app = sorted(detail_df["level"].unique().tolist())
    level_tabs = st.tabs(levels_in_app)

    for tab, lvl in zip(level_tabs, levels_in_app):
        with tab:
            sub = detail_df[detail_df["level"] == lvl]

            st.write(f"### {lvl} — {len(sub)} logs")

            df = pd.DataFrame(sub,columns = ["date", "level", "message","serviceName"])
            st.dataframe(df)

