package cloud.etherly.middleend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfoDto {

  private String id;
  private String username;
  private String email;

}
