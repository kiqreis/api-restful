package br.com.kiqreis.apirestfulsb.integration.controller;

import br.com.kiqreis.apirestfulsb.config.TestConfig;
import br.com.kiqreis.apirestfulsb.dtos.PersonDto;
import br.com.kiqreis.apirestfulsb.integration.testcontainers.AbstractIT;
import br.com.kiqreis.apirestfulsb.models.Person;
import br.com.kiqreis.apirestfulsb.utils.PersonCreator;
import br.com.kiqreis.apirestfulsb.utils.PersonDtoCreator;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.DeserializationFeature;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonControllerJsonIT extends AbstractIT {

  private static ObjectMapper objectMapper;
  private static RequestSpecification requestSpecification;
  private static final PersonDto personDto = PersonDtoCreator.personDtoSave();
  private static final Person person = PersonCreator.personUsage();

  @BeforeAll
  static void setUp() {
    objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  @Test
  @Order(1)
  void testCreatePersonJson() throws IOException {
    requestSpecification = new RequestSpecBuilder()
        .addHeader(TestConfig.HEADER_PARAM_ORIGIN, "http://localhost:8888")
        .setBasePath("/person")
        .setPort(TestConfig.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    String content = given().spec(requestSpecification)
        .contentType(TestConfig.CONTENT_TYPE_JSON)
        .body(personDto)
        .when()
        .post()
        .then()
        .statusCode(201)
        .extract()
        .body()
        .asString();

    Person savedPerson = objectMapper.readValue(content, Person.class);
    person.setId(savedPerson.getId());

    Assertions.assertThat(savedPerson).isNotNull();
    Assertions.assertThat(savedPerson.getId()).isInstanceOf(UUID.class).isNotNull();
    Assertions.assertThat(savedPerson.getName()).isNotBlank();
    Assertions.assertThat(savedPerson.getEmail()).isNotBlank();

    Assertions.assertThat(savedPerson.getName()).isEqualTo(personDto.getName());
    Assertions.assertThat(savedPerson.getEmail()).isEqualTo(personDto.getEmail());
  }

  @Test
  @Order(2)
  void testCreatePersonJsonWithInvalidOrigin() {
    requestSpecification = new RequestSpecBuilder()
        .addHeader(TestConfig.HEADER_PARAM_ORIGIN, "http://localhost:9999")
        .setBasePath("/person")
        .setPort(TestConfig.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    String content = given().spec(requestSpecification)
        .contentType(TestConfig.CONTENT_TYPE_JSON)
        .body(personDto)
        .when()
        .post()
        .then()
        .statusCode(403)
        .extract()
        .body()
        .asString();

    Assertions.assertThat(content).isNotBlank();

    Assertions.assertThat(content).contains("Invalid CORS request");

    Assertions.assertThat(content).isEqualTo("Invalid CORS request");
  }

  @Test
  @Order(3)
  void testFindById() throws IOException {
    requestSpecification = new RequestSpecBuilder()
        .addHeader(TestConfig.HEADER_PARAM_ORIGIN, "http://localhost:8888")
        .setBasePath("/person")
        .setPort(TestConfig.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    String content = given().spec(requestSpecification)
        .contentType(TestConfig.CONTENT_TYPE_JSON)
        .pathParam("id", person.getId())
        .when()
        .get("{id}")
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString();

    Person savedPerson = objectMapper.readValue(content, Person.class);

    Assertions.assertThat(savedPerson).isNotNull();
    Assertions.assertThat(savedPerson.getId()).isNotNull();
    Assertions.assertThat(savedPerson.getName()).isNotBlank();
    Assertions.assertThat(savedPerson.getEmail()).isNotBlank();

    Assertions.assertThat(savedPerson.getName()).isEqualTo(personDto.getName());
    Assertions.assertThat(savedPerson.getEmail()).isEqualTo(personDto.getEmail());
  }

  @Test
  @Order(4)
  void testFindByIdWithInvalidOrigin() {
    requestSpecification = new RequestSpecBuilder()
        .addHeader(TestConfig.HEADER_PARAM_ORIGIN, "http://localhost:9999")
        .setBasePath("/person")
        .setPort(TestConfig.SERVER_PORT)
        .addFilter(new RequestLoggingFilter(LogDetail.ALL))
        .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
        .build();

    String content = given().spec(requestSpecification)
        .contentType(TestConfig.CONTENT_TYPE_JSON)
        .pathParam("id", person.getId())
        .when()
        .get("{id}")
        .then()
        .statusCode(403)
        .extract()
        .body()
        .asString();

    Assertions.assertThat(content).isNotBlank();

    Assertions.assertThat(content).contains("Invalid CORS request");

    Assertions.assertThat(content).isEqualTo("Invalid CORS request");
  }
}
