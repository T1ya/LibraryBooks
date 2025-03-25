package presentation;

import static org.junit.jupiter.api.Assertions.*;

import model.Book;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.MainService;
import service.MainServiceImpl;
import ulils.MyArrayList;
import ulils.MyList;
import ulils.Print;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


class MenuTest {

    private MainService service;
    private Menu menu;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream originalSystemOut = System.out;

    @BeforeEach
    void setUp() {
        // Создаем реальный экземпляр MainServiceImpl
        // Для этого нам понадобятся реальные реализации UserRepos и BookRepos
        service = new MainServiceImpl(new InMemoryUserRepos(), new InMemoryBookRepos());
        menu = new Menu(service);
        System.setOut(new PrintStream(outputStreamCaptor));

    }

    @AfterEach
    void tearDown() {
        System.setOut(originalSystemOut);
    }

    private void simulateUserInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

    @Test
    void testStart_ExitOption() {
        simulateUserInput("0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Exit."), "Должно быть сообщение о выходе");
    }

    @Test
    void testRegisterUser_ValidInput_Success() {
        simulateUserInput("1\ntest@example.com\npassword\n0\n");
        menu.start();
        User user = new User("test@example.com", "Anto@5646");
        assertEquals(user,service.registerUser("test@example.com", "Anto@5646"));
//        assertTrue(service.registerUser("test@example.com"), "Пользователь должен быть зарегистрирован");
//        assertTrue(Print.getOutput().contains("user test@example.com added into the system"), "Сообщение об успешной регистрации должно быть выведено");
    }

    @Test
    void testRegisterUser_InvalidEmail_Failure() {
        simulateUserInput("1\ninvalid-email\npassword\n0\n");
        menu.start();
        assertFalse(service.isEmailExist("invalid-email"), "Пользователь не должен быть зарегистрирован при неверном email");
        assertTrue(Print.getOutput().contains("invalid email"), "Сообщение об ошибке для неверного email должно быть выведено");
    }

    @Test
    void testRegisterUser_InvalidPassword_Failure() {
        simulateUserInput("1\ntest@example.com\nshort\n0\n");
        menu.start();
        assertFalse(service.isEmailExist("test@example.com"), "Пользователь не должен быть зарегистрирован при неверном пароле");
        assertTrue(Print.getOutput().contains("invalid password!"), "Сообщение об ошибке для неверного пароля должно быть выведено");
    }

    @Test
    void testRegisterUser_UserAlreadyExists_Failure() {
        simulateUserInput("1\n1\n1\n0\n"); // Предполагаем, что "1" уже зарегистрирован в InMemoryUserRepos
        menu.start();
        assertTrue(Print.getOutput().contains("user1is already in the system!"), "Сообщение об ошибке для существующего пользователя должно быть выведено");
    }

    @Test
    void testLoginUser_SuccessfulLogin() {
        simulateUserInput("2\n1\n1\n0\n"); // Предполагаем, что "1" и "1" - валидные данные в InMemoryUserRepos
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("hello,1!"), "Должно быть приветственное сообщение");
        assertNotNull(service.getActiveUser(), "Активный пользователь должен быть установлен");
    }

    @Test
    void testLoginUser_UserDoesNotExist_Failure() {
        simulateUserInput("2\nnonexistent@example.com\npassword\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Invalid email or password."), "Должно быть сообщение об ошибке");
        assertNull(service.getActiveUser(), "Активный пользователь должен оставаться null");
    }

    @Test
    void testLoginUser_PasswordDoesNotMatch_Failure() {
        simulateUserInput("2\n1\nwrong_password\n0\n"); // Предполагаем, что "1" существует, но пароль неверный
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Invalid email or password."), "Должно быть сообщение об ошибке");
        assertNull(service.getActiveUser(), "Активный пользователь должен оставаться null");
    }

    @Test
    void testLogoutUser() {
        // Сначала логинимся, затем пробуем выйти
        simulateUserInput("2\n1\n1\n3\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("User 1 logged out"), "Сообщение об успешном выходе должно быть выведено");
        assertNull(service.getActiveUser(), "Активный пользователь должен быть сброшен");
    }

    @Test
    void testLogoutUser_NoUserLoggedIn_NoAction() {
        simulateUserInput("3\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("There is no active user in system"), "Сообщение об отсутствии активного пользователя должно быть выведено");
    }

    @Test
    void testAddBook_LoggedInAdmin() {
        // Логинимся как администратор (предполагаем, что "1" - админ)
        simulateUserInput("2\n1\n1\n4\ntitle\nauthor\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Book titleadded to the library"), "Сообщение об успешном добавлении книги должно быть выведено");
        assertEquals(1, service.getAllBooks().size(), "Должна быть добавлена одна книга (если InMemoryBookRepos пуст)");
    }

    @Test
    void testAddBook_NotLoggedInUser() {
        simulateUserInput("4\ntitle\nauthor\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("You must be logged in to perform this action."), "Должно быть сообщение об ошибке, если пользователь не вошел");
        assertEquals(0, service.getAllBooks().size(), "Книга не должна быть добавлена");
    }

    @Test
    void testViewAllBooks_BooksAvailable() {
        // Добавляем книгу для теста
        ((MainServiceImpl) service).addBook("Test Title", "Test Author");
        simulateUserInput("5\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Books Catalog:"), "Должно быть сообщение о каталоге книг");
        assertTrue(outputStreamCaptor.toString().contains("\"Test Title\" by Test Author"), "Должно быть название книги");
    }

    @Test
    void testViewAllBooks_NoBooksAvailable() {
        simulateUserInput("5\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("No books available."), "Должно быть сообщение, если нет книг");
    }

    @Test
    void testSearchBook_ExistingBook() {
        // Добавляем книгу для поиска
        ((MainServiceImpl) service).addBook("Search Title", "Search Author");
        simulateUserInput("6\nSearch Title\nSearch Author\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("\"Search Title\" by Search Author"), "Должна быть информация о найденной книге");
    }

    @Test
    void testSearchBook_NonExistingBook() {
        simulateUserInput("6\nNonExistent\nBook\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Book was not found"), "Должно быть сообщение, если книга не найдена");
    }

    @Test
    void testBorrowBook_LoggedInUserAndBookAvailable() {
        // Логинимся, добавляем книгу, пытаемся взять
        simulateUserInput("2\n2\n2\n4\nBorrow Title\nBorrow Author\n7\nBorrow Title\nBorrow Author\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Book \"Borrow Title\" by Borrow Author is Busy now"), "Сообщение об изменении статуса должно быть выведено");
        User activeUser = service.getActiveUser();
        assertNotNull(activeUser);
        assertFalse(activeUser.getUserBooks().isEmpty());
    }

    @Test
    void testBorrowBook_NotLoggedInUser() {
        simulateUserInput("7\nBorrow Title\nBorrow Author\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("You must be logged in to perform this action."), "Должно быть сообщение об ошибке, если пользователь не вошел");
    }

    @Test
    void testReturnBook_LoggedInUserAndBookBorrowed() {
        // Логинимся, добавляем книгу, берем, пытаемся вернуть
        simulateUserInput("2\n2\n2\n4\nReturn Title\nReturn Author\n7\nReturn Title\nReturn Author\n8\nReturn Title\nReturn Author\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Book \"Return Title\" by Return Author is Avaliable now"), "Сообщение об изменении статуса должно быть выведено");
        User activeUser = service.getActiveUser();
        assertNotNull(activeUser);
        assertTrue(activeUser.getUserBooks().isEmpty());
    }

    @Test
    void testReturnBook_NotLoggedInUser() {
        simulateUserInput("8\nReturn Title\nReturn Author\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("You must be logged in to perform this action."), "Должно быть сообщение об ошибке, если пользователь не вошел");
    }

    @Test
    void testViewFreeBooks_BooksAvailable() {
        // Добавляем книгу, но не берем ее
        ((MainServiceImpl) service).addBook("Free Title", "Free Author");
        simulateUserInput("9\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Free Books:"), "Должно быть сообщение о свободных книгах");
        assertTrue(outputStreamCaptor.toString().contains("Book [ID: 1, Title: 'Free Title', Author: 'Free Author', Status: Available]"), "Должно быть название свободной книги");
    }

    @Test
    void testViewFreeBooks_NoBooksAvailable() {
        simulateUserInput("9\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("No free books available."), "Должно быть сообщение, если нет свободных книг");
    }

    @Test
    void testViewBorrowedBooks_BooksBorrowed() {
        // Логинимся, добавляем книгу, берем
        simulateUserInput("2\n2\n2\n4\nBorrowed Title\nBorrowed Author\n7\nBorrowed Title\nBorrowed Author\n10\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Books Currently Borrowed:"), "Должно быть сообщение о взятых книгах");
        assertTrue(outputStreamCaptor.toString().contains("Book [ID: 1, Title: 'Borrowed Title', Author: 'Borrowed Author', Status: Borrowed]"), "Должно быть название взятой книги");
    }

    @Test
    void testViewBorrowedBooks_NoBooksBorrowed() {
        simulateUserInput("10\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("No borrowed books currently."), "Должно быть сообщение, если нет взятых книг");
    }

    @Test
    void testWrongChoice() {
        simulateUserInput("15\n0\n");
        menu.start();
        assertTrue(outputStreamCaptor.toString().contains("Wrong choice. Please try again."), "Должно быть сообщение о неверном выборе");
    }

    // Реализации заглушек для UserRepos и BookRepos (InMemory)
    private static class InMemoryUserRepos implements repository.UserRepos {
        private final MyList<User> users = new MyArrayList<>();

        public InMemoryUserRepos() {
            User admin = new User("1", "1");
            admin.setRole(model.Role.ADMIN);
            users.add(admin);
            users.add(new User("2", "2"));
        }

        @Override
        public User addUser(String email, String password) {
            User user = new User(email, password);
            users.add(user);
            return user;
        }

        @Override
        public boolean isEmailExist(String email) {
            return users.stream().anyMatch(user -> user.getEmail().equals(email));
        }

        @Override
        public User getUserByEmail(String email) {
            return users.stream().filter(user -> user.getEmail().equals(email)).findFirst().orElse(null);
        }

        @Override
        public boolean updatePassword(String email, String newPassword) {
            User user = getUserByEmail(email);
            if (user != null) {
                user.setPassword(newPassword);
                return true;
            }
            return false;
        }
    }

    private static class InMemoryBookRepos implements repository.BookRepos {
        private final MyList<Book> books = new MyArrayList<>();
        private int nextId = 1;

        @Override
        public Book addBook(String title, String author) {
            Book book = new Book(author, title, nextId++);
            books.add(book);
            return book;
        }

        @Override
        public MyList<Book> getAllBooks() {
            return books;
        }

        @Override
        public Book getByTitle(String title) {
            return books.stream().filter(book -> book.getTitle().contains(title)).findFirst().orElse(null);
        }

        @Override
        public MyList<Book> getByAuthor(String author) {
            return new MyArrayList<>(books.stream().filter(book -> book.getAuthor().equals(author)).toList());
        }

        @Override
        public Book getById(int id) {
            return books.stream().filter(book -> book.getId() == id).findFirst().orElse(null);
        }

        @Override
        public Book findBook(String title, String author) {
            return books.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()) &&
                            book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                    .findFirst()
                    .orElse(null);
        }

        @Override
        public void deleteBookById(int id) {
            books.removeIf(book -> book.getId() == id);
        }
    }
}
