package cloud.etherly.middleend.business.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
  public NotFoundException(final String message) {
    super(message, HttpStatus.NOT_FOUND);
    this.setDisplayMessage("No se encontró el recurso solicitado.");
  }

}
