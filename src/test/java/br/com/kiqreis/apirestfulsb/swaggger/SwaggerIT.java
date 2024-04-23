package br.com.kiqreis.apirestfulsb.swaggger;

import br.com.kiqreis.apirestfulsb.config.TestConfig;
import br.com.kiqreis.apirestfulsb.integration.testcontainers.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SwaggerIT extends AbstractIT {

    @Test
    public void shouldDisplaySwaggerUiPage() {
        var content = given()
                        .basePath("/swagger-ui/index.html")
                        .port(TestConfig.SERVER_PORT)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract()
                        .body()
                        .asString();
        assertTrue(content.contains("Swagger UI"));
    }
}
