package br.com.kiqreis.apirestfulsb.controllers;

import br.com.kiqreis.apirestfulsb.dtos.PersonDto;
import br.com.kiqreis.apirestfulsb.models.Person;
import br.com.kiqreis.apirestfulsb.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/person")
@Tag(name = "People", description = "Endpoints for maneging people")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping(consumes = {"application/json", "application/xml", "application/x-yaml"},
            produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(summary = "Add a new person record to the DB", description = "Add a new person to the DB by passing a JSON, XML or YML file",
            tags = {"People"}, responses = {
            @ApiResponse(description = "Saved", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Person.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Person> save(@Valid @RequestBody PersonDto personDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.save(personDto));
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(summary = "Returns the search for a person by id", description = "Returns the search for a person by id", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Person.class))),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Person> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findById(id));
    }

    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(summary = "Return search for all people", description = "Return search for all people", tags = {"People"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Person.class))
                                    )
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
            })
    public ResponseEntity<Page<Person>> findAll(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.findAll(pageable));
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml", "application/x-yaml"},
            produces = {"application/json", "application/xml", "application/x-yaml"})
    @Operation(summary = "Update a person record", description = "updates a person record by passing a JSON, XML or YML file",
            tags = {"People"}, responses = {
            @ApiResponse(description = "Updated", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = Person.class))),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Person> update(@PathVariable("id") UUID id, @Valid @RequestBody PersonDto personDto) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.update(id, personDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person record to the DB", description = "Delete a person record to the DB",
            tags = {"People"}, responses = {
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
    })
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        personService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
