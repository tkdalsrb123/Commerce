package zerobase.commerce.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.dto.UserDto;
import zerobase.commerce.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User register(UserDto.Request userDtoRequest) {
    String passwordHash = passwordEncoder.encode(userDtoRequest.getPassword());
    return userRepository.save(
        User.builder()
            .username(userDtoRequest.getUsername())
            .password(passwordHash)
            .role(userDtoRequest.getRole())
            .build()
    );
  }
}
