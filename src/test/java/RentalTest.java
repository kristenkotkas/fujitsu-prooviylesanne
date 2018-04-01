import Data.Customer;
import Data.Film;
import Data.Inventory;
import Exceptions.*;
import Utils.FilmType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RentalTest {
    private Inventory inventory;

    private Customer customer;

    private Film avatar;
    private Film matrix11;
    private Film spiderMan;
    private Film spiderMan2;
    private Film outOfAfrica;

    void initMovies() {
        this.avatar = new Film("Avatar", FilmType.REGULAR_FILM);
        this.matrix11 = new Film("Matrix 11", FilmType.NEW_RELEASE);
        this.spiderMan = new Film("Spider Man", FilmType.REGULAR_FILM);
        this.spiderMan2 = new Film("Spider Man 2", FilmType.REGULAR_FILM);
        this.outOfAfrica = new Film("Out of Africa", FilmType.OLD_FILM);
        this.customer = new Customer();
    }

    @BeforeEach
    void addFilmsToInventory() {
        initMovies();
        this.inventory = new Inventory();
        this.inventory.addFilms(matrix11, spiderMan, spiderMan2, outOfAfrica, avatar);
    }

    @Test
    void testMakingFilm() {
        assertEquals(avatar.getFilmTitle(), "Avatar");
    }

    @Test
    void testAddingSingleMovieToInventory() {
        inventory.addFilm(matrix11);
        assertTrue(inventory.getAllFilms().contains(matrix11));
    }

    @Test
    void testAddingMultipleMoviesToInventory() {
        inventory.addFilms(spiderMan, spiderMan2, outOfAfrica);
        assertAll(
                () -> assertTrue(inventory.getAllFilms().contains(spiderMan)),
                () -> assertTrue(inventory.getAllFilms().contains(spiderMan2)),
                () -> assertTrue(inventory.getAllFilms().contains(outOfAfrica))
        );
    }

    @Test
    void testCorrectFeeForRental() {
        assertEquals(customer.rentFilm(matrix11, 3).getRentalPrice(), 12);
    }

    @Test
    void testCorrectTotalRentalFee() {
        customer.rentFilm(spiderMan, 4); // 3 + 3 = 6 EUR
        customer.rentFilm(spiderMan2, 3); // 3 EUR
        assertEquals(customer.printReceipt(), 9);
    }

    @Test
    void testCorrectBonusWhenRenting() {
        customer.rentFilm(avatar, 5); // 1 point
        customer.rentFilm(matrix11, 2); // 2 points
        assertEquals(customer.getBonus(), 3);
    }

    @Test
    void testRentingWithValidBonus() {
        customer.setBonus(30);
        customer.rentFilm(matrix11, 1, true);
        assertAll(
                () -> assertEquals(customer.printReceipt(), 0),
                () -> assertEquals(customer.getBonus(), 5)
        );
    }

    @Test
    void testReturningFilm() {
        customer.rentFilm(matrix11, 5);
        customer.printReceipt();

        assertTrue(!inventory.getAvailableFilms().contains(matrix11));
        customer.returnFilm(matrix11, 0);
        assertTrue(inventory.getAvailableFilms().contains(matrix11));
    }

    @Test
    void testZeroFeeAfterReturningAtCorrectTime() {
        customer.rentFilm(matrix11, 5);
        customer.printReceipt();

        assertEquals(customer.returnFilm(matrix11, 0).getRentalPrice(), 0);
        customer.printReturningReceipt();
    }

    @Test
    void testCorrectFeeAfterReturn() {
        customer.rentFilm(matrix11, 5);
        customer.printReceipt();

        assertEquals(customer.returnFilm(matrix11, 3).getRentalPrice(), 12);
        customer.printReturningReceipt();
    }

    @Test
    void testCorrectTotalFeeAfterReturnings() {
        customer.rentFilm(matrix11, 5);
        customer.rentFilm(avatar, 3);
        customer.rentFilm(spiderMan2, 2);
        customer.printReceipt();

        customer.returnFilm(matrix11, 1); // 1 * 4 = 4 EUR
        customer.returnFilm(avatar, 4); // 3 * 4 = 12 EUR
        customer.returnFilm(spiderMan2, 0); // 0 EUR
        assertEquals(customer.printReturningReceipt(), 16);
    }

    @Test
    void testRemoveFilmFromInventory() {
        assertTrue(inventory.getAllFilms().contains(avatar));
        inventory.removeFilm(avatar);
        assertTrue(!inventory.getAllFilms().contains(avatar));
    }

    @Test
    void testChangeFilmType() {
        assertEquals(avatar.getFilmType(), FilmType.REGULAR_FILM);
        inventory.changeFilmType(avatar, FilmType.OLD_FILM);
        assertEquals(avatar.getFilmType(), FilmType.OLD_FILM);
    }

    @Test
    void testCanRentOnlyAvailableFilm() {
        customer.rentFilm(avatar, 10);
        customer.printReceipt();
        assertThrows(FilmException.class, () -> customer.rentFilm(avatar, 10));
    }

    @Test
    void testNoReceiptIfNotRented() {
        assertThrows(RentingException.class, () -> customer.printReceipt());
    }

    @Test
    void testCanRentWithBonusIfEnoughBonus() {
        customer.setBonus(15);
        assertThrows(RentingException.class, () -> customer.rentFilm(matrix11, 1, true));
        customer.setBonus(35);
        assertThrows(RentingException.class, () -> customer.rentFilm(matrix11, 2, true));
    }

    @Test
    void testCanRentWithBonusOnlyNewMovie() {
        customer.setBonus(30);
        assertThrows(RentingException.class, () -> customer.rentFilm(avatar, 1, true));
    }
}