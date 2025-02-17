package com.example.minor_1.controllers;

import com.example.minor_1.dtos.InitiateTransactionRequest;
import com.example.minor_1.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public String initiateTxn(@RequestBody @Valid InitiateTransactionRequest transactionRequest){
        return transactionService.initiateTxn(transactionRequest);
    }

    @PostMapping("/transaction/payment")
    public void makePayment(@RequestParam("amount") Double amount,
                            @RequestParam("studentId") Integer studentId,
                            @RequestParam("transactionId") String txnId){

    }
}
