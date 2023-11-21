package cloud.etherly.middleend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenResponse {

  public String idToken;
  public String accessToken;
  public String refreshToken;
  public int expiresIn;
  public String tokenType;

  public TokenResponse(final String accessToken) {
    this.accessToken = accessToken;
  }

}
