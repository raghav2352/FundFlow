package project.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.banking_app.entity.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

}
