package cloud.etherly.middleend.client;

import cloud.etherly.middleend.business.exceptions.ApiException;
import cloud.etherly.middleend.business.exceptions.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class HttpClient {

  private final ObjectMapper objectMapper;
  private final OkHttpClient httpClient;

  public <T> T get(final String host, final String path, final Class<T> clazz,
                   final Map<HttpStatus, Supplier<RuntimeException>> errorMap) {
    return get(host, path, null, clazz, errorMap);
  }

  public <T> T get(final String host, final String path, final Class<T> clazz) {
    return get(host, path, null, clazz, null);
  }

  public <T> T get(final String host, final String path, final Map<String, String> headers,
                   final Class<T> clazz,
                   final Map<HttpStatus, Supplier<RuntimeException>> errorMap) {
    final String url = getUrl(host, path);
    final Request.Builder builder = new Request.Builder().url(url);
    if (headers != null) {
      builder.headers(Headers.of(headers));
    }
    return request(url, clazz, builder.build(), errorMap);
  }

  public <T> T get(final String host, final String path, final Map<String, String> headers,
                   final Class<T> clazz) {
    return get(host, path, headers, clazz, null);
  }

  public <T> T post(final String host, final String path, final Class<T> clazz, final RequestBody requestBody,
                    final Map<HttpStatus, Supplier<RuntimeException>> errorMap) {
    final String url = getUrl(host, path);
    final Request request = new Request.Builder().url(url).method("POST", requestBody).build();
    return request(url, clazz, request, errorMap);
  }

  public <T> T post(final String host, final String path, final Object body, final Class<T> clazz,
                    final Map<HttpStatus, Supplier<RuntimeException>> errorMap) {
    try {
      final MediaType mediaType = MediaType.get("application/json");
      final String json = objectMapper.writeValueAsString(body);
      final RequestBody requestBody = RequestBody.create(json, mediaType);
      return post(host, path, clazz, requestBody, errorMap);
    } catch (final JsonProcessingException e) {
      throw new InternalServerException("Error while serializing object", e);
    }
  }

  public <T> T postEncoded(final String host, final String path, final Object body,
                           final Class<T> clazz,
                           final Map<HttpStatus, Supplier<RuntimeException>> errorMap) {
    final Map<String, String> values = objectMapper.convertValue(body, Map.class);
    final FormBody.Builder builder = new FormBody.Builder();
    values.forEach(builder::addEncoded);
    return post(host, path, clazz, builder.build(), errorMap);
  }

  @Nullable
  private <T> T request(final String url, final Class<T> clazz, final Request request,
                        final Map<HttpStatus, Supplier<RuntimeException>> errorMap) {
    try (final Response response = httpClient.newCall(request).execute()) {
      if (!response.isSuccessful()) {
        final HttpStatus httpStatus = HttpStatus.valueOf(response.code());
        final Supplier<RuntimeException> defaultValue =
            () -> new ApiException(String.format("Error while calling %s with status %s", url, response.code()), httpStatus);

        final Supplier<RuntimeException> exceptionSupplier =
            Optional.ofNullable(errorMap).flatMap(map -> Optional.ofNullable(map.get(httpStatus))).orElse(defaultValue);

        throw exceptionSupplier.get();
      }
      final ResponseBody body = response.body();
      if (body == null) {
        return null;
      }

      return this.objectMapper.readValue(body.string(), clazz);
    } catch (final IOException e) {
      throw new InternalServerException(String.format("Error while calling %s", url), e);
    }
  }

  public String getUrl(final String host, final String path, final Map<String, String> queryMap) {
    try {
      final URI uri = new URI(host);
      final String query = Optional.ofNullable(queryMap)
          .flatMap(map -> map.entrySet().stream().map(entry -> entry.getKey() + "=" + entry.getValue())
              .reduce((a, b) -> a + "&" + b)).orElse(null);
      return new URI(uri.getScheme(), null, uri.getHost(), uri.getPort(), path, query, null).toString();
    } catch (final URISyntaxException e) {
      throw new InternalServerException("Invalid cognito url", e);
    }
  }

  public String getUrl(final String host, final String path) {
    return getUrl(host, path, null);
  }

}
