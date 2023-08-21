package com.centsanddollars.bankmanagementapp.usertransactionpackage;

import javax.persistence.*;

@Entity
@Table(name = "[user_transaction]")
public class UserTransaction {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    public int transactionId;

    //Deposit or Withdraw
    @Column(name = "transaction_type")
    public String transactionType;

    @Column(name = "transaction_description")
    public String transactionDescription;

    @Column(name = "transaction_amount")
    public double transactionAmount;

    @Column(name = "account_number")
    public int accountNumber;

    @Override
    public String toString() {
        return "UserTransaction{" +
                "transactionId=" + transactionId +
                ", transactionType='" + transactionType + '\'' +
                ", transactionDescription='" + transactionDescription + '\'' +
                ", transactionAmount=" + transactionAmount +
                ", accountNumber=" + accountNumber +
                '}';
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
