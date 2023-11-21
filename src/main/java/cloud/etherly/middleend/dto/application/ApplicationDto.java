package cloud.etherly.middleend.dto.application;

import cloud.etherly.middleend.type.ApplicationType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {

  private final String id = UUID.randomUUID().toString();
  private String name;
  private String description;

  @JsonProperty(value = "type", access = JsonProperty.Access.READ_ONLY)
  private ApplicationTypeDto applicationTypeDto;

  @JsonProperty(value = "type", access = JsonProperty.Access.WRITE_ONLY)
  private ApplicationType applicationType;

  private List<ApplicationScopeDto> scopes = new ArrayList<>();

  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private List<ApplicationVersionDto> versions = new ArrayList<>();

  private List<String> owners = new ArrayList<>();

}
