import java.util.*;
import java.util.stream.Collectors;

class Book {
    private final String title;
    private final String author;
    private final String genre;
    private final double rating;

    public Book(String title, String author, String genre, double rating) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Genre: " + genre + ", Rating: " + rating;
    }
}

class UserPreferences {
    private final Map<String, List<Book>> preferences = new HashMap<>();

    public void addBookForUser(String userName, Book book) {
        preferences.computeIfAbsent(userName, k -> new ArrayList<>()).add(book);
    }

    public List<Book> getBooksForUser(String userName) {
        return preferences.getOrDefault(userName, new ArrayList<>());
    }
}

class BookManager {
    private final List<Book> books = new ArrayList<>();

    public void addBook(String title, String author, String genre, double rating) {
        books.add(new Book(title, author, genre, rating));
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<Book> getBooksSortedByTitle() {
        return books.stream().sorted(Comparator.comparing(Book::getTitle)).collect(Collectors.toList());
    }

    public List<Book> getBooksSortedByAuthor() {
        return books.stream().sorted(Comparator.comparing(Book::getAuthor)).collect(Collectors.toList());
    }

    public List<Book> getBooksSortedByRating() {
        return books.stream().sorted(Comparator.comparing(Book::getRating)).collect(Collectors.toList());
    }

    public List<Book> searchBooksByTitle(String title) {
        return books.stream().filter(book -> book.getTitle().equalsIgnoreCase(title)).collect(Collectors.toList());
    }

    public List<Book> searchBooksByGenre(String genre) {
        return books.stream().filter(book -> book.getGenre().equalsIgnoreCase(genre)).collect(Collectors.toList());
    }

    public List<Book> recommendBooksByGenre(String genre) {
        return books.stream().filter(book -> book.getGenre().equalsIgnoreCase(genre)).collect(Collectors.toList());
    }

    public List<Book> recommendBooksByRating(double minRating) {
        return books.stream().filter(book -> book.getRating() >= minRating).collect(Collectors.toList());
    }
}

class RecommendationEngine {
    public Set<Book> generatePersonalizedRecommendations(List<Book> preferences, List<Book> allBooks) {
        Set<Book> recommendedBooks = new HashSet<>();
        for (Book preference : preferences) {
            List<Book> similarBooks = allBooks.stream()
                    .filter(book -> !book.getTitle().equalsIgnoreCase(preference.getTitle()))
                    .filter(book -> book.getGenre().equalsIgnoreCase(preference.getGenre())
                            || book.getRating() >= preference.getRating())
                    .collect(Collectors.toList());
            recommendedBooks.addAll(similarBooks);
        }
        return recommendedBooks;
    }
}

public class BookRecommendationSystem {

    private static final BookManager bookManager = new BookManager();
    private static final UserPreferences userPreferences = new UserPreferences();
    private static final RecommendationEngine recommendationEngine = new RecommendationEngine();
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

        bookManager.addBook(title, author, genre, rating);
        System.out.print("Enter your name to save your preferences: ");
        String userName = scanner.nextLine();
        userPreferences.addBookForUser(userName, new Book(title, author, genre, rating));

        System.out.println("Book added successfully.");
    }

    public static void viewBooks() {
        if (bookManager.getBooks().isEmpty()) {
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

        List<Book> sortedBooks;
        switch (sortChoice) {
            case 1:
                sortedBooks = bookManager.getBooksSortedByTitle();
                break;
            case 2:
                sortedBooks = bookManager.getBooksSortedByAuthor();
                break;
            case 3:
                sortedBooks = bookManager.getBooksSortedByRating();
                break;
            default:
                System.out.println("Invalid option. Displaying unsorted list.");
                sortedBooks = bookManager.getBooks();
                break;
        }

        System.out.println("\n--- Book List ---");
        for (Book book : sortedBooks) {
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

        List<Book> searchResults;
        switch (searchChoice) {
            case 1:
                System.out.print("Enter title to search: ");
                String title = scanner.nextLine();
                searchResults = bookManager.searchBooksByTitle(title);
                break;
            case 2:
                System.out.print("Enter genre to search: ");
                String genre = scanner.nextLine();
                searchResults = bookManager.searchBooksByGenre(genre);
                break;
            default:
                System.out.println("Invalid option. Returning to main menu.");
                return;
        }

        displaySearchResults(searchResults);
    }

    public static void displaySearchResults(List<Book> searchResults) {
        if (searchResults.isEmpty()) {
            System.out.println("No matching books found.");
        } else {
            for (Book book : searchResults) {
                System.out.println(book);
            }
        }
    }

    public static void recommendBooks() {
        System.out.println("\nChoose recommendation option:");
        System.out.println("1. Recommend by Genre");
        System.out.println("2. Recommend by Rating");
        System.out.print("Select an option: ");
        int recommendChoice = scanner.nextInt();
        scanner.nextLine();

        List<Book> recommendations;
        switch (recommendChoice) {
            case 1:
                System.out.print("Enter genre for recommendation: ");
                String genre = scanner.nextLine();
                recommendations = bookManager.recommendBooksByGenre(genre);
                break;
            case 2:
                System.out.print("Enter minimum rating for recommendation (0.0 - 5.0): ");
                double minRating = scanner.nextDouble();
                recommendations = bookManager.recommendBooksByRating(minRating);
                break;
            default:
                System.out.println("Invalid option. Returning to main menu.");
                return;
        }

        displayRecommendations(recommendations);
    }

    public static void displayRecommendations(List<Book> recommendations) {
        if (recommendations.isEmpty()) {
            System.out.println("No recommendations available.");
        } else {
            for (Book book : recommendations) {
                System.out.println(book);
            }
        }
    }

    public static void advancedRecommendations() {
        System.out.print("Enter your name to get personalized recommendations: ");
        String userName = scanner.nextLine();
        List<Book> preferences = userPreferences.getBooksForUser(userName);

        if (preferences.isEmpty()) {
            System.out.println("No preferences found for this user.");
            return;
        }

        Set<Book> personalizedRecommendations = recommendationEngine.generatePersonalizedRecommendations(preferences,
                bookManager.getBooks());

        if (personalizedRecommendations.isEmpty()) {
            System.out.println("No personalized recommendations available.");
        } else {
            System.out.println("\n--- Personalized Recommendations ---");
            for (Book book : personalizedRecommendations) {
                System.out.println(book);
            }
        }
    }
}
