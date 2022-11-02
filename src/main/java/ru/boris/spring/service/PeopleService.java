package ru.boris.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.boris.spring.models.Person;
import ru.boris.spring.repositories.PeopleRepositories;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private PeopleRepositories peopleRepositories;

    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories) {
        this.peopleRepositories = peopleRepositories;
    }

    public List<Person> findAll() {
        return peopleRepositories.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepositories.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepositories.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setPersonId(id);
        peopleRepositories.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepositories.deleteById(id);
    }
}
