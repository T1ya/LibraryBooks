package model;

public class User {

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