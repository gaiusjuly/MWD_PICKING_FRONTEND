package com.mwd.picking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication
public class PickingApplication {
  private static final Logger logger = LoggerFactory.getLogger(PickingApplication.class);
  public static void main(String[] args) {
    SpringApplication.run(PickingApplication.class, args);
    logger.info("Hello Spring Boot");
  }
  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
    characterEncodingFilter.setForceEncoding(true);
    characterEncodingFilter.setEncoding("UTF-8");
    registrationBean.setFilter(characterEncodingFilter);
    return registrationBean;
  }
//  @Bean
//  public CharacterEncodingFilter characterEncodingFilter() {
//    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//    characterEncodingFilter.setEncoding("UTF=8");
//    characterEncodingFilter.setForceEncoding(true);
//    return characterEncodingFilter;
//  }
 /* @Bean
  protected CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }*/
}
