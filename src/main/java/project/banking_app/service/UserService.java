package project.banking_app.service;

import project.banking_app.dto.BankAccountDTO;
import project.banking_app.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long userId);
    UserDTO registerUser(UserDTO userDTO);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    void addBankAccountToUser(Long userId , BankAccountDTO bankAccountDTO);
    List<BankAccountDTO> getBankAccountsForUser(Long userId );
}
