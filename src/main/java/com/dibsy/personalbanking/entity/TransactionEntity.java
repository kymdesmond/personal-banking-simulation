package com.dibsy.personalbanking.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class TransactionEntity {

    @Id
    private String id;
    private Double amount;

    private String account;

    private Double balance;

    private String description;

    private Date date;
}
