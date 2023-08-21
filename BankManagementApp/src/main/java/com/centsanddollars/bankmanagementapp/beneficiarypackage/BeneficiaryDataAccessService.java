package com.centsanddollars.bankmanagementapp.beneficiarypackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryDataAccessService {
    @Autowired
    private BeneficiaryRepository beneficiaryRepository;

    public void addBeneficiary(Beneficiary beneficiary){
        beneficiaryRepository.save(beneficiary);
    }

    public List<Beneficiary> getBeneficiariesForUser(int userId){
        List<Beneficiary> beneficiaries = beneficiaryRepository.getUserAccountDetailsByAccountNumber(userId);
        return beneficiaries;
    }
}
