package cloud.etherly.middleend.task;

import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.Task;
import cloud.etherly.middleend.business.exceptions.UnauthorizedException;
import cloud.etherly.middleend.model.TokenResponse;
import cloud.etherly.middleend.service.AuthorizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GenerateTokenTask implements Task<TokenResponse> {

  private final AuthorizeService authorizeService;

  @Override
  public TokenResponse execute(final Context context) {
    final String code = context.param("code", () -> new UnauthorizedException("No code found in request"));

    return authorizeService.getToken(code);
  }

}
