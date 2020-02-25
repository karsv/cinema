package com.dev.cinema.dto;

import javax.validation.constraints.NotNull;

public class MovieSessionRequestDto {
    @NotNull(message = "Movie id couldn't be null!")
    private Long movieId;
    @NotNull(message = "CinemaHall id couldn't be null!")
    private Long cinemaHallId;
    @NotNull(message = "Showtime couldn't be null!")
    private String showTime;

    public Long getCinemaHallId() {
        return cinemaHallId;
    }

    public void setCinemaHallId(Long cinemaHallId) {
        this.cinemaHallId = cinemaHallId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
}
