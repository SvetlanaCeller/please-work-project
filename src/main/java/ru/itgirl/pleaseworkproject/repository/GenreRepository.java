package ru.itgirl.pleaseworkproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.pleaseworkproject.model.Genre;


public interface GenreRepository extends JpaRepository<Genre, Long> {
}