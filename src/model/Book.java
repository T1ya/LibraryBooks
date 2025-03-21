package model;


import java.util.Objects;

public class Book {
    private final String author;
    private final String title;
    private final int id;

    private boolean isBusy;

    public Book(String author, String title, int id) {
        this.author = author;
        this.title = title;
        this.id = id;
        this.isBusy = false;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", id=" + id +
                ", isBusy=" + isBusy +
                '}';
    }
    //TODO I have questions about this realisation - Ilya
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return id == book.id && isBusy == book.isBusy && Objects.equals(author, book.author) && Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, id, isBusy);
    }
}
