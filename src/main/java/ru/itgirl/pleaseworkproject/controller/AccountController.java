package ru.itgirl.pleaseworkproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.pleaseworkproject.dto.AccountCreateDto;
import ru.itgirl.pleaseworkproject.dto.AccountDto;
import ru.itgirl.pleaseworkproject.service.AccountService;

@RestController
@RequiredArgsConstructor
@Controller
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/account/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    AccountDto createAccount(@RequestBody AccountCreateDto accountCreateDto) {
        return accountService.createUser(accountCreateDto);
    }

    @DeleteMapping("account/delete/{id}")
    void deleteUser(@PathVariable("id") Long id) {
        accountService.deleteUser(id);
    }
}
