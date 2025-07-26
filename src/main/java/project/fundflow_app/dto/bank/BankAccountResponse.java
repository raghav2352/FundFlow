package project.fundflow_app.dto.bank;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class BankAccountResponse {
    private Long accountId;
    private String accountType;
    private BigDecimal balance;
    private String userName;
}
