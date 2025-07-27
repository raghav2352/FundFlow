package project.fundflow_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.fundflow_app.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    List<Transaction> findByBankAccount_AccountId(Long accountId);
}
