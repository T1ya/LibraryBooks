package repository;

import model.Book;
import model.Role;
import model.User;
import ulils.MyArrayList;
import ulils.MyList;

import java.util.concurrent.atomic.AtomicInteger;

public class BookReposImpl implements BookRepos {
    private final MyList<Book> books;
    private final AtomicInteger currentId = new AtomicInteger(1);

    public BookReposImpl() {
        this.books = new MyArrayList<>();
        addBooks();
    }

    private void addBooks() {
        books.add(new Book("J.D. Salinger", "The Catcher in the Rye", currentId.getAndIncrement()));
        books.add(new Book("M. Bulgakov","The Master and Margarita", currentId.getAndIncrement()));
        books.add(new Book("George Orwell","1984",  currentId.getAndIncrement()));
        books.add(new Book("F. Nietzsche", "The Antichrist", currentId.getAndIncrement()));
        books.add(new Book( "G. Chaucer","The Canterbury Tales", currentId.getAndIncrement()));
        books.add(new Book( "test","test", currentId.getAndIncrement()));
    }

    @Override
    public Book addBook(String title, String author) {
        Book book = new Book(title, author, currentId.getAndIncrement());
        books.add(book);
        return book;
    }

    @Override
    public MyList<Book> getAllBooks() {
        return books;
    }

    @Override
    public Book getByTitle(String title) {
        for (Book b : books) {
            if (b.getTitle().contains(title)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public MyList<Book> getByAuthor(String author) {
        MyList<Book> result = new MyArrayList<>();
        for (Book b : books) {
            if (b.getAuthor().equals(author)) {
                result.add(b);
            }
        }
        return result;
    }

    @Override
    public Book getById(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                return b;
            }
        }
        return null;
    }

    @Override
    public Book findBook(String title, String author) {
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                b.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                return b;
            }
        }
        return null;
    }

    @Override
    public void deleteBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                books.remove(id);
            }
        }
    }
}
