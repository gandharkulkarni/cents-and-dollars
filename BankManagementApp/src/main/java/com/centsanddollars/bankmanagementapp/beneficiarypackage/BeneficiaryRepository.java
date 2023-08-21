package com.centsanddollars.bankmanagementapp.beneficiarypackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Integer> {

    @Query(value = "SELECT * FROM [user_beneficiary](nolock) where user_account=?1",nativeQuery = true)
    public List<Beneficiary> getUserAccountDetailsByAccountNumber(int userAccount);
}
