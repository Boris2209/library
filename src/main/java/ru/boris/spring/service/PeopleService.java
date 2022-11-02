package ru.boris.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.boris.spring.models.Book;
import ru.boris.spring.models.Person;
import ru.boris.spring.repositories.PeopleRepositories;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepositories peopleRepositories;

    private final BooksService booksService;

    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories, BooksService booksService) {
        this.peopleRepositories = peopleRepositories;
        this.booksService = booksService;
    }

    public List<Person> findAll() {
        return peopleRepositories.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepositories.findById(id);
        if (foundPerson.isPresent()){
            for (Book book : foundPerson.get().getBooks()){
                booksService.toOverdue(book);
            }
        }
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
