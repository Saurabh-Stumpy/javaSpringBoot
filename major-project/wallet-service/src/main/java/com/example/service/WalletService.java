package com.example.service;

import com.example.models.Wallet;
import com.example.repository.WalletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private static final String USER_CREATED_TOPIC = "user_created";

    private static final  String TRANSACTION_CREATED_TOPIC = "transaction_created";

    private static final String WALLET_UPDATED_TOPIC = "wallet_updated";


    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper =  new ObjectMapper();

    @Autowired
    WalletRepository walletRepository;

    @Value("${wallet,initial.balance}")
    long balance;

    // User onBoarding flow

    @KafkaListener(topics = {USER_CREATED_TOPIC}, groupId = "jbdl123")
    public void createWallet(String msg) throws ParseException {

        JSONObject obj = (JSONObject) new JSONParser().parse(msg);

        String walletId = (String) obj.get("phone");

        Wallet wallet = Wallet.builder()
                .walletId(walletId)
                .currency("INR")
                .balance(balance)
                .build();

        walletRepository.save(wallet);

        // TODO: Publish an event of wallet_creation

    }

    // User tansaction flow


    @KafkaListener(topics = {TRANSACTION_CREATED_TOPIC}, groupId = "jbdl123")
    public void updateWallet(String msg) throws ParseException, JsonProcessingException {

        JSONObject obj = (JSONObject) new JSONParser().parse(msg);

        String receiverWalletId = (String) obj.get("receiverId");
        String senderWalletId = (String) obj.get("senderId");

        Long amount = (Long) obj.get("amount");
        String transactionId = (String) obj.get("transactionId");

        Wallet senderWallet = walletRepository.findByWalletId(senderWalletId);
        Wallet receiverWallet = walletRepository.findByWalletId(receiverWalletId);

        if (senderWallet==null || receiverWallet == null || senderWallet.getBalance()  < amount){

            obj = new JSONObject();
            obj.put("transactionId", transactionId);
            obj.put("status","Failed");
            obj.put("senderWalletId", senderWalletId);
            obj.put("receiverWalletId", receiverWallet);
            obj.put("amount", amount);
            obj.put("senderWalletBalance", senderWallet == null ?  0: senderWallet.getBalance());

            kafkaTemplate
                    .send(WALLET_UPDATED_TOPIC,objectMapper
                            .writeValueAsString(obj)
                    );
            return;
        }

       walletRepository.updateWallet(senderWalletId, 0-amount);
       walletRepository.updateWallet(receiverWalletId, amount);

    }

}
