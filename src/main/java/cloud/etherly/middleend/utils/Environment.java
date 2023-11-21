package cloud.etherly.middleend.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Environment {
  DEV("dev"),
  TEST("test"),
  PROD("prod");

  private final String name;

  public static Environment getEnvironment(final String name) {
    for (final Environment environment : Environment.values()) {
      if (environment.getName().equalsIgnoreCase(name)) {
        return environment;
      }
    }
    return DEV;
  }

}
