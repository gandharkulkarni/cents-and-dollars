package com.centsanddollars.bankmanagementapp.userpackage;
import com.centsanddollars.bankmanagementapp.useraccountpackage.UserAccountDataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import java.util.List;
@Service
public class UserDataAccessService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager manager;
    public List<User> listAllUsers(){
        return (List<User>) userRepository.findAll();
    }
    public User validateUser(String emailId, String password){
        User userObj = userRepository.validateUser(emailId,password);
        return userObj;
    }

    public void registerNewUser(User newUser){
        System.out.println("User details saved");
        userRepository.save(newUser);
    }

    public User findById(int userId){
        return userRepository.findById(userId);
    }

    public void updateUserDetails(User oldUserDetails,User updatedUserDetails){
        System.out.println("User details updated");
        User userFromDB = userRepository.findById(oldUserDetails.getUserId());
        userFromDB.setEmailId(updatedUserDetails.getEmailId());
        userFromDB.setUserAddress(updatedUserDetails.getUserAddress());
        userFromDB.setCity(updatedUserDetails.getCity());
        userFromDB.setPostalCode(updatedUserDetails.getPostalCode());
        userRepository.save(userFromDB);
    }

}
