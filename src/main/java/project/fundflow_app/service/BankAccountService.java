package project.fundflow_app.service;

import java.math.BigDecimal;
import java.util.List;

import project.fundflow_app.dto.bank.BalanceResponse;
import project.fundflow_app.dto.bank.BankAccountRequest;
import project.fundflow_app.dto.bank.BankAccountResponse;

public interface BankAccountService {

     //Methods for account management

     BankAccountResponse createBankAccount(BankAccountRequest request);

     BankAccountResponse getAccountById(Long id); //for a specific account

     List<BankAccountResponse> getAccountsByUserId(Long userId); // for  all accounts for a user

     List<BankAccountResponse> getAllBankAccounts();

     BankAccountResponse updateAccount(Long id , BankAccountRequest request);

     void deleteAccount(Long userId);

     BalanceResponse getAccountBalance(Long accountId);

     
     




}
