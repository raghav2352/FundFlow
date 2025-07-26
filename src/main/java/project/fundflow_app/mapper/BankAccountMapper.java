package project.fundflow_app.mapper;

import project.fundflow_app.dto.bank.BankAccountRequest;
import project.fundflow_app.dto.bank.BankAccountResponse;
import project.fundflow_app.entity.BankAccount;
import project.fundflow_app.entity.User;

public class BankAccountMapper {
    public static BankAccountResponse toResponse(BankAccount account){
        BankAccountResponse dto = new BankAccountResponse();
        dto.setAccountId(account.getAccountId());
        dto.setBalance(account.getBalance());
        dto.setAccountType(account.getAccountType());
        dto.setUserName(account.getUser().getName());
        return dto;
    }

    public static BankAccount toEntity(BankAccountRequest request, User user) {
        BankAccount account = new BankAccount();
        account.setAccountType(request.getAccountType());
        account.setBalance(request.getBalance());
        account.setUser(user);
        return account;
    }
}
