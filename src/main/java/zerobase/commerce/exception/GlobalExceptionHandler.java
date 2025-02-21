package zerobase.commerce.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler{

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    ErrorCode errorCode = e.getErrorCode();
    ErrorResponse response = new ErrorResponse(errorCode.getMessage(), errorCode.getStatus().value());
    return new ResponseEntity<>(response, errorCode.getStatus());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    ErrorResponse response = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value());
    return new ResponseEntity<>(response, ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
  }

}
