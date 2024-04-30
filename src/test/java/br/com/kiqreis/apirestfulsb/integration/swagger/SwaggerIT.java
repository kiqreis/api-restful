package br.com.kiqreis.apirestfulsb.integration.swagger;

import br.com.kiqreis.apirestfulsb.config.TestConfig;
import br.com.kiqreis.apirestfulsb.integration.testcontainers.AbstractIT;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIT extends AbstractIT {

  @Test
  void shouldDisplayUiPage() {
    String content = given().basePath("/swagger-ui/index.html")
        .port(TestConfig.SERVER_PORT)
        .when()
        .get()
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    Assertions.assertThat(content).contains("Swagger UI");
  }
}
