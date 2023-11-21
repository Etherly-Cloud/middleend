package cloud.etherly.middleend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenRequest {

  public String grantType;
  public String clientId;
  public String redirectUri;
  public String scope;
  public String clientSecret;
  public String code;

}
