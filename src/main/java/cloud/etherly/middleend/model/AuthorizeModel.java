package cloud.etherly.middleend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizeModel {

  private TokenResponse tokenResponse;
  private UserInfoModel userInfoModel;

}
