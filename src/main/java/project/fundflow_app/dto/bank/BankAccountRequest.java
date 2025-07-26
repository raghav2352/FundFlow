package project.fundflow_app.dto.bank;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BankAccountRequest {
    private String accountType;
    private BigDecimal balance;
    private Long userId;
}
