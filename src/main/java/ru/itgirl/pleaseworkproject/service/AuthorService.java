package ru.itgirl.pleaseworkproject.service;

import ru.itgirl.pleaseworkproject.dto.AuthorCreateDto;
import ru.itgirl.pleaseworkproject.dto.AuthorDto;
import ru.itgirl.pleaseworkproject.dto.AuthorDtoWithoutBooks;
import ru.itgirl.pleaseworkproject.dto.AuthorUpdateDto;

import java.util.List;

public interface AuthorService {
    AuthorDto getAuthorById(Long id);

    AuthorDto getAuthorByNameV1(String name);

    AuthorDto getAuthorByNameV2(String name);

    AuthorDto getAuthorByNameV3(String name);

    AuthorDto createAuthor(AuthorCreateDto authorCreateDto);

    AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto);

    public void deleteAuthor(Long id);

    public List<AuthorDtoWithoutBooks> getAllAuthors();
}
