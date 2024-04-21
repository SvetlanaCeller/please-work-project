package ru.itgirl.pleaseworkproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.pleaseworkproject.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
