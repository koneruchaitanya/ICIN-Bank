package com.bank.service;

import java.security.Principal;

import com.bank.domain.PrimaryAccount;
import com.bank.domain.PrimaryTransaction;
import com.bank.domain.SavingsAccount;
import com.bank.domain.SavingsTransaction;

public interface AccountService {
	PrimaryAccount createPrimaryAccount();
    SavingsAccount createSavingsAccount();
    void deposit(String accountType, double amount, Principal principal);
    void withdraw(String accountType, double amount, Principal principal);
    
    
}
