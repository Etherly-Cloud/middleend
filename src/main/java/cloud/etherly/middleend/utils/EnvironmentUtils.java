package cloud.etherly.middleend.utils;

import org.springframework.stereotype.Component;

@Component
public class EnvironmentUtils {

  private boolean is(final Environment environment) {
    return environment.equals(getCurrentEnvironment());
  }

  public boolean isProd() {
    return is(Environment.PROD);
  }

  public boolean isTest() {
    return is(Environment.TEST);
  }

  public boolean isDev() {
    return is(Environment.DEV);
  }

  public boolean showStackTrace() {
    return isTest() || isDev();
  }

  public Environment getCurrentEnvironment() {
    return Environment.getEnvironment(System.getenv("ENV"));
  }

}
