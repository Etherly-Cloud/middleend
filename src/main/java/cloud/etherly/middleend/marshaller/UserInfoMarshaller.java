package cloud.etherly.middleend.marshaller;

import cloud.etherly.middleend.business.engine.Marshaller;
import cloud.etherly.middleend.dto.UserInfoDto;
import cloud.etherly.middleend.model.UserInfoModel;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMarshaller implements Marshaller<UserInfoModel, UserInfoDto> {
  @Override
  public UserInfoDto marshall(final UserInfoModel input) {
    return new UserInfoDto(input.getSub(), input.getUsername(), input.getEmail());
  }

  @Override
  public Class<UserInfoModel> getInputClass() {
    return UserInfoModel.class;
  }

}
