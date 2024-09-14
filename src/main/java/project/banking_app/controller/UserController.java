package project.banking_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.banking_app.dto.BankAccountDTO;
import project.banking_app.dto.UserDTO;
import project.banking_app.entity.User;
import project.banking_app.mapper.UserMapper;
import project.banking_app.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long userId) {
        UserDTO user = userService.getUserById(userId);

        System.out.println("Email: " + user.getEmail());
        System.out.println("Password: " + user.getPassword());
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

        UserDTO createdUser = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser(){
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();


        User user = userService.findByEmail(currentUserEmail);

        UserDTO userDTO = UserMapper.mapToUserDto(user);

        return ResponseEntity.ok(userDTO);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) {
        try {
            UserDTO updatedUser = userService.updateUser(userId, userDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("Successfully deleted user with id " +userId);
    }



    // Fetch the bank accounts for a user
    @GetMapping("/{userId}/bank-accounts")
    public ResponseEntity<List<BankAccountDTO>> getBankAccountsForUser(@PathVariable Long userId) {
        try {
            List<BankAccountDTO> bankAccounts = userService.getBankAccountsForUser(userId);
            return ResponseEntity.ok(bankAccounts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
