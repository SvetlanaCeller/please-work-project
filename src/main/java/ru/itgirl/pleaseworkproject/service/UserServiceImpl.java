package ru.itgirl.pleaseworkproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itgirl.pleaseworkproject.dto.UserCreateDto;
import ru.itgirl.pleaseworkproject.dto.UserDto;
import ru.itgirl.pleaseworkproject.model.MyUser;
import ru.itgirl.pleaseworkproject.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserCreateDto userCreateDto) {
        MyUser user = userRepository.save(convertDtoToEntity(userCreateDto));
        return convertEntityToDto(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDto convertEntityToDto(MyUser user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .roles(user.getRoles()).build();
    }

    private MyUser convertDtoToEntity(UserCreateDto userCreateDto) {
        return MyUser.builder()
                .name(userCreateDto.getName())
                .password(passwordEncoder.encode(userCreateDto.getPassword()))
                .roles(userCreateDto.getRoles()).build();
    }
}
