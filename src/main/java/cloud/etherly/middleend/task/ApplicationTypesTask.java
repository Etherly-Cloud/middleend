package cloud.etherly.middleend.task;

import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.Task;
import cloud.etherly.middleend.dto.application.ApplicationTypeDto;
import cloud.etherly.middleend.service.MetadataService;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationTypesTask implements Task<Map<String, ApplicationTypeDto>> {

  private final MetadataService metadataService;

  @Override
  public Map<String, ApplicationTypeDto> execute(final Context context) {
    return metadataService.getApplicationTypes();
  }

}
