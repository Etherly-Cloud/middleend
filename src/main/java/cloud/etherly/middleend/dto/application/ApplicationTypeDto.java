package cloud.etherly.middleend.dto.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationTypeDto {

  private String pluralLabel;
  private String label;
  private String uri;
  private String description;

}
