package cloud.etherly.middleend.dto;

import cloud.etherly.middleend.business.exceptions.ApiException;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Arrays;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
public class ExceptionDto {

  private Integer status;
  private String message;
  private String displayMessage;
  private String stackTrace;

  public ExceptionDto(final Exception exception, final boolean showStackTrace) {
    if (exception instanceof final ApiException apiException) {
      this.status = apiException.getStatus().value();
      this.message = apiException.getMessage();
      this.displayMessage =
          Optional.ofNullable(apiException.getDisplayMessage()).orElse(this.displayMessage);
    } else {
      this.message = exception.getMessage();
      this.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
      this.displayMessage = "Ocurrió un error inesperado.";
    }

    if (showStackTrace) {
      this.stackTrace = Arrays.toString(exception.getStackTrace());
    }
  }

}
