package zerobase.commerce.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {
  Class<? extends Enum<?>> enumClass();
  String message() default "올바른 값을 입력해주세요.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
