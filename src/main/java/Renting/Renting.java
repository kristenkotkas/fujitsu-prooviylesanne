package Renting;

public class Renting extends Rental {
    private boolean paidWithBonus;

    public Renting(Film film, int initialDays) {
        super(film, initialDays);
        film.rent();
    }

    public Renting(Film film, int initialDays, boolean paidWithBonus) {
        super(film, initialDays);
        this.paidWithBonus = paidWithBonus;
        film.rent();
    }

    public boolean isPaidWithBonus() {
        return paidWithBonus;
    }

    public void setPaidWithBonus(boolean paidWithBonus) {
        this.paidWithBonus = paidWithBonus;
    }

    @Override
    public int getRentalPrice() {
        return paidWithBonus ? 0 : super.getRentalPrice();
    }

    @Override
    public void printRentalInfo() {
        StringBuilder builder = new StringBuilder(super.getFilm() + " " + super.getDays() + " days ");
        if (isPaidWithBonus()) {
            builder.append("(Paid with ").append(getDays() * 25).append(" Bonus points)");
        } else {
            builder.append(getRentalPrice()).append(" EUR");
        }
        System.out.println(builder.toString());
    }
}
