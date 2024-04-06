package ru.itgirl.pleaseworkproject.service;

import ru.itgirl.pleaseworkproject.dto.GenreDto;

public interface GenreService {
    GenreDto getGenreById(Long id);
}