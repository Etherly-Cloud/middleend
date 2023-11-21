package cloud.etherly.middleend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoModel {

  private String sub;
  private String email;
  private String username;
  private boolean email_verified;

}
