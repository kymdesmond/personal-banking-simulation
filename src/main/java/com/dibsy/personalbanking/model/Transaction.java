package com.dibsy.personalbanking.model;

import lombok.Data;

@Data
public class Transaction {

    private String transactionType;
    private String accountId;
    private Double amount;
}
