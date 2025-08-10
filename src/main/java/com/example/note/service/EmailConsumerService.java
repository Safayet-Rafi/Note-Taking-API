package com.example.note.service;

import com.example.note.dto.response.EmailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailConsumerService {

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    public void receiveMessage(EmailMessage message) {
        log.info("Simulated Email Sent To: {}\nSubject: {}\nBody: {}",
                message.to(),
                message.subject(),
                message.body());
    }
}
