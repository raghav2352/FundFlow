package project.fundflow_app.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.fundflow_app.dto.bank.BankAccountRequest;
import project.fundflow_app.dto.bank.BankAccountResponse;
import project.fundflow_app.entity.BankAccount;
import project.fundflow_app.entity.User;
import project.fundflow_app.mapper.BankAccountMapper;
import project.fundflow_app.repository.BankAccountRepository;
import project.fundflow_app.repository.TransactionRepository;
import project.fundflow_app.repository.UserRepository;
import project.fundflow_app.service.BankAccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public BankAccountResponse createBankAccount(BankAccountRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
                
        BankAccount account = BankAccount.builder()
                .balance(request.getBalance())
                .accountType(request.getAccountType())
                .user(user)
                .build();

        bankAccountRepository.save(account);

        return BankAccountMapper.toResponse(account);
    }

    @Override
    public BankAccountResponse getAccountById(Long id) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        return BankAccountMapper.toResponse(account);
    }

    @Override
    public List<BankAccountResponse> getAccountsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        List<BankAccount> accounts = bankAccountRepository.findByUser(user);

        return accounts.stream()
                .map(BankAccountMapper :: toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BankAccountResponse> getAllBankAccounts() {
        return bankAccountRepository.findAll()
                .stream()
                .map(BankAccountMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BankAccountResponse updateAccount(Long id, BankAccountRequest request) {
        BankAccount account = bankAccountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bank account not found"));

        if (request.getBalance() != null) {
            account.setBalance(request.getBalance());
        }

        if (request.getAccountType() != null) {
            account.setAccountType(request.getAccountType());
        }

        if(request.getUserId() != null && !account.getUser().getId().equals(request.getUserId())){
            User newUser = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            account.setUser(newUser);
        }

        BankAccount updated = bankAccountRepository.save(account);

        return BankAccountMapper.toResponse(updated);
    }

    @Override
    @Transactional
    public void deleteAccount(Long accountId) {
        transactionRepository.deleteByBankAccount_AccountId(accountId);
        bankAccountRepository.deleteById(accountId);
    }
    
}


    