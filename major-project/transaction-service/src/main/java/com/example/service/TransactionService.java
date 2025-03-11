package com.example.service;

import com.example.config.KafkaConfig;
import com.example.dto.TransactionCreateRequest;
import com.example.models.Transaction;
import com.example.models.TransactionStatus;
import com.example.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final String TRANSACTION_CREATED = "transaction_created";

    public String transact(TransactionCreateRequest request) throws JsonProcessingException {
        Transaction transaction = Transaction.builder()
                .senderId(request.getSender())
                .receiverId(request.getReceiver())
                .externalId(UUID.randomUUID().toString())
                .transactionStatus(TransactionStatus.PENDING)
                .reason(request.getReason())
                .amount(request.getAmount())
                .build();

        transactionRepository.save(transaction);

        JSONObject obj = new JSONObject();

        obj.put("senderId", transaction.getSenderId());
        obj.put("receiverId",transaction.getReceiverId());
        obj.put("amount",transaction.getAmount());
        obj.put("transactionId",transaction.getExternalId());

        kafkaTemplate.send(TRANSACTION_CREATED,objectMapper.writeValueAsString(obj));

        return transaction.getExternalId();
    }

}
