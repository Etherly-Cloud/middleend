package cloud.etherly.middleend.task;

import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.Task;
import cloud.etherly.middleend.business.exceptions.UnauthorizedException;
import cloud.etherly.middleend.model.UserInfoModel;
import cloud.etherly.middleend.service.AuthorizeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserInfoTask implements Task<UserInfoModel> {

  private final AuthorizeService authorizeService;

  @Override
  public UserInfoModel execute(final Context context) {
    final String token = context.header("authorization", () -> new UnauthorizedException("Missing authorization header"));

    return authorizeService.getUserInfo(token);
  }

}
