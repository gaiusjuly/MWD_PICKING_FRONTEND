package com.mwd.picking;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
      //  System.err.println("addCorsMappings");
        //registry.addMapping("/api/**")
          //      .allowedOrigins("http://localhost:8081","http://localhost","http://192.168.1.2","http://192.168.1.2:8081");
       // registry
      //          .addMapping("/login/**")
      //          .allowedOrigins("http://192.168.1.2:8081");
    }
//  @Bean
//  public FilterRegistrationBean characterEncodingFilter() {
//    CharacterEncodingFilter filter = new CharacterEncodingFilter();
//          filter.setEncoding("UTF-8");
//          filter.setForceEncoding(true);
//      FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//      registrationBean.setFilter(filter);
//      registrationBean.addUrlPatterns("/*");
//      return registrationBean;
//  }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver bean = new InternalResourceViewResolver();

        bean.setViewClass(JstlView.class);
        bean.setPrefix("/WEB-INF/view/");
        bean.setSuffix(".jsp");

        return bean;
    }

}

