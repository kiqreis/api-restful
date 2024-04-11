package br.com.kiqreis.apirestfulsb.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serial;
import java.io.Serializable;

public class PersonDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    public PersonDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public static PersonDtoBuilder builder() {
        return new PersonDtoBuilder();
    }

    public static class PersonDtoBuilder {

        private String name;
        private String email;

        public PersonDtoBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public PersonDto build() {
            return new PersonDto(name, email);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

