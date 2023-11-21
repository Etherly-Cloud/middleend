package cloud.etherly.middleend.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationScopeStatusType {

  @JsonProperty("deploying")
  DEPLOYING("deploying"),

  @JsonProperty("finish")
  FINISH("finish"),

  @JsonProperty("effective")
  EFFECTIVE("effective"),

  @JsonProperty("rollback")
  ROLLBACK("rollback"),

  @JsonProperty("inactive")
  INACTIVE("inactive");

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
