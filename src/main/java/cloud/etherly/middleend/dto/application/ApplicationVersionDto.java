package cloud.etherly.middleend.dto.application;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationVersionDto {

  private final String id = UUID.randomUUID().toString();
  private String name;
  private String branch;

}
