package project.fundflow_app.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER_DEBIT,
    TRANSFER_CREDIT
}
