package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @Test
    void testBookCreation() {
        Book book = new Book("Test Author", "Test Title", 123);

        assertEquals("Test Author", book.getAuthor(), "Автор не соответствует");
        assertEquals("Test Title", book.getTitle(), "Название не соответствует");
        assertEquals(123, book.getId(), "ID не соответствует");
        assertFalse(book.isBusy(), "Книга должна быть доступна при создании");
    }

    @Test
    void testSetBusy() {
        Book book = new Book("Author", "Title", 456);
        book.setBusy(true);
        assertTrue(book.isBusy(), "Статус книги не был изменен на 'Borrowed'");
        book.setBusy(false);
        assertFalse(book.isBusy(), "Статус книги не был изменен на 'Available'");
    }

    @Test
    void testToString() {
        Book availableBook = new Book("Author A", "Title A", 1);
        String expectedAvailable = "Book [ID: 1, Title: 'Title A', Author: 'Author A', Status: Available]";
        assertEquals(expectedAvailable, availableBook.toString(), "Метод toString() для доступной книги работает некорректно");

        Book borrowedBook = new Book("Author B", "Title B", 2);
        borrowedBook.setBusy(true);
        String expectedBorrowed = "Book [ID: 2, Title: 'Title B', Author: 'Author B', Status: Borrowed]";
        assertEquals(expectedBorrowed, borrowedBook.toString(), "Метод toString() для взятой книги работает некорректно");
    }

    @Test
    void testGetInfo() {
        Book book = new Book("Some Author", "Another Title", 789);
        String expectedInfo = "\"Another Title\" by Some Author";
        assertEquals(expectedInfo, book.getInfo(), "Метод getInfo() работает некорректно");
    }

    @Test
    void testEqualsAndHashCode() {
        Book book1 = new Book("Same Author", "Same Title", 10);
        Book book2 = new Book("Same Author", "Same Title", 10);
        Book book3 = new Book("Different Author", "Different Title", 11);

        assertEquals(book1, book2, "Две книги с одинаковыми атрибутами должны быть равны");
        assertEquals(book1.hashCode(), book2.hashCode(), "Хэш-коды для равных книг должны совпадать");

        assertNotEquals(book1, book3, "Книги с разными атрибутами не должны быть равны");
        assertNotEquals(book1.hashCode(), book3.hashCode(), "Хэш-коды для неравных книг должны отличаться");

        Book book4 = new Book("Same Author", "Same Title", 10);
        book4.setBusy(true);
        assertNotEquals(book1, book4, "Книги с одинаковыми основными атрибутами, но разным статусом не должны быть равны");
        assertNotEquals(book1.hashCode(), book4.hashCode(), "Хэш-коды для книг с разным статусом должны отличаться");
    }
}

