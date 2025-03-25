package repository;

import model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulils.MyList;


import static org.junit.jupiter.api.Assertions.*;

class BookReposImplTest {

    private BookReposImpl bookRepos;

    @BeforeEach
    void setUp() {
        bookRepos = new BookReposImpl();
    }

    @Test
    void testAddBook() {
        Book addedBook = bookRepos.addBook("New Title", "New Author");
        assertNotNull(addedBook, "Добавленная книга не должна быть null");
        assertEquals("New Title", addedBook.getTitle(), "Название добавленной книги не соответствует");
        assertEquals("New Author", addedBook.getAuthor(), "Автор добавленной книги не соответствует");
        assertTrue(bookRepos.getAllBooks().contains(addedBook), "Добавленная книга не найдена в списке всех книг");
        assertEquals(7, bookRepos.getAllBooks().size(), "Размер списка книг должен увеличиться на 1");
        assertEquals(7, addedBook.getId(), "ID добавленной книги должен быть следующим после начальных");
    }

    @Test
    void testGetAllBooks() {
        MyList<Book> allBooks = bookRepos.getAllBooks();
        assertNotNull(allBooks, "Список всех книг не должен быть null");
        assertEquals(6, allBooks.size(), "Количество книг в начальном списке не соответствует (должно быть 6)");

    }

    @Test
    void testGetByTitle_ExistingTitle() {
        Book foundBook = bookRepos.getByTitle("Catcher");
        assertNotNull(foundBook, "Книга по названию не найдена");
        assertEquals("The Catcher in the Rye", foundBook.getTitle(), "Название найденной книги не соответствует");
    }

    @Test
    void testGetByTitle_NonExistingTitle() {
        Book foundBook = bookRepos.getByTitle("NonExistent Title");
        assertNull(foundBook, "Должна быть возвращена null для несуществующего названия");
    }

    @Test
    void testGetByAuthor_ExistingAuthor() {
        MyList<Book> foundBooks = bookRepos.getByAuthor("Orwell");
        assertNotNull(foundBooks, "Книги по автору не найдены");
        assertEquals(1, foundBooks.size(), "Количество книг автора не соответствует");
        assertEquals("1984", foundBooks.get(0).getTitle(), "Название найденной книги не соответствует");
    }

    @Test
    void testGetByAuthor_NonExistingAuthor() {
        MyList<Book> foundBooks = bookRepos.getByAuthor("NonExistent Author");
        assertNotNull(foundBooks, "Должен быть возвращен пустой список, а не null");
        assertTrue(foundBooks.isEmpty(), "Список книг для несуществующего автора не пуст");
    }

    @Test
    void testGetById_ExistingId() {
        Book book = bookRepos.getAllBooks().get(0); // Получаем первую книгу для теста по ID
        Book foundBook = bookRepos.getById(book.getId());
        assertNotNull(foundBook, "Книга по ID не найдена");
        assertEquals(book, foundBook, "Найденная книга не соответствует ожидаемой");
    }

    @Test
    void testGetById_NonExistingId() {
        Book foundBook = bookRepos.getById(999);
        assertNull(foundBook, "Должна быть возвращена null для несуществующего ID");
    }

    @Test
    void testFindBook_ExistingTitleAndAuthor() {
        Book foundBook = bookRepos.findBook("Master", "Bulgakov");
        assertNotNull(foundBook, "Книга по названию и автору не найдена");
        assertEquals("The Master and Margarita", foundBook.getTitle(), "Название найденной книги не соответствует");
        assertEquals("M. Bulgakov", foundBook.getAuthor(), "Автор найденной книги не соответствует");
    }

    @Test
    void testFindBook_NonExistingTitleOrAuthor() {
        Book foundBook1 = bookRepos.findBook("NonExistent", "Bulgakov");
        assertNull(foundBook1, "Должна быть возвращена null для несуществующего названия");

        Book foundBook2 = bookRepos.findBook("Master", "NonExistent");
        assertNull(foundBook2, "Должна быть возвращена null для несуществующего автора");

        Book foundBook3 = bookRepos.findBook("NonExistent", "NonExistent");
        assertNull(foundBook3, "Должна быть возвращена null для несуществующего названия и автора");
    }
// Метод не добавлен
//    @Test
//    void testDeleteBookById_ExistingId() {
//        Book bookToDelete = bookRepos.getAllBooks().get(0);
//        int initialSize = bookRepos.getAllBooks().size();
//        bookRepos.deleteBookById(bookToDelete.getId());
//        assertEquals(initialSize - 1, bookRepos.getAllBooks().size(), "Размер списка книг не уменьшился после удаления");
//        assertNull(bookRepos.getById(bookToDelete.getId()), "Удаленная книга все еще найдена");
//    }

    @Test
    void testDeleteBookById_NonExistingId() {
        int initialSize = bookRepos.getAllBooks().size();
        bookRepos.deleteBookById(999);
        assertEquals(initialSize, bookRepos.getAllBooks().size(), "Размер списка книг изменился при попытке удалить несуществующую книгу");
    }
}

