package cloud.etherly.middleend.business.exceptions;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends ApiException {
  public UnauthorizedException(final String message, final Throwable cause) {
    super(message, HttpStatus.UNAUTHORIZED, cause);
    this.setDisplayMessage("No esta autorizado para realizar esta accion.");
  }

  public UnauthorizedException(final String message) {
    this(message, null);
  }

}
