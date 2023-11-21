package cloud.etherly.middleend.config;

import cloud.etherly.middleend.business.Metadata;
import cloud.etherly.middleend.client.kvs.KvsClient;
import cloud.etherly.middleend.client.kvs.KvsClientMock;
import cloud.etherly.middleend.dto.application.ApplicationDto;
import cloud.etherly.middleend.utils.EnvironmentUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class ApplicationModule {

  @Bean
  public KvsClient<ApplicationDto> provideApplicationKvsClient(final EnvironmentUtils environmentUtils) {
    final String environmentName = environmentUtils.getCurrentEnvironment().getName();
    final String prefix = String.format("%s:001:%s:", environmentName, "application");

    return new KvsClientMock<>(prefix);
  }

  @Bean
  public ObjectMapper provideObjectMapper(final Jackson2ObjectMapperBuilder builder) {
    return builder
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .build().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
  }

  @Bean
  public Metadata provideMetadata(final ObjectMapper objectMapper) throws IOException {
    final String metadataString = IOUtils.resourceToString("metadata.json", StandardCharsets.UTF_8, getClass().getClassLoader());

    return objectMapper.readValue(metadataString, Metadata.class);
  }

  @Bean
  public OkHttpClient provideOkHttpClient() {
    return new OkHttpClient();
  }

}
