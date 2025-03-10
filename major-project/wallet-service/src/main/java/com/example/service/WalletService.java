package com.example.service;

import com.example.models.Wallet;
import com.example.repository.WalletRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private static final String USER_CREATION_TOPIC = "user_created";

    @Autowired
    WalletRepository walletRepository;

    @Value("${wallet,initial.balance}")
    long balance;

    @KafkaListener(topics = {USER_CREATION_TOPIC}, groupId = "jbdl123")
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
}
