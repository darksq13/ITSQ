package com.itsq.soc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.itsq.soc.model.Alert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailAlertService {

    private final JavaMailSender mailSender;
    private final String fromEmail;
    private final String[] recipients;
    private final String itsqSupportPhone;

    public EmailAlertService(JavaMailSender mailSender,
                           @Value("${itsq.soc.alerts.email.from}") String fromEmail,
                           @Value("${itsq.soc.alerts.email.recipients}") String[] recipients,
                           @Value("${itsq.support.phone}") String itsqSupportPhone) {
        this.mailSender = mailSender;
        this.fromEmail = fromEmail;
        this.recipients = recipients;
        this.itsqSupportPhone = itsqSupportPhone;
    }

    public void sendAlertEmail(Alert alert) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        
        helper.setFrom(fromEmail);
        helper.setTo(recipients);
        helper.setSubject("[ITSQ SOC Alert] " + alert.getSeverity() + ": " + alert.getTitle());
        
        String htmlContent = String.format("""
            <html>
                <body>
                    <h2 style="color: #0066cc;">ITSQ Security Operations Center Alert</h2>
                    <div style="border-left: 4px solid #0066cc; padding-left: 10px;">
                        <h3>%s</h3>
                        <p><strong>Time:</strong> %s</p>
                        <p><strong>Severity:</strong> <span style="color: %s;">%s</span></p>
                        <p><strong>Source:</strong> %s</p>
                        <p>%s</p>
                    </div>
                    <div style="margin-top: 20px;">
                        <h4>Recommended Actions:</h4>
                        <p>%s</p>
                    </div>
                    <div style="margin-top: 30px; font-size: 0.9em; color: #666;">
                        <p>ITSQ SOC Team<br>
                        Phone: %s</p>
                    </div>
                </body>
            </html>
            """,
            alert.getTitle(),
            alert.getTimestamp(),
            getSeverityColor(alert.getSeverity()),
            alert.getSeverity(),
            alert.getSource(),
            alert.getMessage(),
            alert.getRecommendedActions(),
            itsqSupportPhone
        );
        
        helper.setText(htmlContent, true);
        mailSender.send(message);
    }

    private String getSeverityColor(Alert.Severity severity) {
        return switch (severity) {
            case CRITICAL -> "#ff0000";
            case HIGH -> "#ff6600";
            case MEDIUM -> "#ffcc00";
            case LOW -> "#0066cc";
            default -> "#666666";
        };
    }
}
