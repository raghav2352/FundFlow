package project.banking_app.mapper;

import project.banking_app.dto.BankAccountDTO;
import project.banking_app.entity.BankAccount;

public class AccountMapper {

    public  static BankAccount mapToAccount(BankAccountDTO bankAccountDTO){
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountId(bankAccountDTO.getAccountId());
        bankAccount.setBalance(bankAccountDTO.getBalance());
        bankAccount.setAccountType(bankAccountDTO.getAccountType());
        return bankAccount;
    }

    public static BankAccountDTO mapToAccountDto(BankAccount bankAccount){
        return new BankAccountDTO(bankAccount.getAccountId(), bankAccount.getAccountType(), bankAccount.getBalance());
    }
}
