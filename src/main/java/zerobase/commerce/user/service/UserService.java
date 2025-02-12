package zerobase.commerce.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.dto.UserDto;
import zerobase.commerce.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public User register(UserDto.Request userDtoRequest) {
    return userRepository.save(
        User.builder()
            .username(userDtoRequest.getUsername())
            .password(passwordEncoder.encode(userDtoRequest.getPassword()))
            .role(userDtoRequest.getRole())
            .build()
    );
  }
}
