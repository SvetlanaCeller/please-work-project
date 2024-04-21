package ru.itgirl.pleaseworkproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itgirl.pleaseworkproject.dto.AccountCreateDto;
import ru.itgirl.pleaseworkproject.dto.AccountDto;
import ru.itgirl.pleaseworkproject.model.Account;
import ru.itgirl.pleaseworkproject.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    @Override
    public AccountDto createUser(AccountCreateDto accountCreateDto) {
        Account account = accountRepository.save(convertDtoToEntity(accountCreateDto));
        return convertEntityToDto(account);
    }

    @Override
    public void deleteUser(Long id) {
        accountRepository.deleteById(id);
    }

    private Account convertDtoToEntity(AccountCreateDto accountCreateDto) {
        return Account.builder()
                .login(accountCreateDto.getLogin())
                .password(accountCreateDto.getPassword())
                .roles(accountCreateDto.getRoles())
                .build();
    }

    private AccountDto convertEntityToDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .login(account.getLogin())
                .password(account.getPassword())
                .roles(account.getRoles())
                .build();
    }
}
