package Utils;

import Exceptions.FilmException;

public class Prices {
    private static int PREMIUM_PRICE = 4;
    private static int BASIC_PRICE = 3;

    public static int getPrice(FilmType type, int numOfDays) {
        switch (type) {
            case NEW_RELEASE:
                return PREMIUM_PRICE * numOfDays;
            case REGULAR_FILM:
                return BASIC_PRICE + (numOfDays > 3 ? (numOfDays - 3) * BASIC_PRICE : 0);
            case OLD_FILM:
                return BASIC_PRICE + (numOfDays > 5 ? (numOfDays - 5) * BASIC_PRICE : 0);
            default:
                throw new FilmException("Unknown type.");
        }
    }

    public static int getReturnPrice(FilmType type, int extraDays) {
        switch (type) {
            case NEW_RELEASE:
                return PREMIUM_PRICE * extraDays;
            case REGULAR_FILM:
            case OLD_FILM:
                return BASIC_PRICE * extraDays;
            default:
                throw new FilmException("Unknown type.");
        }
    }
}