package com.itsq.soc.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class FirewallService {

    private final String apiUrl;
    private final String apiKey;
    private final boolean autoBlockEnabled;
    private final RestTemplate restTemplate;

    public FirewallService(
            @Value("${itsq.firewall.api.url}") String apiUrl,
            @Value("${itsq.firewall.api.key}") String apiKey,
            @Value("${itsq.firewall.auto-block.enabled}") boolean autoBlockEnabled) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.autoBlockEnabled = autoBlockEnabled;
        this.restTemplate = new RestTemplate();
    }

    public void blockIpAddress(String ipAddress, String reason) {
        if (!autoBlockEnabled) {
            throw new IllegalStateException("ITSQ Firewall auto-block is disabled");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-ITSQ-API-KEY", apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> request = new HashMap<>();
        request.put("ip_address", ipAddress);
        request.put("reason", reason);
        request.put("origin", "ITSQ SOC");
        request.put("duration", "24h");

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
            apiUrl + "/block",
            HttpMethod.POST,
            entity,
            String.class
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("ITSQ Firewall API request failed: " + response.getBody());
        }
    }

    public String getFirewallStatus() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-ITSQ-API-KEY", apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
            apiUrl + "/status",
            HttpMethod.GET,
            entity,
            String.class
        );

        return response.getBody();
    }
}
