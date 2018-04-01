package Renting;

import Data.Film;
import Utils.Prices;

public class Rental {
    private Film film;
    private int days;

    public Rental(Film film, int days) {
        this.film = film;
        this.days = days;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getRentalPrice() {
        return Prices.getPrice(film.getFilmType(), this.days);
    }

    public void printRentalInfo() {

    }
}
