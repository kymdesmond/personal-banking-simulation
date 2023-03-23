package com.dibsy.personalbanking.service;

import com.dibsy.personalbanking.entity.AccountEntity;
import com.dibsy.personalbanking.entity.TransactionEntity;
import com.dibsy.personalbanking.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface DataService {

    Optional<AccountEntity> findAccountById(String id);

    TransactionEntity doTransaction(Transaction transaction);

    List<TransactionEntity> getTransactions(String account);
}
