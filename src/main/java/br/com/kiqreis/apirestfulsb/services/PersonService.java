package br.com.kiqreis.apirestfulsb.services;

import br.com.kiqreis.apirestfulsb.controllers.PersonController;
import br.com.kiqreis.apirestfulsb.dtos.PersonDto;
import br.com.kiqreis.apirestfulsb.mapper.PersonMapper;
import br.com.kiqreis.apirestfulsb.models.Person;
import br.com.kiqreis.apirestfulsb.repositories.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        person.add(linkTo(methodOn(PersonController.class).findAll()).withRel("People list"));

        return person;
    }

    public List<Person> findAll() {
        List<Person> personList = personRepository.findAll();

        if (!personList.isEmpty()) {
            for (Person person : personList) {
                person.add(linkTo(methodOn(PersonController.class).findById(person.getId())).withSelfRel());
            }
        }

        return personList;
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
