package com.centsanddollars.bankmanagementapp.usertransactionpackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTransactionDataAccessService {
    @Autowired
    private UserTransactionRepository userTransactionRepository;

    public void depositMoney(int accountNumber,double amount){
        UserTransaction transactionObj = new UserTransaction();
        transactionObj.setTransactionAmount(amount);
        transactionObj.setTransactionDescription("Cash deposit");
        transactionObj.setTransactionType("Deposit");
        transactionObj.setAccountNumber(accountNumber);
        userTransactionRepository.save(transactionObj);
    }

    public void transferMoney(int userAccountNumber, int beneficiaryAccountNumber, double amount){
        UserTransaction transactionObj = new UserTransaction();
        transactionObj.setTransactionAmount(amount);
        transactionObj.setTransactionDescription("Money transfer");
        transactionObj.setTransactionType("Money transfer");
        transactionObj.setAccountNumber(userAccountNumber);
        userTransactionRepository.save(transactionObj);

        transactionObj = new UserTransaction();
        transactionObj.setTransactionAmount(amount);
        transactionObj.setTransactionDescription("Money credit");
        transactionObj.setTransactionType("Money credit");
        transactionObj.setAccountNumber(beneficiaryAccountNumber);
        userTransactionRepository.save(transactionObj);
    }

    public List<UserTransaction> getAllUserTransaction(int accountNumber){
        List<UserTransaction> userTransaction = userTransactionRepository.getAllUserTransaction(accountNumber);
        return userTransaction;
    }
    public void withdrawMoney(){

    }
}
