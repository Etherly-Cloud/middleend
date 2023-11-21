package cloud.etherly.middleend.business.engine;

import cloud.etherly.middleend.business.CurrentContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@AllArgsConstructor
public class EngineInterceptor implements HandlerInterceptor {

  private final CurrentContext currentContext;

  @Override
  public boolean preHandle(@NotNull final HttpServletRequest request, @NotNull final HttpServletResponse response,
                           @NotNull final Object handler) {

    currentContext.setContext(new Context(request));
    return true;
  }

}
