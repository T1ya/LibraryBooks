package service;


import model.Book;
import model.User;
import ulils.MyList;

public interface MainService {
    //Work with Users
    User registerUser(String email , String password);
    boolean loginUser(String email , String password);
    void logoutUser();
    User blockUser(String email);
    User getActiveUser();
    //Work with Books
    Book addBook(String title , String author);
    Book takeBook(String title , String author);
    Book findBook(String title , String author);
    Book returnBook(Book book);
    Book deleteBook(int id);
    void updateBookStatus(int id, boolean isBusy);
    //Work with Book lists
    MyList<Book> getFreeBooks();
    MyList<Book> getBusyBooks();
    MyList<Book> getAllBooks();
    MyList<Book> getUserBooks();
}
