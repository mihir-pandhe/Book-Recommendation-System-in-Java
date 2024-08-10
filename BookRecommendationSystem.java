import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Book {
    String title;
    String author;
    String genre;
    double rating;

    Book(String title, String author, String genre, double rating) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Rating: " + rating;
    }
}

public class BookRecommendationSystem {

    private static final List<Book> books = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    viewBooks();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public static void showMenu() {
        System.out.println("\n--- Book Recommendation System ---");
        System.out.println("1. Add Book");
        System.out.println("2. View Books");
        System.out.println("3. Exit");
        System.out.print("Select an option: ");
    }

    public static void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter book author: ");
        String author = scanner.nextLine();
        System.out.print("Enter book genre: ");
        String genre = scanner.nextLine();
        System.out.print("Enter book rating (0.0 - 5.0): ");
        double rating = scanner.nextDouble();
        scanner.nextLine();

        books.add(new Book(title, author, genre, rating));
        System.out.println("Book added successfully.");
    }

    public static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        System.out.println("\nChoose sorting option:");
        System.out.println("1. Sort by Title");
        System.out.println("2. Sort by Author");
        System.out.println("3. Sort by Rating");
        System.out.print("Select an option: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        switch (sortChoice) {
            case 1:
                Collections.sort(books, Comparator.comparing(book -> book.title));
                break;
            case 2:
                Collections.sort(books, Comparator.comparing(book -> book.author));
                break;
            case 3:
                Collections.sort(books, Comparator.comparing(book -> book.rating));
                break;
            default:
                System.out.println("Invalid option. Displaying unsorted list.");
                break;
        }

        System.out.println("\n--- Book List ---");
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
