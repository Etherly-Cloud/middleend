package cloud.etherly.middleend.client.cognito;

import cloud.etherly.middleend.model.TokenResponse;
import cloud.etherly.middleend.model.UserInfoModel;

public interface CognitoClient {

  UserInfoModel getUserInfo(String accessToken);

  TokenResponse getToken(String code);

  String getLoginUrl();

  String getLogoutUrl();

}
