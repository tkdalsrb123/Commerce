package zerobase.commerce.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import zerobase.commerce.exception.CustomException;
import zerobase.commerce.exception.ErrorCode;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.dto.CustomUserDetails;
import zerobase.commerce.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    return new CustomUserDetails(user);
  }
}
