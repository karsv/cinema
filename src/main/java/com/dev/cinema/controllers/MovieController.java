package com.dev.cinema.controllers;

import com.dev.cinema.dto.MovieRequestDto;
import com.dev.cinema.dto.MovieResponseDto;
import com.dev.cinema.model.Movie;
import com.dev.cinema.service.MovieService;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController<T> {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public Movie addMovie(@Valid @RequestBody MovieRequestDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        return movieService.add(movie);
    }

    @GetMapping
    public List<MovieResponseDto> getAllMovie() {
        return movieService
                .getAll()
                .stream()
                .map(this::getMovieToMovieResponseDto)
                .collect(Collectors.toList());
    }

    private MovieResponseDto getMovieToMovieResponseDto(Movie movie) {
        MovieResponseDto movieDto = new MovieResponseDto();
        movieDto.setMovieId(movie.getId());
        return movieDto;
    }
}
