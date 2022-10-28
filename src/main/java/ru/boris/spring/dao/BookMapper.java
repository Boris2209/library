package ru.boris.spring.dao;

import org.springframework.jdbc.core.RowMapper;
import ru.boris.spring.models.Book;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();


        for (int i = 1; i <= columnCount; i++ ) {
            String name = rsmd.getColumnName(i);
            System.out.println(name);
        }

        Book book = new Book();

        book.setBookId(rs.getInt("book_id"));
        //book.setPersonName(rs.getString("person.name"));
        book.setName(rs.getString("name"));
        book.setAuthor(rs.getString("author"));
        book.setYear(rs.getInt("year"));

        return book;
    }
}
