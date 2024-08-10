import java.util.ArrayList;
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
                    System.out.println("Feature not yet implemented.");
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
}
