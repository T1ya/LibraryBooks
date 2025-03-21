package repository;

import model.User;
import ulils.MyArrayList;
import ulils.MyList;

public class UserReposImpl implements UserRepos {
    private final MyList<User> users;

    public UserReposImpl() {
        this.users = new MyArrayList<>();
    }


    @Override
    public User addUser(String email, String password) {
        return null;
    }

    @Override
    public boolean isEmailExist(String email) {
        return false;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public boolean updatePassword(String email, String newPassword) {
        return false;
    }
}
