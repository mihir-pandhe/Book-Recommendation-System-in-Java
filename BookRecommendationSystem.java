import java.util.*;
import java.util.stream.Collectors;

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
    private static final Map<String, List<Book>> userPreferences = new HashMap<>();
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
                    searchBooks();
                    break;
                case 4:
                    recommendBooks();
                    break;
                case 5:
                    advancedRecommendations();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
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

        Book book = new Book(title, author, genre, rating);
        books.add(book);
        System.out.print("Enter your name to save your preferences: ");
        String userName = scanner.nextLine();
        userPreferences.computeIfAbsent(userName, k -> new ArrayList<>()).add(book);

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
                books.sort(Comparator.comparing(book -> book.title));
                break;
            case 2:
                books.sort(Comparator.comparing(book -> book.author));
                break;
            case 3:
                books.sort(Comparator.comparing(book -> book.rating));
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

    public static void searchBooks() {
        System.out.println("\nChoose search option:");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Genre");
        System.out.print("Select an option: ");
        int searchChoice = scanner.nextInt();
        scanner.nextLine();

        switch (searchChoice) {
            case 1:
                searchByTitle();
                break;
            case 2:
                searchByGenre();
                break;
            default:
                System.out.println("Invalid option. Returning to main menu.");
                break;
        }
    }

    public static void searchByTitle() {
        System.out.print("Enter title to search: ");
        String title = scanner.nextLine();
        boolean found = false;
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found with the title: " + title);
        }
    }

    public static void searchByGenre() {
        System.out.print("Enter genre to search: ");
        String genre = scanner.nextLine();
        boolean found = false;
        for (Book book : books) {
            if (book.genre.equalsIgnoreCase(genre)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found in the genre: " + genre);
        }
    }

    public static void recommendBooks() {
        System.out.println("\nChoose recommendation option:");
        System.out.println("1. Recommend by Genre");
        System.out.println("2. Recommend by Rating");
        System.out.print("Select an option: ");
        int recommendChoice = scanner.nextInt();
        scanner.nextLine();

        switch (recommendChoice) {
            case 1:
                recommendByGenre();
                break;
            case 2:
                recommendByRating();
                break;
            default:
                System.out.println("Invalid option. Returning to main menu.");
                break;
        }
    }

    public static void recommendByGenre() {
        System.out.print("Enter genre for recommendation: ");
        String genre = scanner.nextLine();
        boolean found = false;
        for (Book book : books) {
            if (book.genre.equalsIgnoreCase(genre)) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No recommendations available for the genre: " + genre);
        }
    }

    public static void recommendByRating() {
        System.out.print("Enter minimum rating for recommendation (0.0 - 5.0): ");
        double minRating = scanner.nextDouble();
        boolean found = false;
        for (Book book : books) {
            if (book.rating >= minRating) {
                System.out.println(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No recommendations available with a rating of " + minRating + " or higher.");
        }
    }

    public static void advancedRecommendations() {
        System.out.print("Enter your name to get personalized recommendations: ");
        String userName = scanner.nextLine();
        List<Book> preferences = userPreferences.get(userName);

        if (preferences == null || preferences.isEmpty()) {
            System.out.println("No preferences found for user: " + userName);
            return;
        }

        Set<Book> recommendedBooks = new HashSet<>();
        for (Book preference : preferences) {
            List<Book> similarBooks = books.stream()
                    .filter(book -> !book.title.equalsIgnoreCase(preference.title))
                    .filter(book -> book.genre.equalsIgnoreCase(preference.genre) || book.rating >= preference.rating)
                    .collect(Collectors.toList());
            recommendedBooks.addAll(similarBooks);
        }

        System.out.println("\n--- Personalized Recommendations ---");
        for (Book book : recommendedBooks) {
            System.out.println(book);
        }

        if (recommendedBooks.isEmpty()) {
            System.out.println("No personalized recommendations available.");
        }
    }

    public static void showMenu() {
        System.out.println("\n--- Book Recommendation System ---");
        System.out.println("1. Add Book");
        System.out.println("2. View Books");
        System.out.println("3. Search Books");
        System.out.println("4. Get Recommendations");
        System.out.println("5. Get Advanced Recommendations");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }

}
