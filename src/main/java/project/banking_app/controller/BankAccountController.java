package project.banking_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.banking_app.dto.BankAccountDTO;
import project.banking_app.service.BankAccountService;

import java.util.List;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @GetMapping
    public ResponseEntity<List<BankAccountDTO>> getAllBankAccounts() {
        List<BankAccountDTO> bankAccounts = bankAccountService.getAllBankAccounts();
        return ResponseEntity.ok(bankAccounts);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccountDTO> getBankAccountById(@PathVariable Long accountId) {
        BankAccountDTO bankAccount = bankAccountService.getBankAccountById(accountId);
        return bankAccount != null ? ResponseEntity.ok(bankAccount) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<BankAccountDTO> createBankAccount(@RequestParam Long userId, @RequestBody BankAccountDTO bankAccountDTO) {
        System.out.println("Received request to create bank account for userId: " + userId);

        try {
            BankAccountDTO createdBankAccount = bankAccountService.createBankAccount(userId, bankAccountDTO);
            System.out.println("Creating bank account for userId: " + userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount);
        } catch (RuntimeException e) {
            System.out.println("Error creating bank account: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<BankAccountDTO> updateBankAccount(@PathVariable Long accountId, @RequestBody BankAccountDTO bankAccountDTO) {
        try {
            BankAccountDTO updatedBankAccount = bankAccountService.updateBankAccount(accountId, bankAccountDTO);
            return ResponseEntity.ok(updatedBankAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long accountId) {
        bankAccountService.deleteBankAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> checkBalance(@PathVariable Long accountId) {
        try {
            double balance = bankAccountService.checkBalance(accountId);
            return ResponseEntity.ok(balance);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<BankAccountDTO> deposit(@PathVariable Long accountId, @RequestParam double amount) {
        try {
            BankAccountDTO updatedBankAccount = bankAccountService.deposit(accountId, amount);
            return ResponseEntity.ok(updatedBankAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<BankAccountDTO> withdraw(@PathVariable Long accountId, @RequestParam double amount) {
        try {
            BankAccountDTO updatedBankAccount = bankAccountService.withdraw(accountId, amount);
            return ResponseEntity.ok(updatedBankAccount);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
