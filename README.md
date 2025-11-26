# Log-Ingestor-And-Dashboard

A distributed log ingestion and monitoring platform with real-time analytics dashboard.

---

## Overview

**Log-Ingestor-And-Dashboard** provides scalable log ingestion via Kafka and real-time analytics through a rich dashboard. It combines a Java-based log generator/ingestor, a Kafka-powered pipeline, and a Streamlit+MongoDB log analytics dashboard. The solution helps you collect, search, visualize, and troubleshoot logs from multiple distributed applications.

---

## Components

### 1. Log Generator/Ingestor (Java + Kafka)
- **Generates and ingests logs** from sample applications/services.
- **Kafka** acts as the core distributed buffer to handle log streams.
- Example log types: `INFO`, `DEBUG`, `WARN`, `ERROR`, `FATAL` from services like Auth, Billing, Payment, Notification, Inventory, etc.

### 2. Log Storage (MongoDB)
- Stores and indexes ingested logs for analytics and retrieval.
- Connection credentials set via environment variables.

### 3. Log Analytics Dashboard (Python, Streamlit)
- Visualizes, analyzes, and monitors log data interactively.
- Connects to MongoDB to query and display logs.
- Built using [Streamlit](https://streamlit.io/) and [Plotly](https://plotly.com/).

---

## Dashboard Features

- **Sidebar Filters:**  
  - Log level, application/service name, date range, text search.
- **Overview Metrics Row:**  
  - Total logs, error logs, warnings, critical logs.
- **Date-wise Trend Graph:**  
  - Line chart showing log frequency & levels over time.
- **Service-wise Distribution:**  
  - Bar chart: logs per service/application.
- **Filtered Log Table:**  
  - Paginated table for viewing log records.
- **App-specific Detailed View:**  
  - Pie/bar chart log distribution by severity in selected app/service.
  - Tabs to browse logs by severity (`ERROR`, `WARN`, `INFO`, etc.).
- **All visualizations update instantly with filter changes.**

---

## Technologies Used

- **Java:** Backend log generator/ingestor
- **Apache Kafka:** Distributed message backbone for logs
- **Python (Streamlit, Pandas, Plotly):** Dashboard UI and analytics
- **MongoDB:** Log storage for search and dashboard queries

---

## Getting Started

### Prerequisites

- Java 8+
- Python 3.8+
- MongoDB instance (connection string in `.env`)
- Kafka cluster

### Installation & Run

**Clone the repository:**
```sh
git clone https://github.com/SauravMuke54/Log-Ingestor-And-Dashboard.git
cd Log-Ingestor-And-Dashboard
```

**Set up log generator (Java):**
- Build/run the application in `LogGeneratorApplication/` (use Maven/Gradle).

**Set up MongoDB:**
- Set environment variables:
  ```
  MONGO_URI=<your-mongo-uri>
  DATABASE_NAME=<db>
  COLLECTION_NAME=<collection>
  ```
  (see `LogDashboard/src/utils/env.py` for details)

**Run dashboard (Python):**
```sh
cd LogDashboard/src
pip install -r ../logdashboard.egg-info/requires.txt
streamlit run app.py
```

---

## Folder Structure

```
Log-Ingestor-And-Dashboard/
├── LogDashboard/
│   └── src/
│       ├── app.py           # Streamlit dashboard
│       ├── db/              # MongoDB interface
│       ├── apis/            # API helper logic
│       └── utils/           # Utility functions (.env management)
├── LogGeneratorApplication/
│   └── src/main/java/       # Java log generator
│       └── com/saurav/LogGeneratorApplication/
└── README.md
```

---

## Contributing

Contributions welcome—please open issues or pull requests!

---

## Author

[SauravMuke54](https://github.com/SauravMuke54)

For questions, open an [issue](https://github.com/SauravMuke54/Log-Ingestor-And-Dashboard/issues).

---
