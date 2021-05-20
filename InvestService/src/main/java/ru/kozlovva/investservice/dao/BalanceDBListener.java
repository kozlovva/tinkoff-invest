package ru.kozlovva.investservice.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BalanceDBListener extends AbstractMongoEventListener<BalanceInfo> {

    private final SimpMessagingTemplate messagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onAfterSave(AfterSaveEvent<BalanceInfo> event) {
        super.onAfterSave(event);
        try {
            messagingTemplate.convertAndSend("/topic/profile", objectMapper.writeValueAsString(event.getSource()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
