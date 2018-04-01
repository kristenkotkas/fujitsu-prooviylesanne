package Data;

import Utils.FilmType;

public class Film {
    private String filmTitle;
    private FilmType filmType;
    private boolean isRented = false;

    public Film(String filmTitle, FilmType filmTypes) {
        this.filmTitle = filmTitle;
        this.filmType = filmTypes;
    }

    public String getFilmTitle() {
        return filmTitle;
    }

    public void setFilmTitle(String filmTitle) {
        this.filmTitle = filmTitle;
    }

    public FilmType getFilmType() {
        return filmType;
    }

    public void setFilmType(FilmType filmType) {
        this.filmType = filmType;
    }

    public boolean isRented() {
        return isRented;
    }

    public void rent() {
        this.isRented = true;
    }

    public void returnFilm() {
        this.isRented = false;
    }

    @Override
    public String toString() {
        return filmTitle + " (" + filmType + ")";
    }
}
