package ru.itgirl.pleaseworkproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.pleaseworkproject.dto.UserCreateDto;
import ru.itgirl.pleaseworkproject.dto.UserDto;
import ru.itgirl.pleaseworkproject.service.UserService;

@RestController
@RequiredArgsConstructor
@Controller
public class MyUserController {
    private final UserService userService;

    @PostMapping("/user/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    UserDto createUser(@RequestBody UserCreateDto userCreateDto) {
        return userService.createUser(userCreateDto);
    }

    @DeleteMapping("user/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }
}


