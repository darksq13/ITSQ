# SOC Network Monitoring Tool

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-green)](https://opensource.org/licenses/MIT)

A Splunk-like Security Operations Center (SOC) network monitoring tool with Web GUI built in Java/Spring Boot for log aggregation, analysis, and alerting.

![Dashboard Screenshot](./screenshots/dashboard.png)

## Features

- **Log Ingestion**: Collects logs from multiple sources (files, syslog, HTTP)
- **Real-time Processing**: Processes and analyzes logs as they arrive
- **Powerful Search**: Full-text search with filtering capabilities
- **Alerting System**: Configurable rules for security alerts
- **Visual Dashboard**: Interactive charts and statistics
- **User Management**: Role-based access control

## Technology Stack

- **Backend**: Java 17, Spring Boot 3.2
- **Frontend**: Thymeleaf, Chart.js, Bootstrap
- **Database**: PostgreSQL (with H2 for development)
- **Search**: Elasticsearch (optional)
- **Message Broker**: Apache Kafka

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- PostgreSQL 14+ (or H2 for development)
- (Optional) Elasticsearch 8.x
- (Optional) Kafka 3.x

### Installation

1. Clone the repository:
```bash
git clone https://github.com/darksq13/SOC-tools.git
cd soc-tool
