package project.banking_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.banking_app.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
