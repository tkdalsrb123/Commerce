package zerobase.commerce.user.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.type.UserType;


public class UserDto {

  @Getter
  @Setter
  public static class Request {
    private String username;
    private String password;
    private UserType role;
  }

  @Getter
  @Setter
  @Builder
  public static class Response {
    private String username;
    private LocalDateTime createdAt;

    public static Response of(User user) {
      return Response.builder()
          .username(user.getUsername())
          .createdAt(user.getCreated())
          .build();
    }
  }
}
