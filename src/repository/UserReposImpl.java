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
        User admin = new User("1", "1");
        admin.setRole(Role.ADMIN);
        User user = new User("2", "2");
        user.setRole(Role.USER);

        System.out.println("Adding users...");
        users.addAll(admin, user);
        System.out.println("Users added: " + users.size());
    }

    @Override
    public User addUser(String email, String password) {
        User user = new User(email, password);
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
        for (User user : users) {
            if (email.equals(user.getEmail())) {
                return user;
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
