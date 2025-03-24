package presentation;

import model.Book;
import service.MainService;
import ulils.MyList;
import ulils.Print;

import java.util.Scanner;

public class Menu {
    private final MainService service;

    public Menu(MainService service) {
        this.service = service;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWelcome to the Library!");
        while (true) {
            System.out.println("--- Menu ---");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Logout User");
            System.out.println("4. Add Book");
            System.out.println("5. View All Books");
            System.out.println("6. Search Book by Title/Author");
            System.out.println("7. Borrow a Book");
            System.out.println("8. Return a Book");
            System.out.println("9. View Free Books");
            System.out.println("10. View Borrowed Books");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loginUser(scanner);
                    break;
                case 3:
                    logoutUser();
                    break;
                case 4:
                    addBook(scanner);
                    break;
                case 5:
                    viewAllBooks();
                    break;
                case 6:
                    searchBook(scanner);
                    break;
                case 7:
                    borrowBook(scanner);
                    break;
                case 8:
                    returnBook(scanner);
                    break;
                case 9:
                    viewFreeBooks();
                    break;
                case 10:
                    viewBorrowedBooks();
                    break;
                case 0:
                    System.out.println("Exit.");
                    return; // Exit the program
                default:
                    System.out.println("Wrong choice. Please try again.");
                    break;
            }
            pause();
        }
    }

    private void registerUser(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        service.registerUser(email, password);
    }

    private void loginUser(Scanner scanner) {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        boolean success = service.loginUser(email, password);
        if (success) {
            Print.Success("hello," + email + "!");
        } else {
            Print.Error("Invalid email or password.");
        }
    }

    private void logoutUser() {
        service.logoutUser();
    }

    private void addBook(Scanner scanner) {
        if (!isUserLoggedIn()) return;

        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        service.addBook(title, author);
    }

    private void viewAllBooks() {
        MyList<Book> books = service.getAllBooks();
        if (books != null && !books.isEmpty()) {
            System.out.println("Books Catalog:");
            for (Book book : books) {
                System.out.println(book.getInfo());
            }
        } else {
            System.out.println("No books available.");
        }
    }

    private void searchBook(Scanner scanner) {
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        Book book = service.findBook(title, author);
    }

    private void borrowBook(Scanner scanner) {
        if (!isUserLoggedIn()) return;

        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        service.takeBook(title, author);
    }

    private void returnBook(Scanner scanner) {
        if (!isUserLoggedIn()) return;

        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        Book book = service.findBook(title, author);
        service.returnBook(book);
    }

    private void viewFreeBooks() {
        MyList<Book> freeBooks = service.getFreeBooks();
        if (freeBooks != null && !freeBooks.isEmpty()) {
            System.out.println("Free Books:");
            for (Book b : freeBooks) {
                System.out.println(b);
            }
        } else {
            System.out.println("No free books available.");
        }
    }

    private void viewBorrowedBooks() {
        MyList<Book> busyBooks = service.getBusyBooks();
        if (busyBooks != null && !busyBooks.isEmpty()) {
            System.out.println("Books Currently Borrowed:");
            for (Book b : busyBooks) {
                System.out.println(b);
            }
        } else {
            System.out.println("No borrowed books currently.");
        }
    }

    private boolean isUserLoggedIn() {
        if (service.getActiveUser() == null) {
            Print.Error("You must be logged in to perform this action.");
            return false;
        }
        return true;
    }

    private void pause() {
        System.out.println("\nPress any key to continue...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}
