package br.com.kiqreis.apirestfulsb.utils;

import br.com.kiqreis.apirestfulsb.dtos.PersonDto;

public class PersonDtoCreator {

    public static PersonDto personDtoSave() {
        return PersonDto.builder()
                .name("Jurandir")
                .email("jurandir@email.com")
                .build();
    }

    public static PersonDto personUsageDtoToBeUpdated() {
        return PersonDto.builder()
                .name(PersonCreator.personUsageToBeUpdated().getName())
                .email(PersonCreator.personUsageToBeUpdated().getEmail())
                .build();
    }
}
