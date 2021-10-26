package com.cinema.project.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserLoginRequestDtoToClientMapper userLoginRequestDtoToClientMapper;
    private final ClientRegisterValidator clientRegisterValidator;

    public User loginUser(UserLoginRequestDto userLoginRequestDto) {
        return userRepository.getUserByLogin(userLoginRequestDto.getLogin())
                .filter(user -> user.getPassword().equals(userLoginRequestDto.getPassword()))
                .orElseThrow(() -> new UserLoginException("wrong_login_or_password"));
    }

    public User registerClient(UserLoginRequestDto userLoginRequestDto) {
        User client = userLoginRequestDtoToClientMapper.map(userLoginRequestDto);
        return userRepository.registerClient(clientRegisterValidator.validate(client));
    }

}
