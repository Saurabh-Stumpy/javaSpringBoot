package com.example.service;

import org.apache.kafka.common.protocol.types.Field;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private static final String TRANSACTION_COMPLETED_TOPIC = "transaction_completed";

    private static final String temp = "";

    @Autowired
    SimpleMailMessage simpleMailMessage;

    @Autowired
    JavaMailSender javaMailSender;

    @KafkaListener(topics = {TRANSACTION_COMPLETED_TOPIC}  ,groupId = "jbdl123" )
    public void notify(String msg) throws ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(msg);
        String transactionStatus = (String) obj.get("transactionStatus");
        String transactionId = (String) obj.get("transactionId");
        Long amount = (Long) obj.get("amount");
        String senderEmail = (String) obj.get("senderEmail");
        String receiverEmail = (String) obj.get("receiverEmail");

        String senderMsg = getSenderMessage(transactionStatus,amount,transactionId);
        String receiverMsg = getReceiverMessage(transactionStatus,amount,senderEmail);

        if(senderMsg != null && senderMsg.length() >0){
            simpleMailMessage.setTo("walanjuwani@gmail.com");
            simpleMailMessage.setSubject("E-Wallet Transacction updates");
            simpleMailMessage.setFrom("ewallet.noreply.stumpy@gmail.com");
            simpleMailMessage.setText(senderMsg);
            javaMailSender.send(simpleMailMessage);
        }
        if(receiverMsg != null && receiverMsg.length() >0){
            simpleMailMessage.setTo("walanjuwani@gmail.com");
            simpleMailMessage.setSubject("E-Wallet Transacction updates");
            simpleMailMessage.setFrom("ewallet.noreply.stumpy@gmail.com");
            simpleMailMessage.setText(receiverMsg);
            javaMailSender.send(simpleMailMessage);
        }

    }

    private String getSenderMessage(String transactionStatus,Long amount, String transactionId){
        String msg ="";
        if (transactionStatus.equals("Failed")){
            msg = "Hi!! Your transactionId " + transactionId + " of amount " + amount + " has failed";
        }
        else {
            msg = "Hi!! Your transactionId " + transactionId + " of amount " + amount + " Succeded";
        }
        return msg;
    }

    private String getReceiverMessage(String transactionStatus, Long amount, String senderEmail){
        String msg ="";
        if (transactionStatus.equals("SUCCESSFUL")){
            msg = "Hi!! Your account has been credited with amount " + amount + " for the transaction done by the user " + senderEmail;
        }
        return msg;
    }

}
