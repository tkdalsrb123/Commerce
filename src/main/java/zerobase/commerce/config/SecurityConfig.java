package zerobase.commerce.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import zerobase.commerce.jwt.JwtFilter;
import zerobase.commerce.jwt.JwtUtil;
import zerobase.commerce.jwt.LoginFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtUtil jwtUtil;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration),
        jwtUtil);

    // LoginFilter는 로그인 페이지가 /login 으로 설정되어있다. 로그인 페이지를 변환
    loginFilter.setFilterProcessesUrl("/users/login");

    http.csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers("/users/**", "/users", "/products/**").permitAll() // 모든 사용자 접근 가능
            .requestMatchers("/buyer/**").hasRole("BUYER") // BUYER 권한만 접근 가능
            .requestMatchers("/seller/**").hasRole("SELLER") // SELLER 권한만 접근 가능
            .requestMatchers("/reviews/**").authenticated()
            .anyRequest().authenticated() // 다른 요청들은 로그인한 사용자만 접근 가능
        )
        // jwtFilter는 토큰의 유무를 확인. 이때 jwt를 검증하고 securityContextHolder에 세션을 생성
        .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
        .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
        // form 로그인 방식 disable -> why? jwt 방식을 사용할 것이기 때문에
        .formLogin((auth) -> auth.disable())
        // http basic 인증 방식 disable -> why? jwt 방식을 사용할 것이기 때문에
        .httpBasic((auth) -> auth.disable())

        // 세션 설정 -> jwt 방식에서는 세션을 stateless 방식으로 관리함(가장 중요)
        .sessionManagement(
            (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

}
