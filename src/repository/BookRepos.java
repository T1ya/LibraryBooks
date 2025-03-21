package repository;

import model.Book;
import ulils.MyList;

public interface BookRepos {
    Book addBook(String title , String author , int id);

    MyList<Book> getAllBooks();
    Book getByTitle (String title);
    Book getByAuthor (String author);
    Book getById (int id);
    MyList<Book> getFreeBooks ();

}
