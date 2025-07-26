package project.fundflow_app.mapper;

import project.fundflow_app.dto.transaction.TransactionResponse;
import project.fundflow_app.entity.Transaction;

public class TransactionMapper {
    public static TransactionResponse toResponse(Transaction transaction){
        TransactionResponse response = new TransactionResponse();
        response.setId(transaction.getId());
        response.setTransactionType(transaction.getTransactionType());
        response.setAmount(transaction.getAmount());
        response.setTimestamp(transaction.getTimestamp());
        return response;
    }
}
