package com.dibsy.personalbanking.entity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountEntityTest {

    private static Validator validator;

    @BeforeAll
    public static  void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void testAccountNameIsEmpty() {
        AccountEntity accountEntity = new AccountEntity(null, "11223344", 0d, new Date());
        Set<ConstraintViolation<AccountEntity>> constraintViolations = validator.validate(accountEntity);
        assertEquals( 1, constraintViolations.size());
        assertEquals(
                "must not be null",
                constraintViolations.iterator().next().getMessage()
        );
    }

    @Test
    public void testAccountTooShort() {
        AccountEntity accountEntity = new AccountEntity("Account Name", "112233", 0d, new Date());
        Set<ConstraintViolation<AccountEntity>> constraintViolations = validator.validate(accountEntity);
        assertEquals( 1, constraintViolations.size());
        assertEquals("size must be between 8 and 8", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testAccountTooLong() {
        AccountEntity accountEntity = new AccountEntity("Account Name", "1122334455", 0d, new Date());
        Set<ConstraintViolation<AccountEntity>> constraintViolations = validator.validate(accountEntity);
        assertEquals( 1, constraintViolations.size());
        assertEquals("size must be between 8 and 8", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testMinimumBalanceIsZero() {
        AccountEntity accountEntity = new AccountEntity("Account Name", "11223344", -1d, new Date());
        Set<ConstraintViolation<AccountEntity>> constraintViolations = validator.validate(accountEntity);
        assertEquals( 1, constraintViolations.size());
        assertEquals("must be greater than or equal to 0", constraintViolations.iterator().next().getMessage());
    }

    @Test
    public void testAccountObjectIsValid() {
        AccountEntity accountEntity = new AccountEntity("Account Name", "11223344", 0d, new Date());
        Set<ConstraintViolation<AccountEntity>> constraintViolations = validator.validate(accountEntity);
        assertEquals( 0, constraintViolations.size());
    }
}
