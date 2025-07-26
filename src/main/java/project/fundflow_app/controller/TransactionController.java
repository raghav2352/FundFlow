package project.fundflow_app.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import project.fundflow_app.dto.transaction.TransactionRequest;
import project.fundflow_app.dto.transaction.TransactionResponse;
import project.fundflow_app.dto.transaction.TransferRequest;
import project.fundflow_app.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PreAuthorize("@authUtil.isAccountOwner(#request.accountId) or hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@RequestBody TransactionRequest request){
        TransactionResponse response = transactionService.createTransaction(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authUtil.isAccountOwner(#accountId) or hasRole('ADMIN')")
    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionsByAccountId(@PathVariable Long accountId){
        List<TransactionResponse> response = transactionService.getTransactionsByAccountId(accountId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("@authUtil.isAccountOwner(#request.fromAccountId) or hasRole('ADMIN')")
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransferRequest request){
            transactionService.transferFunds(request);
            return ResponseEntity.ok("Funds transferred successfully.");
    } 
    
}
