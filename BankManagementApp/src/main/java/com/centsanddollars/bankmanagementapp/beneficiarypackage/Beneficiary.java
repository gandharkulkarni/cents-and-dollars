package com.centsanddollars.bankmanagementapp.beneficiarypackage;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="[user_beneficiary]")
@IdClass(BeneficiaryId.class)
public class Beneficiary {
    @Column(name = "user_account") @Id
    private int userAccount;
    @Column(name = "beneficiary_account") @Id
    private int beneficiaryAccount;

    @Override
    public String toString() {
        return "Beneficiary{" +
                "userId=" + userAccount +
                ", beneficiaryId=" + beneficiaryAccount +
                '}';
    }

    public Beneficiary() {
    }

    public Beneficiary(int userId, int beneficiaryId) {
        this.userAccount = userId;
        this.beneficiaryAccount = beneficiaryId;
    }

    public int getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(int userId) {
        this.userAccount = userId;
    }

    public int getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(int beneficiaryId) {
        this.beneficiaryAccount = beneficiaryId;
    }
}
