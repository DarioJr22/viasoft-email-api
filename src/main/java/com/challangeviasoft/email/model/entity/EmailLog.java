package com.challangeviasoft.email.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("email_log")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailLog {
    @Id
    private String id;
    private String eventType; // "SENT", "DELIVERED", "OPENED"
    private String recipientEmail;
    private String senderEmail;
    private String subject;
    private String content;
    private Long timestamp;

    @TimeToLive(unit = TimeUnit.DAYS) //Expired after 30 days
    private Long ttl = 30L;

    public EmailLog(String id, String eventType, String recipientEmail, String senderEmail, String subject, String content) {
        this.id = id;
        this.eventType = eventType;
        this.recipientEmail = recipientEmail;
        this.senderEmail = senderEmail;
        this.subject = subject;
        this.content = content;
        this.timestamp = System.currentTimeMillis();
    }


}
