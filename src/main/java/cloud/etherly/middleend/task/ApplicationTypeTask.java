package cloud.etherly.middleend.task;

import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.Task;
import cloud.etherly.middleend.business.exceptions.BadRequestException;
import cloud.etherly.middleend.dto.application.ApplicationTypeDto;
import cloud.etherly.middleend.service.MetadataService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationTypeTask implements Task<ApplicationTypeDto> {

  private final MetadataService metadataService;

  @Override
  public ApplicationTypeDto execute(final Context context) {
    final String applicationType =
        context.param("id", () -> new BadRequestException("application-type is required"));

    return metadataService.getApplicationType(applicationType);
  }

}
