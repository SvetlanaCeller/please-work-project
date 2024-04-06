package ru.itgirl.pleaseworkproject.service;

import ru.itgirl.pleaseworkproject.dto.AuthorCreateDto;
import ru.itgirl.pleaseworkproject.dto.AuthorDto;
import ru.itgirl.pleaseworkproject.dto.AuthorUpdateDto;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getByNameV1(String name);

    AuthorDto getByNameV2(String name);

    AuthorDto getByNameV3(String name);

    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);

    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);

    public void deleteAuthor(Long id);
}
