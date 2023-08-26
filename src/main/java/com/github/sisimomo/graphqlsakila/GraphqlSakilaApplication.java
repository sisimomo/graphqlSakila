package com.github.sisimomo.graphqlsakila;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class)
public class GraphqlSakilaApplication {

  public static void main(String[] args) {
    SpringApplication.run(GraphqlSakilaApplication.class, args);
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(@NotNull final CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(CorsConfiguration.ALL).allowedHeaders(CorsConfiguration.ALL)
            .allowedMethods(CorsConfiguration.ALL);
      }

    };
  }

}
