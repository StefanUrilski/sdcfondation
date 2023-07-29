package bg.sdcf.config;

import bg.sdcf.web.interseptors.FaviconInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
   private final FaviconInterceptor faviconInterceptor;

   @Autowired
   public WebMvcConfig(FaviconInterceptor faviconInterceptor) {
      this.faviconInterceptor = faviconInterceptor;
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(faviconInterceptor);
   }

}
