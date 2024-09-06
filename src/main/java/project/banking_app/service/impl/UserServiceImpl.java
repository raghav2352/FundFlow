package project.banking_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.banking_app.dto.BankAccountDTO;
import project.banking_app.dto.UserDTO;
import project.banking_app.entity.BankAccount;
import project.banking_app.entity.User;
import project.banking_app.mapper.AccountMapper;
import project.banking_app.mapper.UserMapper;
import project.banking_app.repository.BankAccountRepository;
import project.banking_app.repository.UserRepository;
import project.banking_app.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  BankAccountRepository bankAccountRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> UserMapper.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        return  userRepository.findById(userId)
                .map(user -> UserMapper.mapToUserDto(user))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User user = UserMapper.mapToUser(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return UserMapper.mapToUserDto(savedUser);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setName(userDTO.getName());
                    existingUser.setEmail(userDTO.getEmail());
                    return userRepository.save(existingUser);
                })
                .map(existingUser -> UserMapper.mapToUserDto(existingUser))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void addBankAccountToUser(Long userId, BankAccountDTO bankAccountDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Convert DTO to entity
        BankAccount bankAccount = AccountMapper.mapToAccount(bankAccountDTO);
        bankAccount.setUser(user);

        // Save the bank account and add to user
        BankAccount savedAccount = bankAccountRepository.save(bankAccount) ;
        user.getBankAccounts().add(savedAccount);
        userRepository.save(user);
    }

    @Override
    public List<BankAccountDTO> getBankAccountsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getBankAccounts().stream()
                .map(BankAccount -> AccountMapper.mapToAccountDto(BankAccount))
                .collect(Collectors.toList());
    }
}
