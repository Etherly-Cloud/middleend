package cloud.etherly.middleend.marshaller;

import cloud.etherly.middleend.business.engine.Marshaller;
import cloud.etherly.middleend.dto.AuthorizeDto;
import cloud.etherly.middleend.model.AuthorizeModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorizeMarshaller implements Marshaller<AuthorizeModel, AuthorizeDto> {

  private final UserInfoMarshaller userInfoMarshaller;

  @Override
  public AuthorizeDto marshall(final AuthorizeModel input) {
    return new AuthorizeDto(input.getTokenResponse(), userInfoMarshaller.marshall(input.getUserInfoModel()));
  }

  @Override
  public Class<AuthorizeModel> getInputClass() {
    return AuthorizeModel.class;
  }

}
