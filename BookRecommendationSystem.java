import java.util.Scanner;

public class BookRecommendationSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Feature not yet implemented.");
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
}
