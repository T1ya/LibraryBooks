package repository;

import model.User;

public interface UserRepos {

    User addUser(String email, String password);

    // Read

    boolean isEmailExist(String email);

    User getUserByEmail(String email);

    // Update
    boolean updatePassword(String email, String newPassword);
}
