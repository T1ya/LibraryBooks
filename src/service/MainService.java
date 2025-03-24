package service;


import model.Book;
import model.User;
import ulils.MyList;

public interface MainService {
    //Work with Users
    User registerUser(String email , String password);
    User loginUser(String email , String password);
    User logoutUser();
    User deleteUser(String email);
    User blockUser(String email);
    User getActiveUser();
    //Work with Books
    Book addBook(String title , String author);
    Book giveBook(String title , String author);
    Book returnBook(Book book);
    Book deleteBook(int id);
    //Work with Book lists
    MyList<Book> getFreeBooks();
    MyList<Book> getBusyBooks();
    MyList<Book> getAllBooks();
    MyList<Book> getUserBooks();






}
