package com.timurka.firstSecurity.controller;

import com.timurka.firstSecurity.Service;
import com.timurka.firstSecurity.entity.Person;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author Stanislav Rakitov
 */
@RestController
public class Controller {
    private final Service personService;

    public PersonSecurityController(Service personService) {
        this.personService = personService;
    }

    @GetMapping("/persons/by-city")
    @Secured({"ROLE_READ"})
    public List<Person> getPersonsByCity(@RequestParam(value = "city", required = false) String city) {
        return personService.getPersonsByCity(city);
    }


    @GetMapping("/persons/by-age")
    @PreAuthorize("hasAnyAuthority('read')")
    public List<Person> getPersonsByAgeLess(@RequestParam(value = "age", required = false) int age) {
        return personService.getPersonsByAgeLess(age);
    }

    @PostMapping("/persons/create")
    @RolesAllowed({"ROLE_WRITE"})
    public Person createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }


    @DeleteMapping("/persons/delete")
    @PreAuthorize("hasRole('ROLE_WRITE') or hasRole('ROLE_DELETE')")
    public String deletePerson(@RequestBody Person person) {
        personService.deletePerson(person);
        return "Person with " + person.getContact() + " was deleted";
    }

    @GetMapping("/persons/lk")
    @PreAuthorize("#username == authentication.principal.username")
    public String greetingUser(String username) {
        return "Hello " + username + " from secure app!" ;
    }
}