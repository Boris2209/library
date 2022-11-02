package ru.boris.spring.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.boris.spring.models.Person;

@Repository
public interface PeopleRepositories extends JpaRepository<Person, Integer> {
}
