package project.fundflow_app.service;

import java.util.List;

import project.fundflow_app.dto.transaction.TransactionRequest;
import project.fundflow_app.dto.transaction.TransactionResponse;
import project.fundflow_app.dto.transaction.TransferRequest;

public interface TransactionService {
    TransactionResponse createTransaction(TransactionRequest request);
    
    List<TransactionResponse> getTransactionsByAccountId(Long accountId);

    void transferFunds(TransferRequest request);
}
