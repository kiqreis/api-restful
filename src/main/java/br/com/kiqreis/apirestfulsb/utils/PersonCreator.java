package br.com.kiqreis.apirestfulsb.utils;

import br.com.kiqreis.apirestfulsb.models.Person;

import java.util.UUID;

public class PersonCreator {

    private static final UUID id = UUID.randomUUID();

    public static Person personSave() {
        return Person.builder()
                .name("Jurandir")
                .email("jurandir@email.com")
                .build();
    }

    public static Person personUsage() {
        return Person.builder()
                .id(id)
                .name("Jurandir")
                .email("jurandir@email.com")
                .build();
    }

    public static Person personUsageToBeUpdated() {
        return Person.builder()
                .id(id)
                .name("Jurema")
                .email("jurema@email.com")
                .build();
    }
}
