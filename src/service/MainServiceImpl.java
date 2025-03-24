package service;

import model.Book;
import model.Role;
import model.User;
import repository.BookRepos;
import repository.UserRepos;
import ulils.MyArrayList;
import ulils.MyList;
import ulils.Print;
import ulils.UserValidation;

public class MainServiceImpl implements MainService {
    private final UserRepos userRepos;
    private final BookRepos bookRepos;
    private User activeUser;

    public MainServiceImpl(UserRepos userRepos, BookRepos bookRepos) {
        this.userRepos = userRepos;
        this.bookRepos = bookRepos;
    }

    private boolean isAdmin() {
        //check user for the administration rights
        return activeUser != null && activeUser.getRole() == Role.ADMIN;
    }

    @Override
    public User registerUser(String email, String password) {
        // Check mail
        if (!UserValidation.isEmailValid(email)) {
            Print.Error("invalid email");
            return null;
        }
        //Check pass
        if (!UserValidation.passwordIsValid(password)) {
            Print.Error("invalid password!");
            return null;
        }
        // check if user is already in system
        if (userRepos.getUserByEmail(email) != null) {
            Print.Error("user" + email + "is already in the system!");
            return null;
        }
        //add user to repository
        Print.Success("user" + email + " added into the system");
        return userRepos.addUser(email, password);
    }

    @Override
    public boolean loginUser(String email, String password) {
        User user = userRepos.getUserByEmail(email);
        if (user == null) return false; // no user found in system
        if (user.getPassword().equals(password)) { //check password
            activeUser = user;
            Print.Success("user " + email + " logged in");
            return true;
        }
        return false;
    }

    @Override
    public void logoutUser() {
        if (activeUser != null) {
            Print.Success("User " + activeUser.getEmail() + " logged out");
            activeUser = null;
        } else Print.Error("There is no active user in system");
    }

    @Override
    public User blockUser(String email) {
        if (!isAdmin()) {
            Print.Error("Access denied! Only admins can block users.");
            return null;
        }

        if (activeUser.getEmail().equals(email)) {
            Print.Error("You cannot block yourself.");
            return null;
        }

        User user = userRepos.getUserByEmail(email);
        if (user != null) {
            user.setRole(Role.BLOCKED);
            Print.Success("User " + email + " blocked succesfully");
            return user;
        }
        Print.Error("There is no user" + email + " in the system");
        return null;
    }

    @Override
    public User getActiveUser() {
        return activeUser;
    }

    @Override
    public Book addBook(String title, String author) {
        if (!isAdmin()) {
            Print.Error("Access denied! Only admins can add new books.");
            return null;
        }
        //push user to repository
        Print.Success("Book " + title + "added to the library");
        return bookRepos.addBook(title, author);
    }

    @Override
    public Book takeBook(String title, String author) {
        Book book = bookRepos.findBook(title, author);
        if (book != null && !book.isBusy()) {
            //change status
            updateBookStatus(book.getId(), true);
            activeUser.addUserBook(book);
            return book;
        } else {
            Print.Error("Book is already taken or not found!");
        }
        return null;
    }

    @Override
    public Book returnBook(Book book) {
        if (activeUser == null) {
            Print.Error("Please log in to return a book.");
            return null;
        }
        if (activeUser.getUserBooks().contains(book)) {
            updateBookStatus(book.getId(), false);
            activeUser.getUserBooks().remove(book);
            return book;
        } else {
            Print.Error("This book was not borrowed by the user!");
        }
        return null;
    }

    @Override
    public Book findBook(String title, String author) {
        Book book = bookRepos.findBook(title, author);
        if (book != null) {
            Print.Success(book.toString());
        } else {
            Print.Error("Book was not found");
        }
        return book;
    }

    @Override
    public Book deleteBook(int id) {
        Book book = bookRepos.getById(id);
        if (book != null && !book.isBusy()) {
            bookRepos.deleteBookById(id);
            Print.Success(book.getInfo() + " was deleted from the library");
            return book;
        } else {
            Print.Error("Book is not found or currently borrowed by a user.");
        }
        return null;
    }

    @Override
    public void updateBookStatus(int id, boolean isBusy) {
        Book book = bookRepos.getById(id);
        if (book != null) {
            book.setBusy(isBusy);
            Print.Success(book.getInfo() + " is " + (isBusy ? "Busy" : "Avaliable") + " now");
        } else {
            Print.Error("Book was not found in the repository!");
        }
    }

    @Override
    public MyList<Book> getFreeBooks() {
        MyList<Book> freeBooks = new MyArrayList<>();
        for (Book book : bookRepos.getAllBooks()) {
            if (!book.isBusy()) {
                freeBooks.add(book);
            }
        }
        return freeBooks;
    }

    @Override
    public MyList<Book> getBusyBooks() {
        MyList<Book> busyBooks = new MyArrayList<>();
        for (Book book : bookRepos.getAllBooks()) {
            if (book.isBusy()) {
                busyBooks.add(book);
            }
        }
        return busyBooks;
    }

    @Override
    public MyList<Book> getAllBooks() {
        return bookRepos.getAllBooks();
    }

    @Override
    public MyList<Book> getUserBooks() {
        if (activeUser == null) {
            Print.Error("You must be logged in to view your books.");
            return null;
        }
        return activeUser.getUserBooks();
    }
}
