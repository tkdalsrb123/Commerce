package zerobase.commerce.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{
  private final ErrorCode errorCode;

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public CustomException(ErrorCode errorCode, String s) {
    super(errorCode.getMessage() + s);
    this.errorCode = errorCode;
  }

}
