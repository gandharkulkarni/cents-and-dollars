package com.centsanddollars.bankmanagementapp.useraccountpackage;

import javax.persistence.*;

@Entity
@Table(name="[user_account]")
public class UserAccount {
    @Id @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "account_number")
    private int accountNumber;

    @Column(name = "balance")
    private double accountBalance;

    @Override
    public String toString() {
        return "UserAccount{" +
                "accountNumber=" + accountNumber +
                ", accountBalance=" + accountBalance +
                ", userId=" + userId +
                '}';
    }

    @Column(name = "user_id")
    private int userId;

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
