package cloud.etherly.middleend.dto.application;

import cloud.etherly.middleend.type.ApplicationScopeStatusType;
import cloud.etherly.middleend.type.ApplicationScopeType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationScopeDto {

  private final String id = UUID.randomUUID().toString();
  private String name;

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private ApplicationScopeStatusType status = ApplicationScopeStatusType.INACTIVE;

  private ApplicationScopeType type;

}
