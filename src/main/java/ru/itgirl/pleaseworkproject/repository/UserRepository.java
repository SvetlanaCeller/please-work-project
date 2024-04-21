package ru.itgirl.pleaseworkproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.pleaseworkproject.model.MyUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findUserByName(String username);
}
