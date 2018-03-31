package Renting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Inventory {
    private List<Film> films = new ArrayList<>();

    public void addFilm(Film film) {
        this.films.add(film);
    }

    public void addFilms(Film... films) {
        this.films.addAll(Arrays.asList(films));
    }

    public void removeFilm(Film film) {
        this.films.remove(film);
    }

    public void changeFilmType(Film film, FilmType type) {
        if (this.films.contains(film)) {
            this.films.stream()
                    .filter(film1 -> film1.getFilmTitle().equals(film.getFilmTitle()))
                    .findFirst()
                    .get()
                    .setFilmType(type);
        } else {
            throw new RuntimeException(film.getFilmTitle() + "not in inventory.");
        }
    }

    public List<Film> getAllFilms() {
        return this.films;
    }

    public List<Film> getAvailableFilms() {
        return this.films.stream()
                .filter(film -> !film.isRented())
                .collect(Collectors.toList());
    }
}
