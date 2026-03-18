package com.yourpackage.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.*;
import software.amazon.awssdk.services.ses.model.*;
import software.amazon.awssdk.regions.Region;

@Service
public class EmailService {

    private final SesClient sesClient;

    public EmailService() {
        this.sesClient = SesClient.builder()
                .region(Region.AP_SOUTH_1) // Mumbai region
                .build();
    }

    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest request = SendEmailRequest.builder()
                .destination(Destination.builder()
                        .toAddresses(to)
                        .build())
                .message(Message.builder()
                        .subject(Content.builder().data(subject).build())
                        .body(Body.builder()
                                .text(Content.builder().data(body).build())
                                .build())
                        .build())
                .source("minikamatma123@gmail.com") // MUST be verified in SES
                .build();

        sesClient.sendEmail(request);
    }
}
