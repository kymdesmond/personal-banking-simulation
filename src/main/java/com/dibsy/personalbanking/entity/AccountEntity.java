package com.dibsy.personalbanking.entity;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document
public class AccountEntity {

    public AccountEntity() {
    }

    public AccountEntity(String accountName, String account, Double balance, Date dateCreated) {
        this.accountName = accountName;
        this.account = account;
        this.balance = balance;
        this.dateCreated = dateCreated;
    }

    @Id
    private String id;

    @NotNull
    private String accountName;

    @Size(min = 8, max = 8)
    private String account;

    @Min(0)
    private Double balance;

    private Date dateCreated;
}
