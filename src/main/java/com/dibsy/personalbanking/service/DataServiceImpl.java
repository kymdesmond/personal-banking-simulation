package com.dibsy.personalbanking.service;

import com.dibsy.personalbanking.entity.AccountEntity;
import com.dibsy.personalbanking.entity.TransactionEntity;
import com.dibsy.personalbanking.model.Transaction;
import com.dibsy.personalbanking.repository.AccountEntityRepository;
import com.dibsy.personalbanking.repository.TransactionEntityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataServiceImpl implements DataService{

    private final AccountEntityRepository accountEntityRepository;

    private final TransactionEntityRepository transactionEntityRepository;


    @Override
    public Optional<AccountEntity> findAccountById(String id) {
        return accountEntityRepository.findById(id);

    }

    @Override
    public TransactionEntity doTransaction(Transaction transaction) {
        log.info("perform transaction -- {}", transaction);
        return switch (transaction.getTransactionType()) {
            case "DEPOSIT" -> depositToAccount(transaction);
            case "WITHDRAW" -> withdrawFromAccount(transaction);
            default -> throw new IllegalStateException("Unexpected value: " + transaction.getTransactionType());
        };
    }

    public TransactionEntity depositToAccount(Transaction transaction) {
        AccountEntity accountEntity1 = new AccountEntity();
        accountEntity1.setAccountName("dummy");
        accountEntity1.setAccount("12345");
        accountEntity1.setDateCreated(new Date());
        accountEntity1.setBalance(0d);

        accountEntityRepository.save(accountEntity1);

        log.info("deposit to account -- {} -- {}", transaction.getAccountId(), transaction.getAmount());
        Optional<AccountEntity> optionalAccountEntity = findAccountById(accountEntity1.getId());
        if (optionalAccountEntity.isPresent()) {
            log.info("create a new transaction entry");
            AccountEntity accountEntity = optionalAccountEntity.get();
            TransactionEntity transactionEntity = createTransactionEntity(accountEntity, transaction.getAmount(),
                    transaction.getTransactionType());
            accountEntity.setBalance(transactionEntity.getBalance());

            accountEntityRepository.save(accountEntity);
            return transactionEntity;
        }
        log.error("missing account details");
        return null;
    }

    public TransactionEntity withdrawFromAccount(Transaction transaction) {
        log.info("withdraw from account -- {} -- {}", transaction.getAccountId(), transaction.getAmount());
        Optional<AccountEntity> optionalAccountEntity = findAccountById(transaction.getAccountId());
        if (optionalAccountEntity.isPresent()) {
            log.info("create a new transaction entry");
            AccountEntity accountEntity = optionalAccountEntity.get();
            TransactionEntity transactionEntity = createTransactionEntity(accountEntity, transaction.getAmount(),
                    transaction.getTransactionType());
            accountEntity.setBalance(transactionEntity.getBalance());

            accountEntityRepository.save(accountEntity);
            return transactionEntity;
        }
        log.error("missing account details");
        return null;
    }

    @Override
    public List<TransactionEntity> getTransactions(String account) {
        log.info("list transactions by account -- {}", account);
        return transactionEntityRepository.getTransactionEntitiesByAccount(account);
    }

    private TransactionEntity createTransactionEntity(AccountEntity accountEntity, Double amount, String type) {
         TransactionEntity transactionEntity = new TransactionEntity();
         transactionEntity.setAccount(accountEntity.getAccount());
         transactionEntity.setDescription(type + "_" + accountEntity.getAccount() + "_" + amount);
         transactionEntity.setAmount(amount);
         transactionEntity.setDate(new Date());
         Double balance = switch (type) {
             case "DEPOSIT" -> accountEntity.getBalance() + amount;
             case "WITHDRAWAL" -> accountEntity.getBalance() - amount;
             default -> throw new IllegalStateException("Unexpected value: " + type);
         };
        transactionEntity.setBalance(balance);
        transactionEntityRepository.save(transactionEntity);

        return transactionEntity;
    }
}
