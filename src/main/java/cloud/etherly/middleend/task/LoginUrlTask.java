package cloud.etherly.middleend.task;

import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.Task;
import cloud.etherly.middleend.dto.LoginUrlDto;
import cloud.etherly.middleend.service.AuthorizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginUrlTask implements Task<LoginUrlDto> {

  private final AuthorizeService authorizeService;

  @Override
  public LoginUrlDto execute(final Context context) {
    return new LoginUrlDto(
        authorizeService.getLoginUrl()
    );
  }

}
