package cloud.etherly.middleend.controller;

import cloud.etherly.middleend.business.CurrentContext;
import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.exceptions.BadRequestException;
import cloud.etherly.middleend.dto.application.ApplicationDto;
import cloud.etherly.middleend.service.ApplicationService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/application")
public class ApplicationController {

  private final CurrentContext currentContext;
  private final ApplicationService applicationService;

  @RequestMapping()
  public ResponseEntity<List<ApplicationDto>> getApplications() {
    return ResponseEntity.ok(applicationService.getApplications());
  }

  @RequestMapping("{id}")
  public ResponseEntity<ApplicationDto> getApplicationById() {
    final Context context = currentContext.getContext();
    final String id = context.param("id", () -> new BadRequestException("Missing id parameter"));

    return ResponseEntity.ok(applicationService.getApplication(id));
  }

  @PostMapping()
  public ResponseEntity<ApplicationDto> createApplication() {
    final Context context = currentContext.getContext();
    final ApplicationDto applicationDto = context.body(ApplicationDto.class,
        () -> new BadRequestException("The application body is corrupted"));

    return ResponseEntity.ok(applicationService.createApplication(applicationDto));
  }

}
