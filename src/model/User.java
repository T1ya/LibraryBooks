package model;

import ulils.MyArrayList;
import ulils.MyList;

import java.util.Objects;

public class User {
    private String email;
    private String password;

    private Role role;


    private MyList<Book> userBooks;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.role = Role.USER;
        this.userBooks = new MyArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public MyList<Book> getUserBooks() {
        return userBooks;
    }

    public void setUserBooks(MyList<Book> userBooks) {
        this.userBooks = userBooks;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", userBooks=" + userBooks +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role, userBooks);
    }
}

public interface UserRepository {

    // CRUD

    User addUser(String email, String password);

    // Read

    boolean isEmailExist(String email);

    User getUserByEmail(String email);

    // Update
    boolean updatePassword(String email, String newPassword);


}