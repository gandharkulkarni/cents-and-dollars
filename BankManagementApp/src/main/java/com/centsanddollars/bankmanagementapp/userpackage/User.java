package com.centsanddollars.bankmanagementapp.userpackage;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="[user]")
public class User implements Serializable {
    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", city='" + city + '\'' +
                ", postalCode=" + postalCode +
                ", roleId=" + roleId +
                ", emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="user_address")
    private String userAddress;
    @Column(name = "city")
    private String city;
    @Column(name="postal_code")
    private int postalCode;
    @Column(name = "role_id")
    private int roleId;
    @Column(name = "email_id")
    private String emailId;
    @Column(name = "password")
    private String password;

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public String getCity() {
        return city;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}


