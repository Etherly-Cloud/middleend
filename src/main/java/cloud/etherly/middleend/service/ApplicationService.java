package cloud.etherly.middleend.service;

import cloud.etherly.middleend.business.exceptions.BadRequestException;
import cloud.etherly.middleend.client.kvs.KvsClient;
import cloud.etherly.middleend.dto.application.ApplicationDto;
import cloud.etherly.middleend.dto.application.ApplicationScopeDto;
import cloud.etherly.middleend.dto.application.ApplicationTypeDto;
import cloud.etherly.middleend.dto.application.ApplicationVersionDto;
import cloud.etherly.middleend.type.ApplicationScopeStatusType;
import cloud.etherly.middleend.type.ApplicationScopeType;
import cloud.etherly.middleend.type.ApplicationType;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

  private final KvsClient<ApplicationDto> kvsClient;
  private final MetadataService metadataService;

  public ApplicationService(final KvsClient<ApplicationDto> kvsClient, final MetadataService metadataService) {
    this.kvsClient = kvsClient;
    this.metadataService = metadataService;
    this.createApplication(buildMock(metadataService));
  }

  private ApplicationDto buildMock(final MetadataService metadataService) {
    final ApplicationType applicationType = ApplicationType.WEB_APP;
    final ApplicationTypeDto applicationTypeDto = metadataService.getApplicationType(applicationType.name());
    final List<ApplicationScopeDto> scopes = List.of(
        new ApplicationScopeDto("testrelease", ApplicationScopeStatusType.EFFECTIVE, ApplicationScopeType.PROD),
        new ApplicationScopeDto("prod", ApplicationScopeStatusType.DEPLOYING, ApplicationScopeType.TEST)
    );

    final List<ApplicationVersionDto> versions = List.of(
        new ApplicationVersionDto("0.0.1-init", "develop"),
        new ApplicationVersionDto("0.0.1", "master")
    );

    final List<String> admin = List.of("admin", "juan.campino");
    return new ApplicationDto("ping-app", "a ping-app-api :)", applicationTypeDto, applicationType, scopes, versions, admin);
  }

  public ApplicationDto getApplication(final String id) {
    return kvsClient.find(id).orElseThrow(() -> new BadRequestException(String.format("Application with id %s not found", id)));
  }

  public List<ApplicationDto> getApplications() {
    return kvsClient.findAll();
  }

  public ApplicationDto createApplication(final ApplicationDto applicationDto) {
    Optional.ofNullable(applicationDto.getName()).orElseThrow(() -> new BadRequestException("Application name is required"));
    Optional.ofNullable(applicationDto.getDescription())
        .orElseThrow(() -> new BadRequestException("Application description is required"));
    Optional.ofNullable(applicationDto.getApplicationType())
        .orElseThrow(() -> new BadRequestException("Application type is required"));

    if (applicationDto.getScopes().isEmpty()) {
      throw new BadRequestException("Application scopes is required");
    }

    if (applicationDto.getOwners().isEmpty()) {
      throw new BadRequestException("Application owners is required");
    }

    final List<ApplicationDto> all = kvsClient.findAll();
    final boolean exists = all.stream().anyMatch(application -> application.getName().equals(applicationDto.getName()));
    if (exists) {
      throw new BadRequestException(String.format("Application with name %s already exists", applicationDto.getName()));
    }

    applicationDto.setApplicationTypeDto(metadataService.getApplicationType(applicationDto.getApplicationType().name()));

    return kvsClient.save(applicationDto.getId(), applicationDto);
  }

}
