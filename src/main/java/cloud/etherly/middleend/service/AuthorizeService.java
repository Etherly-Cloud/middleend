package cloud.etherly.middleend.service;

import cloud.etherly.middleend.client.cognito.CognitoClient;
import cloud.etherly.middleend.model.TokenResponse;
import cloud.etherly.middleend.model.UserInfoModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizeService {

  private final CognitoClient cognitoClient;

  public UserInfoModel getUserInfo(final String accessToken) {
    return cognitoClient.getUserInfo(accessToken);
  }

  public TokenResponse getToken(final String code) {
    return cognitoClient.getToken(code);
  }

  public String getLoginUrl() {
    return cognitoClient.getLoginUrl();
  }

  public String getLogoutUrl() {
    return cognitoClient.getLogoutUrl();
  }

}
