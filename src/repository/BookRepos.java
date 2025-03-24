package repository;

import model.Book;
import ulils.MyList;

public interface BookRepos {
    Book addBook(String title, String author);

    MyList<Book> getAllBooks();

    Book getByTitle(String title);

    MyList<Book> getByAuthor(String author);

    Book getById(int id);

    MyList<Book> getFreeBooks();

    void deleteBookById(int id);

}
