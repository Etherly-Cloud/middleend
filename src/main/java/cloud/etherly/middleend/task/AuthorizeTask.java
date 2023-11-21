package cloud.etherly.middleend.task;

import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.Task;
import cloud.etherly.middleend.business.engine.task.TaskManager;
import cloud.etherly.middleend.model.AuthorizeModel;
import cloud.etherly.middleend.model.TokenResponse;
import cloud.etherly.middleend.model.UserInfoModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorizeTask implements Task<AuthorizeModel> {

  private final TaskManager taskManager;

  private final GenerateTokenTask generateTokenTask;
  private final UserInfoTask userInfoTask;

  @Override
  public AuthorizeModel execute(final Context context) {
    final TokenResponse tokenResponse = taskManager.executeTask(context, generateTokenTask).getValue();
    final String accessToken = tokenResponse.getAccessToken();

    context.addHeader("authorization", accessToken);

    final UserInfoModel userInfoModel = taskManager.executeTask(context, userInfoTask).getValue();
    return new AuthorizeModel(tokenResponse, userInfoModel);
  }

}
