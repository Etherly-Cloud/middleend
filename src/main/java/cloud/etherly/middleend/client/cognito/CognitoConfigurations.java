package cloud.etherly.middleend.client.cognito;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "client.cognito")
public class CognitoConfigurations {

  public String url;
  public String grantType;
  public String clientId;
  public String clientSecret;
  public String loginRedirectUri;
  public String scope;

}
