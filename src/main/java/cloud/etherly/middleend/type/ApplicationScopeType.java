package cloud.etherly.middleend.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationScopeType {

  @JsonProperty("production")
  PROD("production"),

  @JsonProperty("test")
  TEST("web_app");

  private final String value;

  public static ApplicationScopeType fromString(final String name) {
    for (final ApplicationScopeType type : ApplicationScopeType.values()) {
      if (type.getValue().equalsIgnoreCase(name)) {
        return type;
      }
    }
    return null;
  }

}
