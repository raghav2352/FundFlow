package project.fundflow_app.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import project.fundflow_app.dto.bank.BankAccountRequest;
import project.fundflow_app.dto.bank.BankAccountResponse;
import project.fundflow_app.service.BankAccountService;

import java.util.List;

@RestController
@RequestMapping("/api/bank-accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountService bankAccountService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<BankAccountResponse> createAccount(@RequestBody BankAccountRequest request){
        BankAccountResponse response = bankAccountService.createBankAccount(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authUtil.isAccountOwner(#accountId) or hasRole('ADMIN')")
    @GetMapping("/{accountId}")
    public ResponseEntity<BankAccountResponse> getAccountById(@PathVariable Long accountId){
        BankAccountResponse response = bankAccountService.getAccountById(accountId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BankAccountResponse>> getAccountsByUserId(@PathVariable Long userId){
        List<BankAccountResponse> accounts = bankAccountService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<BankAccountResponse>> getAllAccounts(){
        return ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }

    
    @PreAuthorize("@authUtil.isAccountOwner(#accountId) or hasRole('ADMIN')")
    @PatchMapping("/{accountId}")
    public ResponseEntity<BankAccountResponse> updateAccount(@PathVariable Long accountId ,@RequestBody BankAccountRequest request){
        System.out.println("Received PATCH request for accountId = " + accountId);
        BankAccountResponse response = bankAccountService.updateAccount(accountId, request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authUtil.isAccountOwner(#accountId) or hasRole('ADMIN')")
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long accountId){
        bankAccountService.deleteAccount(accountId);
        return ResponseEntity.ok("Bank account deleted successfully.");
    }

   
}
