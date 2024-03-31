package ru.itgirl.pleaseworkproject.service;

import ru.itgirl.pleaseworkproject.dto.BookDto;

public interface BookService {
    BookDto getByNameV1(String name);
    BookDto getByNameV2(String name);

    public BookDto getByNameV3(String name);
}