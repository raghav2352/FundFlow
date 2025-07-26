package project.fundflow_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import project.fundflow_app.entity.BankAccount;
import project.fundflow_app.entity.User;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    List<BankAccount> findByUser(User user);

}
