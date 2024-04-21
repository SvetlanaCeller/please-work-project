package ru.itgirl.pleaseworkproject.service;

import ru.itgirl.pleaseworkproject.dto.UserCreateDto;
import ru.itgirl.pleaseworkproject.dto.UserDto;

public interface UserService {
    UserDto createUser(UserCreateDto userCreateDto);

    public void deleteUser(Long Id);
}
