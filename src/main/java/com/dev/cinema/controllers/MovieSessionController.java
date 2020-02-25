package com.dev.cinema.controllers;

import com.dev.cinema.dto.MovieSessionRequestDto;
import com.dev.cinema.dto.MovieSessionResponseDto;
import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moviesessions")
public class MovieSessionController {
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final MovieSessionService movieSessionService;
    private final MovieService movieService;
    private final CinemaHallService cinemaHallService;

    public MovieSessionController(MovieSessionService movieSessionService,
                                  MovieService movieService,
                                  CinemaHallService cinemaHallService) {
        this.movieSessionService = movieSessionService;
        this.movieService = movieService;
        this.cinemaHallService = cinemaHallService;
    }

    @PostMapping("/add")
    private MovieSession addMovieSession(@Valid @RequestBody MovieSessionRequestDto movieSessionDto,
                                         BindingResult result) {
        if (result.hasErrors()) {
            throw new DataProcessingException("Wrong parameters");
        }
        MovieSession movieSession = new MovieSession();
        movieSession.setShowTime(LocalDateTime.parse(movieSessionDto.getShowTime(), FORMATTER));
        movieSession.setMovie(movieService.getById(movieSessionDto.getMovieId()));
        movieSession.setCinemaHall(cinemaHallService.getById(movieSessionDto.getCinemaHallId()));
        return movieSessionService.add(movieSession);
    }

    @GetMapping("/available")
    private List<MovieSessionResponseDto> getAvailableMovieSessions(
            @RequestParam(name = "movie_id") Long movieId,
            @RequestParam(name = "date") String date) {
        LocalDate localDate = LocalDate.parse(date);
        return movieSessionService.findAvailableSessions(movieId, localDate)
                .stream()
                .map(this::getMovieSessionToMovieSessionResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping
    private List<MovieSessionResponseDto> getAll() {
        return movieSessionService
                .getAll()
                .stream()
                .map(this::getMovieSessionToMovieSessionResponseDto)
                .collect(Collectors.toList());
    }

    private MovieSessionResponseDto getMovieSessionToMovieSessionResponseDto(
            MovieSession movieSession) {
        MovieSessionResponseDto movieSessionDto = new MovieSessionResponseDto();
        movieSessionDto.setCinemaHallDescription(movieSession.getCinemaHall().getDescription());
        movieSessionDto.setMovieTitle(movieSession.getMovie().getTitle());
        movieSessionDto.setShowTime(movieSession.getShowTime().format(FORMATTER));
        return movieSessionDto;
    }
}
