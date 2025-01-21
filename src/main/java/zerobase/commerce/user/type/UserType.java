package zerobase.commerce.user.type;

import lombok.Getter;

@Getter
public enum UserType {
  ROLE_SELLER("ROLE_SELLER"),
  ROLE_BUYER("ROLE_BUYER");

  private final String name;

  UserType(String name) {
    this.name = name;
  }
}
