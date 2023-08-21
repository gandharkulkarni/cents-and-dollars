package com.centsanddollars.bankmanagementapp.useraccountpackage;

import com.centsanddollars.bankmanagementapp.useraccountpackage.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface UserAccountRepository extends JpaRepository<UserAccount,Integer> {
    @Query(value = "SELECT account.account_number FROM [user_account](nolock) account",nativeQuery = true)
    public List<Integer> getAllAccountNumbers();

    @Query(value = "SELECT * FROM [user_account](nolock) account where account.user_id=?1",nativeQuery = true)
    public UserAccount getUserAccountDetails(int userId);

    @Query(value = "SELECT * FROM [user_account](nolock) account where account.account_number=?1",nativeQuery = true)
    public UserAccount getUserAccountDetailsByAccountNumber(int accountNumber);
}
