package com.dibsy.personalbanking.repository;

import com.dibsy.personalbanking.entity.TransactionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionEntityRepository extends MongoRepository<TransactionEntity, String> {

    List<TransactionEntity> getTransactionEntitiesByAccount(String account);
}
