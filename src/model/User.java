package model;

import ulils.MyArrayList;
import ulils.MyList;

import java.util.Objects;

public class User {
    private String email;
    private String password;

    private Role role;


    private MyList<Book> userBooks;

    public User(String string, String s) {
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role && Objects.equals(userBooks, user.userBooks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password, role, userBooks);
    }
}

