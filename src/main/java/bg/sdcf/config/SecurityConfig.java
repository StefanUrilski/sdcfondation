package bg.sdcf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
      http
            .cors()
               .disable()
           .csrf()
              .disable()
            .authorizeRequests()
               .antMatchers("/**").permitAll()
               .anyRequest().permitAll()
            .and()
            .formLogin()
               .loginPage("/users/login")
               .usernameParameter("email")
               .passwordParameter("password")
               .defaultSuccessUrl("/")
            .and()
            .logout()
               .logoutUrl("/logout")
               .logoutSuccessUrl("/")
            .and()
            .exceptionHandling()
            .accessDeniedPage("/error/unauthorized")
      ;
   }

}
