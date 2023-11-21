package cloud.etherly.middleend.business.engine;

import cloud.etherly.middleend.business.engine.task.cache.TaskContext;
import cloud.etherly.middleend.model.UserInfoModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.web.servlet.HandlerMapping;

@Data
@NoArgsConstructor
public class Context {
  private static final ObjectMapper objectMapper = new ObjectMapper();
  private final TaskContext taskContext = new TaskContext();
  private Map<String, Object> requestParams = new HashMap<>();
  private Map<String, String> requestHeaders = new HashMap<>();

  @SneakyThrows
  protected Context(final HttpServletRequest request) {
    final String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
    if (!body.isEmpty()) {
      this.requestParams = objectMapper.readValue(body, Map.class);
    }
    request.getParameterMap().forEach((key, value) -> requestParams.put(key, value[0]));

    final Object attribute = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
    if (attribute instanceof Map) {
      final Map<String, String> pathVariables = (Map<String, String>) attribute;
      pathVariables.forEach((key, value) -> {
        final var snakeCaseKey = key.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
        requestParams.put(snakeCaseKey, value);
      });
    }

    request.getHeaderNames().asIterator().forEachRemaining(
        headerName -> requestHeaders.put(headerName.toLowerCase(), request.getHeader(headerName)));
  }

  public <T> T body(@NonNull final Class<T> clazz, final Supplier<?> exceptionSupplier) {
    try {
      return objectMapper.convertValue(requestParams, clazz);
    } catch (final Exception e) {
      throw (RuntimeException) exceptionSupplier.get();
    }
  }

  public <T, X extends Throwable> T header(@NonNull final String name,
                                           final Supplier<?> exceptionSupplier)
      throws X {
    return (T) Optional.ofNullable(requestHeaders.get(name.toLowerCase()))
        .orElseThrow((Supplier<? extends X>) exceptionSupplier);
  }

  public Optional<String> header(@NonNull final String name) {
    return Optional.ofNullable(requestHeaders.get(name.toLowerCase()));
  }

  public <T> Optional<T> param(@NonNull final String name) {
    return (Optional<T>) Optional.ofNullable(requestParams.get(name));
  }

  public <T, X extends Throwable> T param(@NonNull final String name,
                                          final Supplier<?> exceptionSupplier)
      throws X {
    return (T) Optional.ofNullable(requestParams.get(name))
        .orElseThrow((Supplier<? extends X>) exceptionSupplier);
  }

  public void addParam(final String key, final Object value) {
    requestParams.put(key, value);
  }

  public void addHeader(final String key, final String value) {
    requestHeaders.put(key, value);
  }

  public UserInfoModel getCurrentUser() {
    return (UserInfoModel) this.taskContext.param("userInfo")
        .orElseThrow(() -> new RuntimeException("UserInfo not found in context"));
  }

}
