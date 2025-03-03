package com.example.minor_1.controllers;

import com.example.minor_1.dtos.InitiateTransactionRequest;
import com.example.minor_1.models.SecuredUser;
import com.example.minor_1.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PostMapping("/transaction")
    public String initiateTxn(@RequestBody @Valid InitiateTransactionRequest transactionRequest) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user =(SecuredUser) authentication.getPrincipal();
        Integer adminId = user.getAdmin().getId();

        return transactionService.initiateTxn(transactionRequest, adminId);
    }

    @PostMapping("/transaction/payment")
    public void makePayment(@RequestParam("amount") Integer amount,
                            @RequestParam("transactionId") String txnId) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser user =(SecuredUser) authentication.getPrincipal();
        Integer studentId = user.getStudent().getId();

        transactionService.payFine(amount, studentId, txnId);

    }
}
