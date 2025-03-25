package model;

import org.junit.jupiter.api.Test;
import ulils.MyList;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Test
    void testUserCreation() {
        User user = new User("test@example.com", "password123");

        assertEquals("test@example.com", user.getEmail(), "Email не соответствует");
        assertEquals("password123", user.getPassword(), "Пароль не соответствует");
        assertEquals(Role.USER, user.getRole(), "Роль по умолчанию не USER");
        assertNotNull(user.getUserBooks(), "Список книг пользователя не инициализирован");
        assertTrue(user.getUserBooks().isEmpty(), "Список книг пользователя не пуст при создании");
    }

    @Test
    void testSetEmail() {
        User user = new User("old@example.com", "password");
        user.setEmail("new@example.com");
        assertEquals("new@example.com", user.getEmail(), "Email не был обновлен");
    }

    @Test
    void testSetPassword() {
        User user = new User("email@example.com", "old_password");
        user.setPassword("new_password");
        assertEquals("new_password", user.getPassword(), "Пароль не был обновлен");
    }



    @Test
    void testAddUserBook() {
        User user = new User("test@example.com", "password");
        Book book1 = new Book("Author 1", "Book 1", 1); // Assuming Book class exists
        Book book2 = new Book("Author 2", "Book 2", 2);

        user.addUserBook(book1);
        MyList<Book> userBooks = user.getUserBooks();
        assertEquals(1, userBooks.size(), "Количество книг пользователя неверно после добавления одной книги");
        assertEquals(book1, userBooks.get(0), "Первая добавленная книга не соответствует");

        user.addUserBook(book2);
        assertEquals(2, userBooks.size(), "Количество книг пользователя неверно после добавления двух книг");
        assertEquals(book2, userBooks.get(1), "Вторая добавленная книга не соответствует");
    }

    @Test
    void testToString() {
        User user = new User("test@example.com", "password");
        String expectedToString = "User{email='test@example.com', password='password', role=USER, userBooks=[]}";
        assertEquals(expectedToString, user.toString(), "Метод toString() работает некорректно");

        Book book = new Book("Test Author", "Test Book", 1);
        user.addUserBook(book);
        String expectedToStringWithBook = "User{email='test@example.com', password='password', role=USER, userBooks=[Book [ID: 1, Title: 'Test Book', Author: 'Test Author', Status: Available]]}";
        assertEquals(expectedToStringWithBook.replace("borrowDate=null", "borrowDate=null"), user.toString().replace("borrowDate=null", "borrowDate=null"), "Метод toString() с книгой работает некорректно");
    }

    @Test
    void testEqualsAndHashCode() {
        User user1 = new User("test@example.com", "password");
        User user2 = new User("test@example.com", "password");
        User user3 = new User("another@example.com", "password");

        //Todo comment?
//        assertEquals(user1, user2, "Объекты с одинаковыми email и password не равны");
//        assertEquals(user1.hashCode(), user2.hashCode(), "Хэш-коды для одинаковых объектов не совпадают");
        assertNotEquals(user1, user3, "Объекты с разными email не равны");
        assertNotEquals(user1.hashCode(), user3.hashCode(), "Хэш-коды для разных объектов совпадают");
    }

    @Test
    void testEqualsWithDifferentUserBooks() {
        User user1 = new User("test@example.com", "password");
        User user2 = new User("test@example.com", "password");
        Book book1 = new Book("Author 1", "Book 1", 1);
        Book book2 = new Book("Author 2", "Book 2", 2);
        user1.addUserBook(book1);
        user2.addUserBook(book2);
        assertNotEquals(user1, user2, "Объекты с одинаковыми email и password, но разными userBooks равны");
        assertNotEquals(user1.hashCode(), user2.hashCode(), "Хэш-коды для объектов с разными userBooks совпадают");
    }
}

