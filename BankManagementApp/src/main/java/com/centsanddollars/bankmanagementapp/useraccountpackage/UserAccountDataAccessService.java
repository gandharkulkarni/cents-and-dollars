package com.centsanddollars.bankmanagementapp.useraccountpackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountDataAccessService{
    @Autowired
    private UserAccountRepository userAccountRepository;

    public void createUserAccount(UserAccount userAccount){
        System.out.println("User account created");
        userAccountRepository.save(userAccount);
    }
    public List<Integer> getAllAccountNumbers(){
        System.out.println("Getting all user accounts");
        return userAccountRepository.getAllAccountNumbers();
    }
    public void updateBalance(int accountNumber, double amount){
        UserAccount userAccount = userAccountRepository.getOne(accountNumber);
        userAccount.setAccountBalance(userAccount.getAccountBalance()+amount);
        userAccountRepository.save(userAccount);
    }

    public UserAccount getUserAccountDetailsByAccountNumber(int accountNumber){ return userAccountRepository.getUserAccountDetailsByAccountNumber(accountNumber);}

    public UserAccount getUserAccountDetails(int userId){
        return userAccountRepository.getUserAccountDetails(userId);
    }


}
