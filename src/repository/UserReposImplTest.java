package repository;

import model.Book;
import model.Role;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ulils.MyList;

import static org.junit.jupiter.api.Assertions.*;

class UserReposImplTest {

    private UserReposImpl userRepos;

    @BeforeEach
    void setUp() {
        userRepos = new UserReposImpl();
    }

    @Test
    void testAddUser() {
        User newUser = userRepos.addUser("test@example.com", "new_password");
        assertNotNull(newUser, "Новый пользователь не должен быть null");
        assertEquals("test@example.com", newUser.getEmail(), "Email нового пользователя не соответствует");
        assertEquals("new_password", newUser.getPassword(), "Пароль нового пользователя не соответствует");
        assertTrue(userRepos.isEmailExist("test@example.com"), "Email нового пользователя не найден");

    }



    @Test
    void testIsEmailExist_ExistingEmail() {
        assertTrue(userRepos.isEmailExist("1"), "Существующий email должен быть найден");
        assertTrue(userRepos.isEmailExist("2"), "Существующий email должен быть найден");
    }

    @Test
    void testIsEmailExist_NonExistingEmail() {
        assertFalse(userRepos.isEmailExist("nonexistent@example.com"), "Несуществующий email не должен быть найден");
    }

    @Test
    void testGetUserByEmail_ExistingEmail() {
        User foundUser = userRepos.getUserByEmail("1");
        assertNotNull(foundUser, "Пользователь с существующим email не найден");
        assertEquals("1", foundUser.getPassword(), "Пароль найденного пользователя не соответствует");
        assertEquals(Role.ADMIN, foundUser.getRole(), "Роль найденного пользователя не соответствует");
    }

    @Test
    void testGetUserByEmail_NonExistingEmail() {
        User foundUser = userRepos.getUserByEmail("nonexistent@example.com");
        assertNull(foundUser, "Для несуществующего email должен быть возвращен null");
    }

    @Test
    void testUpdatePassword_ExistingEmail() {
        assertTrue(userRepos.updatePassword("1", "new_admin_password"), "Пароль не был обновлен для существующего email");
        User updatedUser = userRepos.getUserByEmail("1");
        assertNotNull(updatedUser, "Обновленный пользователь не найден");
        assertEquals("new_admin_password", updatedUser.getPassword(), "Пароль не был обновлен корректно");
    }

    @Test
    void testUpdatePassword_NonExistingEmail() {
        assertFalse(userRepos.updatePassword("nonexistent@example.com", "new_password"), "Пароль был обновлен для несуществующего email");
    }

   
}

