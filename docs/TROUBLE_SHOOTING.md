# Trouble Shooting

---
>프로젝트를 진행하면서 발생한 문제점들과 해결 과정을 작성한다.

---

### 에러
```
io.jsonwebtoken.RequiredTypeException: Cannot convert existing claim value of type 'class java.lang.String' to desired type 'class zerobase.commerce.user.type.UserType'. JJWT only converts simple String, Date, Long, Integer, Short and Byte types automatically. Anything more complex is expected to be already converted to your desired type by the JSON Deserializer implementation. You may specify a custom Deserializer for a JwtParser with the desired conversion configuration via the JwtParserBuilder.deserializer() method. See https://github.com/jwtk/jjwt#custom-json-processor for more information. If using Jackson, you can specify custom claim POJO types as described in https://github.com/jwtk/jjwt#json-jackson-custom-types
```

### 해결과정
- jwt로 로그인 인증 과정을 구현중 enum을 사용하여 role을 지정하려고 했다.
- 하지만 jwt는 기본적으로 Sring, Date, Long, Integer, Short, Byte 타입만 자동으로 변해준다.
- 즉, enum을 받아오면 String으로 변환해야 하고 jwt에서 값을 반환받으면 Enum.valueOf()로 enum 값으로 변환해야한다.

```
UserType role = UserType.valueOf(jwtUtil.getRole(token));
```
```
@Override
      public String getAuthority() {
        return user.getRole().getName();
      }
    });
```