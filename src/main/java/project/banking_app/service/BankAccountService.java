package project.banking_app.service;

import project.banking_app.dto.BankAccountDTO;

import java.util.List;

public interface BankAccountService {

     //Methods for account management

     BankAccountDTO createBankAccount(Long userId , BankAccountDTO bankAccountDTO);

     BankAccountDTO getBankAccountById(Long accountId);

     List<BankAccountDTO> getAllBankAccounts();

     void deleteBankAccount(Long accountId);

     BankAccountDTO updateBankAccount(Long accountId , BankAccountDTO bankAccountDTO);

     //Methods for transactions

     BankAccountDTO deposit(Long accountId, double amount);

     BankAccountDTO withdraw(Long accountId , double amount);

     double checkBalance(Long accountId);






}
