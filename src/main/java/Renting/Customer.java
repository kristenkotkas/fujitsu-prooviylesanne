package Renting;

import Exceptions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Customer {
    private int bonus;
    private List<Renting> activeCart;
    private List<Returning> activeReturnings;
    private List<Rental> currentlyRentedMovies;

    public Customer() {
        this.bonus = 0;
        this.activeCart = new ArrayList<>();
        this.activeReturnings = new ArrayList<>();
        this.currentlyRentedMovies = new ArrayList<>();
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    private void printBonusRemaining() {
        if (activeCart.stream().anyMatch(Renting::isPaidWithBonus)) {
            System.out.println();
            System.out.println("Remaining Bonus points: " + getBonus());
        }
    }

    public Renting rentFilm(Film film, int initialRentedDays) {
        if (!film.isRented()) {
            Renting renting = new Renting(film, initialRentedDays);
            this.activeCart.add(renting);
            if (film.getFilmType() == FilmType.NEW_RELEASE) {
                setBonus(getBonus() + 2);
            } else {
                setBonus(getBonus() + 1);
            }
            return renting;
        } else {
            throw new FilmException(film.getFilmTitle() + " is currently rented.");
        }
    }

    public Renting rentFilm(Film film, int initialDays, boolean payWithBonus) {
        if (payWithBonus) {
            if (film.getFilmType() != FilmType.NEW_RELEASE) {
                throw new RentingException("Film is not a new movie.");
            }
            if (getBonus() < 25 * initialDays) {
                throw new RentingException("Not enough bonus points.");
            }
            Renting renting = new Renting(film, initialDays, true);
            this.activeCart.add(renting);
            setBonus(getBonus() - 25 * initialDays);
            return renting;
        } else {
            return rentFilm(film, initialDays);
        }
    }

    private int getTotalPrice(List<? extends Rental> list) {
        return list.stream()
                .mapToInt(Rental::getRentalPrice)
                .sum();
    }

    public int printReceipt() {
        if (!this.activeCart.isEmpty()) {
            this.activeCart.forEach(Rental::printRentalInfo);
            int totalPrice = getTotalPrice(this.activeCart);
            System.out.println("Total price : " + totalPrice + " EUR");
            printBonusRemaining();
            System.out.println();
            this.currentlyRentedMovies.addAll(this.activeCart);
            this.activeCart.clear();
            return totalPrice;
        } else {
            throw new RentingException("No movies purchased.");
        }
    }

    public Returning returnFilm(Film film, int daysLate) {
        Optional<Rental> returningFilm = this.currentlyRentedMovies.stream()
                .filter(rental -> rental.getFilm().equals(film))
                .findFirst();
        if (returningFilm.isPresent()) {
            Returning returning = new Returning(returningFilm.get().getFilm(), daysLate);
            activeReturnings.add(returning);
            this.currentlyRentedMovies.remove(returningFilm.get());
            return returning;
        } else {
            throw new ReturningException(film.getFilmTitle() + " not rented by customer.");
        }
    }

    public int printReturningReceipt() {
        if (!this.activeReturnings.isEmpty()) {
            this.activeReturnings.forEach(Rental::printRentalInfo);
            int totalPrice = getTotalPrice(this.activeReturnings);
            System.out.println("Total late charge: " + totalPrice + " EUR");
            this.activeReturnings.clear();
            return totalPrice;
        } else {
            throw new RentingException("No movies returned.");
        }
    }
}
