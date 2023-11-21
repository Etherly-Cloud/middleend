package cloud.etherly.middleend.controller;

import cloud.etherly.middleend.business.CurrentContext;
import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.TaskManager;
import cloud.etherly.middleend.dto.application.ApplicationTypeDto;
import cloud.etherly.middleend.task.ApplicationTypeTask;
import cloud.etherly.middleend.task.ApplicationTypesTask;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/application/types")
public class ApplicationTypesController {

  private final CurrentContext currentContext;
  private final TaskManager taskManager;

  private final ApplicationTypeTask applicationTypeTask;
  private final ApplicationTypesTask applicationTypesTask;

  @RequestMapping()
  public ResponseEntity<Map<String, ApplicationTypeDto>> getApplicationTypes() {
    final Context context = currentContext.getContext();

    final Map<String, ApplicationTypeDto> applicationTypeDtoList =
        taskManager.executeTask(context, applicationTypesTask).getValue();

    return ResponseEntity.ok(applicationTypeDtoList);
  }

  @RequestMapping("{id}")
  public ResponseEntity<ApplicationTypeDto> getApplicationType() {
    final Context context = currentContext.getContext();

    final ApplicationTypeDto applicationTypeDto = taskManager.executeTask(context, applicationTypeTask).getValue();

    return ResponseEntity.ok(applicationTypeDto);
  }

  @PostMapping()
  public ResponseEntity<ApplicationTypeDto> save() {
    final Context context = currentContext.getContext();

    final ApplicationTypeDto applicationTypeDto = taskManager.executeTask(context, applicationTypeTask).getValue();

    return ResponseEntity.ok(applicationTypeDto);
  }

}
