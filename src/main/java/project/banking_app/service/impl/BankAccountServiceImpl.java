package project.banking_app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.banking_app.dto.BankAccountDTO;
import project.banking_app.entity.BankAccount;
import project.banking_app.entity.User;
import project.banking_app.mapper.AccountMapper;
import project.banking_app.repository.BankAccountRepository;
import project.banking_app.repository.UserRepository;
import project.banking_app.service.BankAccountService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public BankAccountDTO createBankAccount(Long userId, BankAccountDTO bankAccountDTO) {
        if (bankAccountDTO.getAccountType() == null) {
            throw new IllegalArgumentException("Account type must not be null");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        BankAccount bankAccount = AccountMapper.mapToAccount(bankAccountDTO);
        bankAccount.setUser(user);
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        user.getBankAccounts().add(savedAccount);
        userRepository.save(user);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public BankAccountDTO getBankAccountById(Long accountId) {
        BankAccount account = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        return  AccountMapper.mapToAccountDto(account);
    }

    @Override
    public List<BankAccountDTO> getAllBankAccounts() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        return accounts.stream().map(bankAccount -> AccountMapper.mapToAccountDto(bankAccount))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteBankAccount(Long accountId) {
        BankAccount account = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        bankAccountRepository.deleteById(accountId);
    }

    @Override
    public BankAccountDTO updateBankAccount(Long accountId, BankAccountDTO bankAccountDTO) {
        Optional<BankAccount> existingBankAccount  = bankAccountRepository.findById(accountId);
        if(existingBankAccount.isPresent()){
            BankAccount account = existingBankAccount.get();
            account.setBalance(bankAccountDTO.getBalance());
            account.setAccountType(bankAccountDTO.getAccountType());
            account = bankAccountRepository.save(account);
            return AccountMapper.mapToAccountDto(account);
        }else {
            throw new RuntimeException("Bank account not found");
        }
    }

    @Override
    public BankAccountDTO deposit(Long accountId, double amount) {
        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        double newBalance = bankAccount.getBalance() + amount;
        bankAccount.setBalance(newBalance);
        BankAccount savedAccount = bankAccountRepository.save(bankAccount);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public BankAccountDTO withdraw(Long accountId, double amount) {
        BankAccount bankAccount = bankAccountRepository
                .findById(accountId)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));
        if(bankAccount.getBalance() < amount){
            throw new RuntimeException("Insufficient balance");
        }
        double newBalance = bankAccount.getBalance() - amount;
        bankAccount.setBalance(newBalance);
        BankAccount updatedAccount = bankAccountRepository.save(bankAccount);
        return AccountMapper.mapToAccountDto(updatedAccount);
    }

    @Override
    public double checkBalance(Long accountId) {
        Optional<BankAccount> bankAccount = bankAccountRepository.findById(accountId);
        return bankAccount.map(BankAccount::getBalance).orElseThrow(() -> new RuntimeException("Bank account not found"));
    }
}
