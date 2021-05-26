package com.bank.dao;

import org.springframework.data.repository.CrudRepository;

import com.bank.domain.SavingsAccount;


public interface SavingsAccountDao extends CrudRepository<SavingsAccount, Long> {

    SavingsAccount findByAccountNumber (int accountNumber);
}
