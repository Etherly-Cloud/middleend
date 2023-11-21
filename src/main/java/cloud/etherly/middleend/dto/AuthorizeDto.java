package cloud.etherly.middleend.dto;

import cloud.etherly.middleend.model.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizeDto {

  private final TokenResponse token;
  private final UserInfoDto user;

}
