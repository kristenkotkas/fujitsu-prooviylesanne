import Data.Customer;
import Data.Film;
import Data.Inventory;
import Utils.FilmType;

public class Main {
    private static Inventory inventory = new Inventory();
    private static Film matrix11 = new Film("Matrix 11", FilmType.REGULAR_FILM);
    private static Film spiderMan = new Film("Spider Man", FilmType.REGULAR_FILM);
    private static Film spiderMan2 = new Film("Spider Man 2", FilmType.REGULAR_FILM);
    private static Film outOfAfrica = new Film("Out of Africa", FilmType.OLD_FILM);
    private static Film avatar = new Film("Avatar", FilmType.NEW_RELEASE);
    private static Customer customer = new Customer();


    public static void main(String[] args) {
        printTitle("Inventory functions");
        demonstrateInventoryFunctions();
        printTitle("Renting functions");
        rentingFunctions();
        printTitle("Returning functions");
        returningFunctions();
    }

    private static void printTitle(String title) {
        System.out.println();
        System.out.println("---" + title + "---");
    }

    private static void demonstrateInventoryFunctions() {
        // add films
        inventory.addFilm(matrix11);
        inventory.addFilms(avatar, spiderMan, spiderMan2, outOfAfrica);
        // change film type
        inventory.changeFilmType(matrix11, FilmType.NEW_RELEASE);
        // remove film
        inventory.removeFilm(spiderMan);
        // list all films
        System.out.println("All films in inventory:");
        inventory.getAllFilms().forEach(System.out::println);
    }

    private static void rentingFunctions() {
        // set points for demo
        customer.setBonus(50);
        System.out.println("Bonus points: " + customer.getBonus());
        // rent films
        customer.rentFilm(matrix11, 5); // 5 * 4 = 20 EUR | + 2 bonus
        customer.rentFilm(spiderMan2, 7); // 3 + 4 * 3 = 15 EUR | + 1 bonus
        // rent with bonus
        customer.rentFilm(avatar, 2, true); // - 50 bonus
        customer.printReceipt(); // Total 35 EUR and 3 Bonus points remaining.
        // get available films
        System.out.println("Available films in inventory:");
        inventory.getAvailableFilms().forEach(System.out::println);
    }

    private static void returningFunctions() {
        // return film
        customer.returnFilm(matrix11, 0);
        // return films late
        customer.returnFilm(spiderMan2, 3); // 3 * 3 = 9 EUR
        customer.returnFilm(avatar, 5); // 5 * 2 = 20 EUR
        customer.printReturningReceipt(); // Total 29 EUR
        // get available films
        System.out.println("\nAvailable films in inventory:");
        inventory.getAvailableFilms().forEach(System.out::println);
    }
}
