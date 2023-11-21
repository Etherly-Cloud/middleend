package cloud.etherly.middleend.task;

import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.Task;
import cloud.etherly.middleend.dto.LogoutUrlDto;
import cloud.etherly.middleend.service.AuthorizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LogoutUrlTask implements Task<LogoutUrlDto> {

  private final AuthorizeService authorizeService;

  @Override
  public LogoutUrlDto execute(final Context context) {
    return new LogoutUrlDto(
        authorizeService.getLogoutUrl()
    );
  }

}
