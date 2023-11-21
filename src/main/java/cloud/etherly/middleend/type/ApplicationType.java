package cloud.etherly.middleend.type;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApplicationType {

  @JsonProperty("web_app")
  WEB_APP("web_app");

  private final String value;

  public static ApplicationType fromString(final String name) {
    for (final ApplicationType type : ApplicationType.values()) {
      if (type.getValue().equalsIgnoreCase(name)) {
        return type;
      }
    }
    return null;
  }

}
