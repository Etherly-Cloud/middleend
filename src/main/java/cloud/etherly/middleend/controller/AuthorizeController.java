package cloud.etherly.middleend.controller;

import cloud.etherly.middleend.business.CurrentContext;
import cloud.etherly.middleend.business.engine.Context;
import cloud.etherly.middleend.business.engine.task.TaskManager;
import cloud.etherly.middleend.dto.AuthorizeDto;
import cloud.etherly.middleend.dto.LoginUrlDto;
import cloud.etherly.middleend.dto.LogoutUrlDto;
import cloud.etherly.middleend.dto.UserInfoDto;
import cloud.etherly.middleend.marshaller.AuthorizeMarshaller;
import cloud.etherly.middleend.marshaller.UserInfoMarshaller;
import cloud.etherly.middleend.task.AuthorizeTask;
import cloud.etherly.middleend.task.LoginUrlTask;
import cloud.etherly.middleend.task.LogoutUrlTask;
import cloud.etherly.middleend.task.UserInfoTask;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/authorize")
@AllArgsConstructor
public class AuthorizeController {

  private final CurrentContext currentContext;
  private final TaskManager taskManager;

  private final AuthorizeMarshaller authorizeMarshaller;
  private final UserInfoMarshaller userInfoMarshaller;

  private final UserInfoTask userInfoTask;
  private final LoginUrlTask loginUrlTask;
  private final LogoutUrlTask logoutUrlTask;
  private final AuthorizeTask authorizeTask;

  @GetMapping("/complete")
  public ResponseEntity<AuthorizeDto> authorize() {
    final Context context = currentContext.getContext();

    final AuthorizeDto authorizeDto = taskManager.executeTask(context, authorizeTask, authorizeMarshaller);
    return ResponseEntity.ok(authorizeDto);
  }

  @GetMapping("/info")
  public ResponseEntity<UserInfoDto> getUser() {
    final Context context = currentContext.getContext();

    final UserInfoDto userInfoDto = taskManager.executeTask(context, userInfoTask, userInfoMarshaller);
    return ResponseEntity.ok(userInfoDto);
  }

  @GetMapping("/login")
  public ResponseEntity<LoginUrlDto> getLoginUrl() {
    final Context context = currentContext.getContext();

    final LoginUrlDto loginUrlDto = taskManager.executeTask(context, loginUrlTask).getValue();
    return ResponseEntity.ok(loginUrlDto);
  }

  @GetMapping("/logout")
  public ResponseEntity<LogoutUrlDto> getLogoutUrl() {
    final Context context = currentContext.getContext();

    final LogoutUrlDto loginUrlDto = taskManager.executeTask(context, logoutUrlTask).getValue();
    return ResponseEntity.ok(loginUrlDto);
  }

}
