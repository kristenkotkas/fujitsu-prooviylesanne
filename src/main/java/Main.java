import Renting.*;

public class Main {

    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Film matrix11 = new Film("Matrix 11", FilmType.NEW_RELEASE);
        Film spiderMan = new Film("Spider Man", FilmType.REGULAR_FILM);
        Film spiderMan2 = new Film("Spider Man 2", FilmType.REGULAR_FILM);
        Film outOfAfrica = new Film("Out of Africa", FilmType.OLD_FILM);
        Film avatar = new Film("Avatar", FilmType.NEW_RELEASE);

        inventory.addFilms(matrix11, spiderMan, spiderMan2, outOfAfrica, avatar);

        Customer customer = new Customer();
        customer.setBonus(25);

        customer.rentFilm(matrix11, 1, true);
        customer.rentFilm(spiderMan, 5);
        customer.rentFilm(spiderMan2, 2);
        customer.rentFilm(outOfAfrica, 7);

        customer.printReceipt();

        customer.returnFilm(matrix11, 1);
        customer.returnFilm(spiderMan2, 2);

        customer.printReturningReceipt();
    }
}
