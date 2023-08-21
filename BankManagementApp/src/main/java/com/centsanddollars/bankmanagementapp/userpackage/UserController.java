package com.centsanddollars.bankmanagementapp.userpackage;

import com.centsanddollars.bankmanagementapp.beneficiarypackage.Beneficiary;
import com.centsanddollars.bankmanagementapp.beneficiarypackage.BeneficiaryDataAccessService;
import com.centsanddollars.bankmanagementapp.useraccountpackage.UserAccount;
import com.centsanddollars.bankmanagementapp.useraccountpackage.UserAccountDataAccessService;
import com.centsanddollars.bankmanagementapp.usertransactionpackage.UserTransaction;
import com.centsanddollars.bankmanagementapp.usertransactionpackage.UserTransactionDataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserDataAccessService userDataAccessService;

    @Autowired
    private UserAccountDataAccessService userAccountDataAccessService;

    @Autowired
    private UserTransactionDataAccessService userTransactionDataAccessService;

    @Autowired
    private BeneficiaryDataAccessService beneficiaryDataAccessService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private UserAccount userAccountDetails;

    private UserAuthenticationDetails userAuthenticationDetails;


    @GetMapping("/")
    public String loadLoginForm(Model model) {
        return "redirect:/login";
    }

    @GetMapping("/loadHomePage")
    public String loadHomePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginView";
        } else {
            this.userAuthenticationDetails = (UserAuthenticationDetails) authentication.getPrincipal();
            if (userAuthenticationDetails.getUser().getRoleId() == 1) {
                model.addAttribute("loggedInUser", userAuthenticationDetails.getUser());
                return "/employeeHome";
            } else if (userAuthenticationDetails.getUser().getRoleId() == 2) {
                model.addAttribute("loggedInUser", userAuthenticationDetails.getUser());
                this.userAccountDetails = getUserAccountDetails(userAuthenticationDetails.getUser().getUserId());
                model.addAttribute("accountNumber",userAccountDetails.getAccountNumber());
                model.addAttribute("accountBalance",userAccountDetails.getAccountBalance());
                return "/customerHome";
            } else {
                return "loginView";
            }
        }
    }
    private UserAccount getUserAccountDetails(int userId){
        return userAccountDataAccessService.getUserAccountDetails(userId);
    }
    @GetMapping("/home")
    public String authenticateUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginView";
        } else {
            this.userAuthenticationDetails = (UserAuthenticationDetails)authentication.getPrincipal();
            if(this.userAuthenticationDetails.getUser().getRoleId()==1) {
                model.addAttribute("loggedInUser",this.userAuthenticationDetails.getUser());
                return "/employeeHome";
            }
            else if(this.userAuthenticationDetails.getUser().getRoleId()==2){
                model.addAttribute("loggedInUser",this.userAuthenticationDetails.getUser());
                this.userAccountDetails = getUserAccountDetails(this.userAuthenticationDetails.getUser().getUserId());
                model.addAttribute("accountNumber",userAccountDetails.getAccountNumber());
                model.addAttribute("accountBalance",userAccountDetails.getAccountBalance());
                return "/customerHome";
            }
            else {
                return "loginView";
            }
        }
    }

    @GetMapping("/employee/addNewUser")
    public String loadUserRegistrationForm(Model model) {
        model.addAttribute("registrationSuccess",false);
        return "addUserForm";
    }

    @ModelAttribute("newUserModel")
    public User InitializeUserModel() {
        return new User();
    }

    @PostMapping("/employee/addUser")
    public String addUser(User newUser,Model model) {
        //Save user details
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        newUser.setRoleId(2);
        userDataAccessService.registerNewUser(newUser);
        //Create user account
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(newUser.getUserId());
        userAccount.setAccountBalance(0.0);
        userAccountDataAccessService.createUserAccount(userAccount);
        model.addAttribute("registrationSuccess",true);
        return "addUserForm";
    }
    @GetMapping("/employee/loadCashDepositForm")
    public String loadCashDepositForm(Model model){
        model.addAttribute("invalidAccountNumber",false);
        model.addAttribute("successfulDeposit",false);
        return "depositForm";
    }
    @PostMapping("/employee/depositCash")
    public String depositCash(@RequestParam(value="accountNumber") int accountNumber, @RequestParam(value="amount") double amount,Model model){
        List<Integer>validAccountNumbers= userAccountDataAccessService.getAllAccountNumbers();
        if(validAccountNumbers.contains(accountNumber)){
            model.addAttribute("invalidAccountNumber",false);
            userAccountDataAccessService.updateBalance(accountNumber,amount);
            userTransactionDataAccessService.depositMoney(accountNumber,amount);
            model.addAttribute("successfulDeposit",true);
        }
        else {
            model.addAttribute("successfulDeposit",false);
            model.addAttribute("invalidAccountNumber",true);
        }
        return "depositForm";
    }

    @GetMapping("/customer/getTransactions")
    public String getTransactions(Model model){
        if(this.userAccountDetails!=null) {
            int accountNumber = this.userAccountDetails.getAccountNumber();
            List<UserTransaction> userTransactions = userTransactionDataAccessService.getAllUserTransaction(accountNumber);
            model.addAttribute("loggedInUser",this.userAuthenticationDetails.getUser());
            model.addAttribute("userTransactions", userTransactions);
            return "customerTransactions";
        }
        return "loginView";
    }

    @GetMapping("/customer/editProfile")
    public String loadUserProfile(Model model) {
        if (this.userAccountDetails != null) {
            model.addAttribute("loggedInUser", userDataAccessService.findById(this.userAuthenticationDetails.getUser().getUserId()));
            return "editCustomerDetails";
        }
        return "loginView";
    }

    @PostMapping("/customer/updateUserDetails")
    public String updateUserProfile(User loggedInUser, Model model) {
        if (this.userAccountDetails != null) {
            userDataAccessService.updateUserDetails(this.userAuthenticationDetails.getUser(), loggedInUser);
            model.addAttribute("loggedInUser", userDataAccessService.findById(this.userAuthenticationDetails.getUser().getUserId()));
            model.addAttribute("updateSuccess", true);
            return "editCustomerDetails";
        }
        return "loginView";
    }
    @GetMapping("/customer/addBeneficiary")
    public String getBeneficiaryForm(Model model){
        if (this.userAccountDetails != null) {
            model.addAttribute("loggedInUser", userDataAccessService.findById(this.userAuthenticationDetails.getUser().getUserId()));
            model.addAttribute("userAccountNumber", userDataAccessService.findById(this.userAccountDetails.getAccountNumber()));
            return "addBeneficiary";
        }
        return "loginView";
    }
    @PostMapping("/customer/addBeneficiary")
    public String addBeneficiary(@RequestParam(value="beneficiaryAccountNumber") int beneficiaryAccountNumber, Model model){
        model.addAttribute("loggedInUser", userDataAccessService.findById(this.userAuthenticationDetails.getUser().getUserId()));
        if (this.userAccountDetails != null) {
            List<Integer> accountNumbers = userAccountDataAccessService.getAllAccountNumbers();
            if(accountNumbers.contains(beneficiaryAccountNumber)) {
                UserAccount beneficiaryAccount = userAccountDataAccessService.getUserAccountDetailsByAccountNumber(beneficiaryAccountNumber);
                if(this.userAccountDetails.getUserId()!=beneficiaryAccount.getUserId()) {
                    Beneficiary beneficiary = new Beneficiary(this.userAccountDetails.getAccountNumber(), beneficiaryAccount.getAccountNumber());
                    beneficiaryDataAccessService.addBeneficiary(beneficiary);
                    model.addAttribute("beneficiarySuccess", true);
                }
                else{
                    model.addAttribute("invalidAccountNumber", true);
                }
            }
            else {
                model.addAttribute("invalidAccountNumber", true);
            }
            return "addBeneficiary";
        }
        return "loginView";
    }

    @GetMapping("/customer/transferMoney")
    public String getMoneyTransferForm(Model model){
        if (this.userAccountDetails != null) {
            model.addAttribute("loggedInUser", userDataAccessService.findById(this.userAuthenticationDetails.getUser().getUserId()));
            model.addAttribute("accountNumber",userAccountDetails.getAccountNumber());
            model.addAttribute("accountBalance",userAccountDetails.getAccountBalance());
            HashMap<Integer, String> beneficiariesMap = new HashMap<>();
            List<Beneficiary> beneficiaries = beneficiaryDataAccessService.getBeneficiariesForUser(userAccountDetails.getAccountNumber());
            for (Beneficiary beneficiary: beneficiaries) {
                UserAccount beneficiaryAccount= userAccountDataAccessService.getUserAccountDetailsByAccountNumber(beneficiary.getBeneficiaryAccount());
                User beneficiaryUser = userDataAccessService.findById(beneficiaryAccount.getUserId());
                beneficiariesMap.put(beneficiary.getBeneficiaryAccount(),beneficiaryUser.getFirstName()+" "+beneficiaryUser.getLastName()+ " | Account number: "+ beneficiary.getBeneficiaryAccount());
            }
            model.addAttribute("beneficiaries", beneficiariesMap);
            return "transferMoney";
        }
        return "loginView";
    }

    @PostMapping("/customer/transferMoney")
    public String transferMoney(@RequestParam(value = "accountNumber") int beneficiaryAccountNumber, @RequestParam(value = "amount") double amount, Model model) {
        if (this.userAccountDetails != null) {
            model.addAttribute("loggedInUser", userDataAccessService.findById(this.userAuthenticationDetails.getUser().getUserId()));
            model.addAttribute("accountNumber", userAccountDetails.getAccountNumber());
            model.addAttribute("accountBalance", userAccountDetails.getAccountBalance());
            HashMap<Integer, String> beneficiariesMap = new HashMap<>();
            List<Beneficiary> beneficiaries = beneficiaryDataAccessService.getBeneficiariesForUser(userAccountDetails.getAccountNumber());
            for (Beneficiary beneficiary: beneficiaries) {
                UserAccount beneficiaryAccount= userAccountDataAccessService.getUserAccountDetailsByAccountNumber(beneficiary.getBeneficiaryAccount());
                User beneficiaryUser = userDataAccessService.findById(beneficiaryAccount.getUserId());
                beneficiariesMap.put(beneficiary.getBeneficiaryAccount(),beneficiaryUser.getFirstName()+" "+beneficiaryUser.getLastName()+ " | Account number: "+ beneficiary.getBeneficiaryAccount());
            }
            model.addAttribute("beneficiaries", beneficiariesMap);
            if (beneficiaryAccountNumber == 0) {
                model.addAttribute("invalidAccountNumber", true);
            } else {
                if (amount > userAccountDetails.getAccountBalance()) {
                    model.addAttribute("insufficientBalance", true);
                } else {
                    userAccountDataAccessService.updateBalance(userAccountDetails.getAccountNumber(), -amount);
                    userAccountDataAccessService.updateBalance(beneficiaryAccountNumber, amount);
                    userTransactionDataAccessService.transferMoney(userAccountDetails.getAccountNumber(), beneficiaryAccountNumber, amount);
                    model.addAttribute("successfulTransfer", true);
                }
            }
            return "transferMoney";
        }
        return "loginView";
    }
}

