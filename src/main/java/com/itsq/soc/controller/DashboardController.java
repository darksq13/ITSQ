package com.itsq.soc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping
    public String dashboard(Model model) {
        // In a real app, this would come from a service
        model.addAttribute("recentAlerts", Arrays.asList(
            new Alert("Unauthorized access attempt", "high"),
            new Alert("Multiple failed logins", "medium"),
            new Alert("New vulnerability detected", "critical")
        ));
        return "dashboard";
    }

    @GetMapping("/search")
    @ResponseBody
    public List<LogEntry> search(@RequestParam String query) {
        // In a real app, this would search your database
        return Arrays.asList(
            new LogEntry("2023-01-01T12:00:00", "firewall", "Blocked suspicious IP: 192.168.1.100"),
            new LogEntry("2023-01-01T12:01:00", "auth", "Failed login for user: admin")
        );
    }

    // Simple inner classes for demo purposes
    static class Alert {
        private String message;
        private String severity;

        public Alert(String message, String severity) {
            this.message = message;
            this.severity = severity;
        }

        // Getters
        public String getMessage() { return message; }
        public String getSeverity() { return severity; }
    }

    static class LogEntry {
        private String timestamp;
        private String source;
        private String message;

        public LogEntry(String timestamp, String source, String message) {
            this.timestamp = timestamp;
            this.source = source;
            this.message = message;
        }

        // Getters
        public String getTimestamp() { return timestamp; }
        public String getSource() { return source; }
        public String getMessage() { return message; }
    }
}
