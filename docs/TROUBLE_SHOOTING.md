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

---

### 에러
```
jakarta.validation.UnexpectedTypeException: HV000030: No validator could be found for constraint 'jakarta.validation.constraints.NotBlank' validating type 'zerobase.commerce.product.type.ProductCategory'. Check configuration for 'productCategory'
```

### 해결과정
- ValidEnum을 커스터마이징 하여 enum에 대하여 검증하는 어노테이션을 작성

[ValidEnum.java](../src/main/java/zerobase/commerce/validation/ValidEnum.java)\
[EnumValidator.java](../src/main/java/zerobase/commerce/validation/EnumValidator.java)

---

### 에러
```angular2html
Caused by: java.sql.SQLSyntaxErrorException: 
You have an error in your SQL syntax; 
check the manual that corresponds to your MySQL server version for the right syntax to use near 
'order add constraint FKt7abetueht6dd1gs9jyl3o4t7 foreign key (user_id) reference' at line 1
```

### 해결과정
- mysql 예약어인 order by 와 order table 명이 충돌하여 생긴 오류
- 이를 방지하기 위해 모든 table 명들을 복수형으로 변경

---

### 에러
```angular2html
java.lang.ClassCastException: class java.lang.Long cannot be cast to class java.lang.Integer (java.lang.Long and java.lang.Integer are in module java.base of loader 'bootstrap')
```

### 해결과정
- JPQL을 사용하여 직접 쿼리를 작성하고 데이터를 조회하였을 때 Integer 값도 Long으로 불러온다.
- 이때 (Integer)를 사용하여 강제 캐스팅하게 되면 발생하는 오류로 이유는 Long은 null값이 포함될 수 있기 때문인다.
- (Long) 으로 받은 후 .intValue()를 사용하여 안전하게 변환한다.

---

### 에러
- getCart()를 PostMan으로 API 테스트 중 403 에러

### 해결과정
- cart에 상품을 등록하거나 삭제하는 메서드는 잘 동작하는데 조회하는 기능은 403 error 반환한다.
- 이유는 @ResponseBody의 유무인데 @Controller를 사용할 때 @ResponseBody가 없으면 Spring MVC는 뷰를 반환하려고 시도한다.