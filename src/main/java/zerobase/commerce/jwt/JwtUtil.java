package zerobase.commerce.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  private final SecretKey secretKey;

  public JwtUtil(@Value("${spring.jwt.secret}") String secret
  ) {
    this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
        SIG.HS256.key().build().getAlgorithm());
  }

  // 검증
  public String getUsername(String token) {
    // verifyWith = 토큰이 우리 서버에서 생성되었는지, 우리 서버에서 생성된게 우리가 가지고 있는 키와 맞는지 확인하는 메서드
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
        .get("username", String.class);
  }

  // 검증
  public String getRole(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
        .get("role", String.class);
  }

  // 검증
  public Boolean isExpired(String token) {
    return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload()
        .getExpiration().before(new Date());
  }

  // 생성
  public String createJwt(String username, String role, Long expiredMs) {
    return Jwts.builder()
        .claim("username", username)
        .claim("role", role)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expiredMs))
        .signWith(secretKey)
        .compact();
  }
}
