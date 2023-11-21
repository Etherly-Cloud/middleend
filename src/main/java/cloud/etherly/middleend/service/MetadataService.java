package cloud.etherly.middleend.service;

import cloud.etherly.middleend.business.Metadata;
import cloud.etherly.middleend.business.exceptions.BadRequestException;
import cloud.etherly.middleend.dto.application.ApplicationTypeDto;
import cloud.etherly.middleend.type.ApplicationType;
import java.util.Map;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MetadataService {

  private final Metadata metadata;

  public ApplicationTypeDto getApplicationType(final String name) {
    final ApplicationType applicationType = Optional.ofNullable(ApplicationType.fromString(name)).orElseThrow(
        () -> new BadRequestException("No constant with name " + name + " found"));

    return Optional.ofNullable(metadata.getTypes().get(applicationType.getValue()))
        .orElseThrow(() -> new BadRequestException("A metadata entry for " + name + " was not found"));
  }

  public Map<String, ApplicationTypeDto> getApplicationTypes() {
    return metadata.getTypes();
  }

}
