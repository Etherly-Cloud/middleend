package cloud.etherly.middleend.business.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApiException {
  public BadRequestException(final String message) {
    super(message, HttpStatus.BAD_REQUEST);
    this.setDisplayMessage("La solicitud no pudo ser procesada, revise los datos enviados.");
  }

}
