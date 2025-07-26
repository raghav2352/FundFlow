package project.fundflow_app.dto.transaction;

import java.math.BigDecimal;
import lombok.Data;
import project.fundflow_app.entity.TransactionType;

@Data
public class TransactionRequest {
    private Long accountId;
    private BigDecimal amount;
    private TransactionType transactionType;
}
