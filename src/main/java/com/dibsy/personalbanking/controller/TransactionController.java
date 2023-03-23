package com.dibsy.personalbanking.controller;

import com.dibsy.personalbanking.entity.TransactionEntity;
import com.dibsy.personalbanking.model.Transaction;
import com.dibsy.personalbanking.service.DataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class TransactionController {

    private final DataService dataService;

    @PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionEntity> deposit(@RequestBody Transaction transaction) {
        log.info("deposit to account request -- {}", transaction);
        TransactionEntity transactionEntity = dataService.doTransaction(transaction);

        if (transactionEntity != null) {
            return  ResponseEntity.ok(transactionEntity);
        }

        return ResponseEntity.badRequest().build();
    }
}
