package ru.itgirl.pleaseworkproject.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.pleaseworkproject.dto.BookCreateDto;
import ru.itgirl.pleaseworkproject.dto.BookDto;
import ru.itgirl.pleaseworkproject.service.BookService;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library-users")
public class BookController {

    private final BookService bookService;

    @GetMapping("/hello")
    public String hello() {
        return "Welcome, if you want to have full access, please, authorize";
    }

    @GetMapping("/book")
    BookDto getByNameV1(@RequestParam("name") String name) {
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
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    BookDto createBook(@RequestBody BookCreateDto bookCreateDto) {
        return bookService.createBook(bookCreateDto);
    }

    @PutMapping("/book/update")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    BookDto updateBook(@RequestBody @Valid BookDto bookDto) {
        return bookService.updateBook(bookDto);
    }

    @DeleteMapping("book/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }

    @GetMapping("/books")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    Model getBooksView(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return model;
    }

}
