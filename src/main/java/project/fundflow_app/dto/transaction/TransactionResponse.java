package project.fundflow_app.dto.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import project.fundflow_app.entity.TransactionType;

@Data
public class TransactionResponse {
    private Long id;
    private TransactionType transactionType;
    private BigDecimal amount;
    private LocalDateTime timestamp;
}
