package com.centsanddollars.bankmanagementapp.beneficiarypackage;

import java.io.Serializable;

public class BeneficiaryId  implements Serializable {
    private int userAccount;

    private int beneficiaryAccount;

    public BeneficiaryId() {
    }

    public BeneficiaryId(int userId, int beneficiaryId) {
        this.userAccount = userId;
        this.beneficiaryAccount = beneficiaryId;
    }
}
