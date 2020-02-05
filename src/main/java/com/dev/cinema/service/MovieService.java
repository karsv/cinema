package com.dev.cinema.service;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.Movie;

import java.util.List;

public interface MovieService {
    Movie add(Movie movie) throws DataProcessingException;

    List<Movie> getAll();
}
