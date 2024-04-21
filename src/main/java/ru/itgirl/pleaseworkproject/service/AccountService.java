package ru.itgirl.pleaseworkproject.service;

import ru.itgirl.pleaseworkproject.dto.AccountCreateDto;
import ru.itgirl.pleaseworkproject.dto.AccountDto;

public interface AccountService {
    AccountDto createUser(AccountCreateDto accountCreateDto);

    public void deleteUser(Long id);


}
