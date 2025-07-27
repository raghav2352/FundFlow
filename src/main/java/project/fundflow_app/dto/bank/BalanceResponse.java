package project.fundflow_app.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse {
    private Long accountId;
    private String userName;
    private BigDecimal balance;
}
