package ru.itgirl.pleaseworkproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.itgirl.pleaseworkproject.model.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author> {
    @Query(nativeQuery = true, value = "SELECT * FROM AUTHOR WHERE name = ?")
    Optional<Author> findAuthorByNameBySql(String name);

    Optional<Author> findAuthorByName(String name);
}