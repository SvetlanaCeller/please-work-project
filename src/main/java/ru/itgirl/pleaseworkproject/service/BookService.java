package ru.itgirl.pleaseworkproject.service;

import ru.itgirl.pleaseworkproject.dto.BookCreateDto;
import ru.itgirl.pleaseworkproject.dto.BookDto;

import java.util.List;

public interface BookService {
    BookDto getByNameV1(String name);

    BookDto getByNameV2(String name);

    public BookDto getByNameV3(String name);

    BookDto createBook(BookCreateDto bookCreateDto);

    BookDto updateBook(BookDto bookDto);

    public void deleteBook(Long id);

    public List<BookDto> getAllBooks();
}