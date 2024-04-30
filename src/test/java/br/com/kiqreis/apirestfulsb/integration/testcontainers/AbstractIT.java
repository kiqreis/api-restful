package br.com.kiqreis.apirestfulsb.integration.testcontainers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;

@ContextConfiguration(initializers = AbstractIT.Initializer.class)
public class AbstractIT {

  private static final MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8.3.0")
      .withDatabaseName("api_restful")
      .withUsername("root")
      .withPassword("root");

  @BeforeAll
  static void beforeAll() {
    mysql.start();
  }

  @AfterAll
  static void afterAll() {
    mysql.stop();
  }

  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues.of(
          "spring.datasource.url: " + mysql.getJdbcUrl(),
          "spring.datasource.username: " + mysql.getUsername(),
          "spring.datasource.password: " + mysql.getPassword()
      ).applyTo(configurableApplicationContext.getEnvironment());
     }
  }
}
