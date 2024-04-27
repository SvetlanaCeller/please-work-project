package ru.itgirl.pleaseworkproject.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.pleaseworkproject.dto.AuthorCreateDto;
import ru.itgirl.pleaseworkproject.dto.AuthorDto;
import ru.itgirl.pleaseworkproject.dto.AuthorUpdateDto;
import ru.itgirl.pleaseworkproject.service.AuthorService;

@RestController
@RequiredArgsConstructor
@Controller
@SecurityRequirement(name = "library-users")
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author/{id}")
    AuthorDto getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author")
    AuthorDto getAuthorByName(@RequestParam("name") String name) {
        return authorService.getByNameV1(name);
    }

    @GetMapping("/author/v2")
    AuthorDto getAuthorByNameV2(@RequestParam("name") String name) {
        return authorService.getByNameV2(name);
    }

    @GetMapping("/author/v3")
    AuthorDto getAuthorByNameV3(@RequestParam("name") String name) {
        return authorService.getByNameV3(name);
    }

    @PostMapping("/author/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    AuthorDto createAuthor(@RequestBody @Valid AuthorCreateDto authorCreateDto) {
        return authorService.createAuthor(authorCreateDto);
    }

    @PutMapping("/author/update")
    AuthorDto updateAuthor(@RequestBody @Valid AuthorUpdateDto authorUpdateDto) {
        return authorService.updateAuthor(authorUpdateDto);
    }

    @DeleteMapping("author/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void updateAuthor(@PathVariable("id") Long id){
        authorService.deleteAuthor(id);

    }

    @GetMapping("/authors")
    Model getAuthorsView(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return model;
    }


}