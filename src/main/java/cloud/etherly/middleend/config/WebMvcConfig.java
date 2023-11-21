package cloud.etherly.middleend.config;

import cloud.etherly.middleend.business.engine.EngineInterceptor;
import cloud.etherly.middleend.business.interceptor.AuthInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

  private final EngineInterceptor engineInterceptor;
  private final AuthInterceptor authInterceptor;

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .maxAge(3600);
  }

  @Override
  public void addInterceptors(final InterceptorRegistry registry) {
    registry.addInterceptor(engineInterceptor);
    registry.addInterceptor(authInterceptor).excludePathPatterns("/v1/authorize/**");
  }

}
