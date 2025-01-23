package zerobase.commerce.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.dto.CustomUserDetails;
import zerobase.commerce.user.type.UserType;


public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  public JwtFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String authorizationHeader = request.getHeader("Authorization");

    // authorization 헤더 검증
    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      System.out.println("Authorization header not found");
      filterChain.doFilter(request, response);
      return;
    }

    // Bearer 제거 후 순수 토큰 획득
    String token = authorizationHeader.split(" ")[1];

    // 토큰 소멸 시간 검증
    if (jwtUtil.isExpired(token)) {
      System.out.println("Expired token");
      filterChain.doFilter(request, response);
      return;
    }

    String username = jwtUtil.getUsername(token);
    UserType role = UserType.valueOf(jwtUtil.getRole(token));

    User user = new User();
    user.setUsername(username);
    user.setPassword("temppasword");
    user.setRole(role);

    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    // 스프링 시큐리티 인증 토큰 생성
    Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null,
        customUserDetails.getAuthorities());


    // 세션에 사용자 등록
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);

  }
}
