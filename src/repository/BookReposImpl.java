package repository;

import model.Book;
import ulils.MyArrayList;
import ulils.MyList;

import java.util.concurrent.atomic.AtomicInteger;

public class BookReposImpl implements BookRepos {
    private final MyList<Book> books;
    private final AtomicInteger currentId = new AtomicInteger(1);

    public BookReposImpl(MyList<Book> books) {
        this.books = new MyArrayList<>();
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
            if (b.getTitle().contains(title.toLowerCase())
                && b.getAuthor().contains(author.toLowerCase())) {
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
