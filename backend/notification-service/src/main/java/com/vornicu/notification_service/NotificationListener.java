package com.vornicu.notification_service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

//    private final EmailService emailService;
//    private final TemplateService templateService;
//
//
//    @KafkaListener(topics = "measurement-alerts", groupId = "notification-service")
//    public void receive(String message) {
//
//        JSONObject json = new JSONObject(message);
//
//        String email = json.optString("ownerEmail", "unknown@none.com");
//        String deviceId = json.optString("deviceId", "N/A");
//        String temperature = json.optString("temperature", "N/A");
//        String timestamp = json.optString("timestamp", "N/A");
//

//        String value = json.optString("value", temperature);
//
//
//        String html = templateService.loadTemplate("alert.html");
//
//        html = html.replace("{{deviceId}}", deviceId)
//                .replace("{{value}}", value)
//                .replace("{{temperature}}", temperature)
//                .replace("{{timestamp}}", timestamp);
//
//        emailService.sendHtmlEmail(email, "Alertă de măsurători", html);
//
//        System.out.println("EMAIL TRIMIS -> " + email);
//    }

    private final EmailService emailService;
    private final TemplateService templateService;

    private final Map<String, Instant> lastAlertTime = new ConcurrentHashMap<>();
    private static final double CRITICAL_TEMP = 70.0;

    @KafkaListener(topics = "measurement-alerts", groupId = "notification-service")
    public void receive(String message) {

        try {
            JSONObject json = new JSONObject(message);

            Object deviceObj = json.opt("deviceId");
            String deviceId = deviceObj == null ? "N/A" : deviceObj.toString();

            Object tempObj = json.opt("temperature");
            String temperatureStr = tempObj == null ? "0" : tempObj.toString();
            double temperature = Double.parseDouble(temperatureStr);

            String email = json.opt("ownerEmail") != null ? json.opt("ownerEmail").toString() : "unknown@none.com";
            String timestamp = json.opt("timestamp") != null ? json.opt("timestamp").toString() : "N/A";


            if (temperature < CRITICAL_TEMP) {
                log.info("Temperatura {} sub prag. Nu se trimite email.", temperature);
                return;
            }

//            rate limiting
            Instant lastSent = lastAlertTime.get(deviceId);
            Instant now = Instant.now();

            if (lastSent != null && Duration.between(lastSent, now).toMinutes() < 10) {
                log.warn("Alertă ignorată — trimisă recent pentru device {}", deviceId);
                return;
            }

            lastAlertTime.put(deviceId, now);

            String html = templateService.loadTemplate("alert.html")
                    .replace("{{deviceId}}", deviceId)
                    .replace("{{value}}", temperatureStr)
                    .replace("{{temperature}}", temperatureStr)
                    .replace("{{timestamp}}", timestamp);

            emailService.sendHtmlEmail(email, "Alertă – Temperatura critică", html);
            log.info("EMAIL TRIMIS către {}", email);

        } catch (Exception e) {
            // asta e important ca Kafka să nu facă retry loop nesfârșit
            log.error("Eroare la procesare mesaj (IGNOR MESAJUL): {}", e.getMessage(), e);
        }
    }

}
