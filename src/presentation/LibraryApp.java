package presentation;

import repository.BookReposImpl;
import repository.UserReposImpl;
import service.MainServiceImpl;

public class LibraryApp {
    public static void main(String[] args) {
        BookReposImpl bookRepos = new BookReposImpl();
        UserReposImpl userRepos = new UserReposImpl();

        MainServiceImpl mainService = new MainServiceImpl(userRepos, bookRepos);

        Menu menu = new Menu(mainService);
        menu.start();
    }
}
