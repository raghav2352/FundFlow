package project.fundflow_app.repository;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import project.fundflow_app.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findByBankAccount_AccountId(Long accountId);

    @Modifying
    @Transactional
    void deleteByBankAccount_AccountId(Long accountId);
}
