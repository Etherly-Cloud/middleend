package cloud.etherly.middleend.client.cognito;

import cloud.etherly.middleend.business.exceptions.BadRequestException;
import cloud.etherly.middleend.business.exceptions.InternalServerException;
import cloud.etherly.middleend.business.exceptions.UnauthorizedException;
import cloud.etherly.middleend.client.HttpClient;
import cloud.etherly.middleend.model.TokenRequest;
import cloud.etherly.middleend.model.TokenResponse;
import cloud.etherly.middleend.model.UserInfoModel;
import java.util.Map;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CognitoClientImpl implements CognitoClient {

  private final CognitoConfigurations cognitoConfigurations;
  private final HttpClient httpClient;

  @Override
  public UserInfoModel getUserInfo(final String accessToken) {
    final String normalizedAccessToken = accessToken.replace("Bearer ", "");
    final String path = "/oauth2/userInfo";

    final Map<String, String> headers = Map.of(
        "Authorization", "Bearer " + normalizedAccessToken
    );

    final Map<HttpStatus, Supplier<RuntimeException>> codes = Map.of(
        HttpStatus.UNAUTHORIZED, () -> new UnauthorizedException("You are not authorized to access this resource"),
        HttpStatus.BAD_REQUEST, () -> new BadRequestException("Invalid authorization token"),
        HttpStatus.INTERNAL_SERVER_ERROR, () -> new InternalServerException("An internal server error occurred")
    );

    return httpClient.get(cognitoConfigurations.getUrl(), path, headers, UserInfoModel.class, codes);
  }

  @Override
  public TokenResponse getToken(final String code) {
    final TokenRequest tokenRequest = buildTokenRequest(code);
    final String path = "/oauth2/token";

    final Map<HttpStatus, Supplier<RuntimeException>> codes = Map.of(
        HttpStatus.UNAUTHORIZED, () -> new BadRequestException("Invalid code"),
        HttpStatus.BAD_REQUEST, () -> new BadRequestException("Invalid code"),
        HttpStatus.INTERNAL_SERVER_ERROR, () -> new InternalServerException("An internal server error occurred")
    );

    return httpClient.postEncoded(cognitoConfigurations.getUrl(), path, tokenRequest, TokenResponse.class, codes);
  }

  @Override
  public String getLoginUrl() {
    final String path = "/oauth2/authorize";

    return httpClient.getUrl(cognitoConfigurations.getUrl(), path, getLoginParams());
  }

  @Override
  public String getLogoutUrl() {
    final String path = "/logout";

    return httpClient.getUrl(cognitoConfigurations.getUrl(), path, getLoginParams());
  }

  private Map<String, String> getLoginParams() {
    return Map.of(
        "response_type", "code",
        "client_id", cognitoConfigurations.getClientId(),
        "scope", cognitoConfigurations.getScope(),
        "redirect_uri", cognitoConfigurations.getLoginRedirectUri()
    );
  }

  private TokenRequest buildTokenRequest(final String code) {
    return new TokenRequest(
        cognitoConfigurations.getGrantType(),
        cognitoConfigurations.getClientId(),
        cognitoConfigurations.getLoginRedirectUri(),
        cognitoConfigurations.getScope(),
        cognitoConfigurations.getClientSecret(),
        code
    );
  }

}
