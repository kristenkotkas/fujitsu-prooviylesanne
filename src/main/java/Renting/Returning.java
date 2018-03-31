package Renting;

public class Returning extends Rental {
    public Returning(Film film, int extraDays) {
        super(film, extraDays);
        film.returnFilm();
    }

    private String getCorrectForm(String text, int count) {
        return count == 1 ? text : text + "s";
    }

    @Override
    public int getRentalPrice() {
        return Prices.getReturnPrice(super.getFilm().getFilmType(), super.getDays());
    }

    @Override
    public void printRentalInfo() {
        if (super.getDays() > 0) {
            System.out.println(
                    super.getFilm() + " " + super.getDays() + " extra " +
                            getCorrectForm("day", super.getDays()) + " " +
                            Prices.getReturnPrice(super.getFilm().getFilmType(), super.getDays()) + " EUR");
        }
    }
}
