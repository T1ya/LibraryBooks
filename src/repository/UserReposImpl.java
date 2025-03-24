package repository;

import model.Role;
import model.User;
import ulils.MyArrayList;
import ulils.MyList;

public class UserReposImpl implements UserRepos {
    private final MyList<User> users;


    public UserReposImpl() {
        this.users = new MyArrayList<>();
        addUsers();
    }

    private void addUsers() {
        User admin = new User("admin@gmail.com","12345@Az");
        User user = new User("user@gmail.com","12345@Az");
        admin.setRole(Role.ADMIN);
        user.setRole(Role.USER);
        users.addAll(admin, user);
    }

    @Override
    public User addUser(String email, String password) {
       User user = new User(email,password);
       users.add(user);
       return user;
    }

    @Override
    public boolean isEmailExist(String email) {
        for (User u1 : users) {
            if (u1.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        for (User u1 : users) {
            if (u1.getEmail().equals(email)) {
                return u1;
            }
        }
        return null;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        for (User u1 : users) {
            if (u1.getEmail().equals(email)) {
                u1.setPassword(newPassword);
                return true;
            }
        }
        return false;
    }
}
