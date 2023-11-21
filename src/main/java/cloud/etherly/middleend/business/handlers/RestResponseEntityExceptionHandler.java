package cloud.etherly.middleend.business.handlers;

import cloud.etherly.middleend.dto.ExceptionDto;
import cloud.etherly.middleend.utils.EnvironmentUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class RestResponseEntityExceptionHandler {

  private final EnvironmentUtils environmentUtils;

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<Object> handleConflict(final Exception exception) {
    final ExceptionDto exceptionDto = new ExceptionDto(exception, environmentUtils.showStackTrace());
    return new ResponseEntity<>(exceptionDto, HttpStatusCode.valueOf(exceptionDto.getStatus()));
  }

}
