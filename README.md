# 🔍 PingWatch — Real-Time Uptime Monitor

A production-style website uptime monitoring REST API built with Java and Spring Boot.

## 🚀 What It Does
- Monitors multiple websites automatically every 60 seconds
- Detects when websites go DOWN and tracks recovery
- Calculates uptime percentage over time
- Displays real-time status on a live dashboard
- Logs every ping with response time and timestamp

## 🛠️ Tech Stack
- **Language:** Java 21
- **Framework:** Spring Boot 3.5
- **Database:** H2 (in-memory)
- **ORM:** Spring Data JPA
- **Logging:** SLF4J
- **Frontend:** HTML, CSS, JavaScript

## 📡 API Endpoints
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/monitor | Add a URL to monitor |
| GET | /api/status | Get uptime % for all URLs |
| GET | /api/logs?url= | Get ping history for a URL |
| GET | /api/ping?url= | Manually ping a URL |

## 🏃 How to Run Locally
1. Clone the repo
2. Open in IntelliJ IDEA
3. Run PingwatchApplication.java
4. Open http://localhost:8080

## 💡 Real World Use Case
Companies pay $7-25/month for tools like UptimeRobot
and Pingdom that do exactly this. PingWatch replicates
core uptime monitoring functionality from scratch.