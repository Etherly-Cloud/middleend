package cloud.etherly.middleend.business.interceptor;

import cloud.etherly.middleend.business.CurrentContext;
import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.TaskManager;
import cloud.etherly.middleend.task.UserInfoTask;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

  private final CurrentContext currentContext;
  private final TaskManager taskManager;
  private final UserInfoTask userInfoTask;

  @Override
  public boolean preHandle(@NotNull final HttpServletRequest request, @NotNull final HttpServletResponse response,
                           @NotNull final Object handler) {

    final Context context = currentContext.getContext();
    taskManager.executeTask(context, userInfoTask);
    return true;
  }

}

