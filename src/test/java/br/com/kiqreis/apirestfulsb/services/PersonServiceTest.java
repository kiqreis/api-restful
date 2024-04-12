package br.com.kiqreis.apirestfulsb.services;

import br.com.kiqreis.apirestfulsb.models.Person;
import br.com.kiqreis.apirestfulsb.repositories.PersonRepository;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;


    @BeforeEach
    void setUp() {
        PageImpl<Person> personPage = new PageImpl<>(List.of(PersonCreator.personUsage()));

        BDDMockito.when(personRepository.findAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(personPage);

        BDDMockito.when(personRepository.findById(ArgumentMatchers.any(UUID.class)))
                .thenReturn(Optional.of(PersonCreator.personUsage()));

        BDDMockito.when(personRepository.save(ArgumentMatchers.any(Person.class)))
                .thenReturn(PersonCreator.personUsage());
    }

    @Test
    @DisplayName("findAll should return a page of people when successful")
    void findAllShouldReturnAPageOfPeopleWhenSuccessful() {
        String expectedName = PersonCreator.personUsage().getName();

        Page<Person> personPage = personService.findAll(Pageable.ofSize(1));

        Assertions.assertThat(personPage).isNotNull().isNotEmpty();

        Assertions.assertThat(personPage.toList()).isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(personPage.toList().getFirst().getName())
                .isNotBlank()
                .isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById should return a person when successful")
    void findByIdShouldReturnAPersonWhenSuccessful() {
        UUID expectedId = PersonCreator.personUsage().getId();

        Person person = personService.findById(UUID.randomUUID());

        Assertions.assertThat(person).isNotNull();

        Assertions.assertThat(person.getId()).isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("save should return a person when the save is successful")
    void saveShouldReturnAPersonWhenSuccessful() {
        Person person = personService.save(PersonDtoCreator.personDtoSave());

        Assertions.assertThat(person.getName()).isNotBlank()
                .isEqualTo(PersonCreator.personUsage().getName());

        Assertions.assertThat(person.getEmail()).isNotBlank()
                .isEqualTo(PersonCreator.personUsage().getEmail());

        Assertions.assertThat(person).isNotNull()
                .isEqualTo(PersonCreator.personUsage());
    }

    @Test
    @DisplayName("update should return a person when the update is successful")
    void updateShouldReturnAPersonWhenSuccessful() {
        Assertions.assertThatCode(() -> personService.update(UUID.randomUUID(), PersonDtoCreator.personUsageDtoToBeUpdated()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete should removes person when successful")
    void shouldRemovesPersonWhenSuccessful() {
        Assertions.assertThatCode(() -> personService.deleteById(UUID.randomUUID()))
                .doesNotThrowAnyException();
    }
}