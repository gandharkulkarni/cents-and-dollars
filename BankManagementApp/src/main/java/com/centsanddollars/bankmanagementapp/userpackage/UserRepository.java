package com.centsanddollars.bankmanagementapp.userpackage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM [User](nolock) u WHERE u.email_Id = ?1 and u.password = ?2", nativeQuery = true)
    public User validateUser(String emailId, String password);

    @Query(value = "SELECT * FROM [User](nolock) u WHERE u.email_Id = ?1",nativeQuery = true)
    public User findByEmail(String email);

    @Query(value = "SELECT * FROM [User](nolock) u WHERE u.user_id = ?1",nativeQuery = true)
    public User findById(int userId);
}
