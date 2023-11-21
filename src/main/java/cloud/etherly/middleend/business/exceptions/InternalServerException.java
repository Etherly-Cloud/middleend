package cloud.etherly.middleend.business.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerException extends ApiException {
  public InternalServerException(final String message, final Throwable cause) {
    super(message, HttpStatus.INTERNAL_SERVER_ERROR, cause);
  }

  public InternalServerException(final String message) {
    super(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
