package cloud.etherly.middleend.business;

import cloud.etherly.middleend.dto.application.ApplicationTypeDto;
import java.util.Map;
import lombok.Getter;

@Getter
public class Metadata {

  private Map<String, ApplicationTypeDto> types;

}
