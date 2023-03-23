package com.dibsy.personalbanking.repository;

import com.dibsy.personalbanking.entity.AccountEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountEntityRepository extends MongoRepository<AccountEntity, String> {
}
