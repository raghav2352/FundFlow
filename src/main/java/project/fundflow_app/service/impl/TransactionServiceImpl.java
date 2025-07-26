package project.fundflow_app.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import project.fundflow_app.dto.transaction.TransactionRequest;
import project.fundflow_app.dto.transaction.TransactionResponse;
import project.fundflow_app.dto.transaction.TransferRequest;
import project.fundflow_app.entity.BankAccount;
import project.fundflow_app.entity.Transaction;
import project.fundflow_app.entity.TransactionType;
import project.fundflow_app.mapper.TransactionMapper;
import project.fundflow_app.repository.BankAccountRepository;
import project.fundflow_app.repository.TransactionRepository;
import project.fundflow_app.service.TransactionService;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final BankAccountRepository bankAccountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionResponse createTransaction(TransactionRequest request) {
        BankAccount account = bankAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new RuntimeException("Bank Account not found"));

        BigDecimal amount = request.getAmount();
        TransactionType type = request.getTransactionType();

        if(type == null){
            throw  new IllegalArgumentException("Transaction type must not be null");
        }

        switch (type){
            case DEPOSIT -> account.setBalance(account.getBalance().add(amount));
            case WITHDRAW -> {
                if(account.getBalance().compareTo(amount) < 0){
                    throw new RuntimeException("Insufficient balance");
                }
                account.setBalance(account.getBalance().subtract(amount));
            }
            default -> throw new RuntimeException("Unsupported transaction type");
        }



        Transaction transaction = new Transaction();
        transaction.setBankAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setTimestamp(LocalDateTime.now());

        Transaction savedTx = transactionRepository.save(transaction);
        bankAccountRepository.save(account);

        TransactionResponse response = new TransactionResponse();
        response.setId(savedTx.getId());
        response.setTransactionType(savedTx.getTransactionType());
        response.setAmount(savedTx.getAmount());
        response.setTimestamp(savedTx.getTimestamp());

        return response;
    }

    @Override
    public List<TransactionResponse> getTransactionsByAccountId(Long accountId) {
        List<Transaction> transactions = transactionRepository.findByBankAccount_AccountId(accountId);
        return transactions.stream()
                .map(TransactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void transferFunds(TransferRequest request) {
        BankAccount fromAccount = bankAccountRepository.findById(request.getFromAccountId())
                .orElseThrow(() -> new RuntimeException("Sender account not found"));

        BankAccount toAccount = bankAccountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver account not found"));

        log.info("From Account Balance: {}", fromAccount.getBalance());
        log.info("Transfer Amount: {}", request.getAmount());

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be positive");
        }

        if(fromAccount.getBalance().compareTo(request.getAmount()) <0){
            throw new RuntimeException("Insufficient balance for transfer");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount())); //DEBIT from sender

        Transaction debit = new Transaction();
        debit.setBankAccount(fromAccount);
        debit.setAmount(request.getAmount().negate());
        debit.setTransactionType(TransactionType.TRANSFER_DEBIT);
        debit.setTimestamp(LocalDateTime.now());

        toAccount.setBalance(toAccount.getBalance().add(request.getAmount())); //CREDIT to receiver

        Transaction credit = new Transaction();
        credit.setBankAccount(toAccount);
        credit.setAmount(request.getAmount());
        credit.setTransactionType(TransactionType.TRANSFER_CREDIT);
        credit.setTimestamp(LocalDateTime.now());

        transactionRepository.save(debit);
        transactionRepository.save(credit);
        bankAccountRepository.save(fromAccount);
        bankAccountRepository.save(toAccount);
    }
    
}
