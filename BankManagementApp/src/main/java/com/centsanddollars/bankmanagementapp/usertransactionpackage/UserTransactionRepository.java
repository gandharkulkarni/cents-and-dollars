package com.centsanddollars.bankmanagementapp.usertransactionpackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTransactionRepository extends JpaRepository<UserTransaction,Integer> {
    @Query(value = "SELECT * FROM [user_transaction](nolock) u WHERE u.account_number = ?1", nativeQuery = true)
    List<UserTransaction> getAllUserTransaction(int accountNumber);
}
