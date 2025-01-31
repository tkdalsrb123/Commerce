package zerobase.commerce.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.commerce.user.dto.UserDto;
import zerobase.commerce.user.service.UserService;

@RestController
@RequiredArgsConstructor
@ResponseBody
public class UserController {

  private final UserService userService;

  @PostMapping("/users")
  public UserDto.Response register(UserDto.Request userDtoRequest) {
    return UserDto.Response.of(
        userService.register((userDtoRequest)));
  }

}
