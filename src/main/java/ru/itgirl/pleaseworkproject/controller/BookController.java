package ru.itgirl.pleaseworkproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.pleaseworkproject.dto.BookCreateDto;
import ru.itgirl.pleaseworkproject.dto.BookDto;
import ru.itgirl.pleaseworkproject.dto.BookDtoWithoutGenre;
import ru.itgirl.pleaseworkproject.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    BookDto getBookByName(@RequestParam("name") String name) {
        return bookService.getByNameV1(name);
    }

    @GetMapping("/book/v2")
    BookDto getBookByNameV2(@RequestParam("name") String name) {
        return bookService.getByNameV2(name);
    }

    @GetMapping("/book/v3")
    BookDto getBookByNameV3(@RequestParam("name") String name) {
        return bookService.getByNameV3(name);
    }

    @PostMapping("/book/create")
    BookDto createBook(@RequestBody BookCreateDto bookCreateDto){
        return bookService.createBook(bookCreateDto);
    }
}