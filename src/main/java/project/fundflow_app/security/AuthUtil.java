package project.fundflow_app.security;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import project.fundflow_app.entity.BankAccount;
import project.fundflow_app.entity.User;
import project.fundflow_app.repository.BankAccountRepository;
import project.fundflow_app.repository.UserRepository;
import project.fundflow_app.service.BankAccountService;
import project.fundflow_app.service.UserService;

@Component
public class AuthUtil {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserService userService;

    public AuthUtil(UserRepository userRepository, BankAccountService bankAccountService, BankAccountRepository bankAccountRepository, @Lazy UserService userService) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.userService = userService;
    }

    public boolean isAccountOwner(Long accountId){
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(currentUserEmail);
        BankAccount account = bankAccountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        return account.getUser().getId().equals(user.getId());
    }

    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: "+email));
    }
}
