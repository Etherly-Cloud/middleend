package cloud.etherly.middleend.business.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {
  private final String message;
  private final HttpStatus status;
  private String displayMessage;
  private String errorCode;

  public ApiException(final String message, final HttpStatus status) {
    this(message, status, null);
  }

  public ApiException(final String message, final HttpStatus status, final Throwable cause) {
    super(message, cause);
    this.message = message;
    this.status = status;
  }

}
