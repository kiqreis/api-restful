package br.com.kiqreis.apirestfulsb.controllers;

import br.com.kiqreis.apirestfulsb.dtos.PersonDto;
import br.com.kiqreis.apirestfulsb.models.Person;
import br.com.kiqreis.apirestfulsb.services.PersonService;
import br.com.kiqreis.apirestfulsb.utils.PersonCreator;
import br.com.kiqreis.apirestfulsb.utils.PersonDtoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @BeforeEach
    void setUp() {
        PageImpl<Person> personPage = new PageImpl<>(List.of(PersonCreator.personUsage()));

        BDDMockito.given(personService.findAll(ArgumentMatchers.any(Pageable.class)))
                .willReturn(personPage);

        BDDMockito.given(personService.findById(ArgumentMatchers.any(UUID.class)))
                .willReturn(PersonCreator.personUsage());

        BDDMockito.given(personService.save(ArgumentMatchers.any(PersonDto.class)))
                .willReturn(PersonCreator.personUsage());

        BDDMockito.given(personService.update(ArgumentMatchers.any(UUID.class), ArgumentMatchers.any(PersonDto.class)))
                .willReturn(PersonCreator.personUsageToBeUpdated());

        BDDMockito.willDoNothing().given(personService).deleteById(ArgumentMatchers.any(UUID.class));
    }

    @Test
    @DisplayName("findAll should return a page of people when successful")
    void findAllShouldReturnAPageOfPeopleWhenSuccessful() {
        String expectedName = PersonCreator.personUsage().getName();

        Page<Person> personPage = personController.findAll(Pageable.ofSize(1)).getBody();

        Assertions.assertThat(personPage).isNotNull();

        Assertions.assertThat(personPage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(personPage.toList().getFirst().getName()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById should return a person when successful")
    void findByIdShouldReturnAPersonWhenSuccessful() {
        UUID expectedId = PersonCreator.personUsage().getId();

        Person person = personController.findById(UUID.randomUUID()).getBody();

        Assertions.assertThat(person).isNotNull();

        Assertions.assertThat(person.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save should return a person when the save is successful")
    void saveShouldReturnAPersonWhenSuccessful() {
        Person person = personController.save(PersonDtoCreator.personDtoSave()).getBody();

        Assertions.assertThat(person.getName()).isNotBlank()
                .isEqualTo(PersonCreator.personSave().getName());

        Assertions.assertThat(person.getEmail()).isNotBlank()
                .isEqualTo(PersonCreator.personSave().getEmail());

        Assertions.assertThat(person).isNotNull()
                .isEqualTo(PersonCreator.personUsage());
    }

    @Test
    @DisplayName("update should return a person when the update is successful")
    void updateShouldReturnAPersonWhenSuccessful() {
        Person person = personController.update(UUID.randomUUID(), PersonDtoCreator.personUsageDtoToBeUpdated()).getBody();

        Assertions.assertThat(person).isNotNull()
                .isEqualTo(PersonCreator.personUsageToBeUpdated());

        Assertions.assertThat(person.getName()).isNotBlank()
                .isEqualTo(PersonCreator.personUsageToBeUpdated().getName());

        Assertions.assertThat(person.getEmail()).isNotBlank()
                .isEqualTo(PersonCreator.personUsageToBeUpdated().getEmail());

        Assertions.assertThatCode(() -> personController.update(UUID.randomUUID(), PersonDtoCreator.personUsageDtoToBeUpdated()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete should removes person when successful")
    void shouldRemovesPersonWhenSuccessful() {
        ResponseEntity<Void> voidResponseEntity = personController.deleteById(UUID.randomUUID());

        Assertions.assertThat(voidResponseEntity).isNotNull();

        Assertions.assertThat(voidResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(voidResponseEntity.getBody()).isNull();

        Assertions.assertThatCode(() -> personController.deleteById(UUID.randomUUID()))
                .doesNotThrowAnyException();
    }
}