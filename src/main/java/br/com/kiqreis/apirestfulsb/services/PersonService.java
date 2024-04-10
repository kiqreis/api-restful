package br.com.kiqreis.apirestfulsb.services;

import br.com.kiqreis.apirestfulsb.controllers.PersonController;
import br.com.kiqreis.apirestfulsb.dtos.PersonDto;
import br.com.kiqreis.apirestfulsb.mapper.PersonMapper;
import br.com.kiqreis.apirestfulsb.models.Person;
import br.com.kiqreis.apirestfulsb.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    public Person save(PersonDto personDto) {
        return personRepository.save(PersonMapper.INSTANCE.toPerson(personDto));
    }

    public Person findById(UUID id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person id not found"));

        person.add(linkTo(PersonController.class).withRel("People list"));

        return person;
    }

    public Page<Person> findAll(Pageable pageable) {
        Page<Person> personPage = personRepository.findAll(pageable);

        if (!personPage.isEmpty()) {
            for (Person person : personPage.getContent()) {
                person.add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel());
            }
        }

        return personPage;
    }

    @Transactional(rollbackFor = Exception.class)
    public Person update(UUID id, PersonDto personDto) {
        Person personUpdate = findById(id);

        Person person = PersonMapper.INSTANCE.toPerson(personDto);
        person.setId(personUpdate.getId());

        return personRepository.save(person);
    }

    public void deleteById(UUID id) {
        Person person = findById(id);
        personRepository.deleteById(person.getId());
    }
}
