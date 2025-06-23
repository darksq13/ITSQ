package com.itsq.soc.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Alert {
    public enum Severity {
        CRITICAL, HIGH, MEDIUM, LOW, INFO
    }
    
    public enum Type {
        SECURITY, PERFORMANCE, AVAILABILITY, BRUTE_FORCE, MALWARE
    }
    
    private String id;
    private String title;
    private String message;
    private Severity severity;
    private Type type;
    private String source;
    private LocalDateTime timestamp;
    private String recommendedActions;
    private String itsqCaseId;  // ITSQ-specific case tracking
    
    public boolean isHighOrCritical() {
        return severity == Severity.HIGH || severity == Severity.CRITICAL;
    }
}
