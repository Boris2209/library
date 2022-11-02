package ru.boris.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.boris.spring.models.Book;
import ru.boris.spring.models.Person;
import ru.boris.spring.repositories.BooksRepositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {

    private final BooksRepositories booksRepositories;

    @Autowired
    public BooksService(BooksRepositories booksRepositories) {
        this.booksRepositories = booksRepositories;
    }

    public List<Book> findAll() {
        List<Book> books = booksRepositories.findAll();
        for (Book book : books)
            toOverdue(book);
        return books;
    }

    public Book findOne(int id) {
        Optional<Book> foundBook = booksRepositories.findById(id);
        foundBook.ifPresent(this::toOverdue);
        return foundBook.orElse(null);
    }
    public void toOverdue(Book book) {
        long tenDaysInMilliseconds = 864000000;
        Date createdDate = book.getCreateDate();
        Date nowDate = new Date();
        book.setOverdue((nowDate.getTime() - createdDate.getTime()) < tenDaysInMilliseconds);
    }

    @Transactional
    public void save(Book book) {
        book.setCreateDate(new Date());
        booksRepositories.save(book);
    }

    @Transactional
    public void update(int id, Book book) {
        Book oldBook = findOne(id);
        book.setOwner(oldBook.getOwner());
        book.setCreateDate(oldBook.getCreateDate());
        book.setBookId(id);
        booksRepositories.save(book);
    }

    @Transactional
    public void delete(int id) {
        booksRepositories.deleteById(id);
    }

}
