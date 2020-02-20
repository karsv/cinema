package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.MovieSession;

import java.time.LocalDate;
import java.util.List;

public interface MovieSessionDao {
    MovieSession add(MovieSession movieSession) throws DataProcessingException;

    List<MovieSession> findAvailableSessions(Long movieId, LocalDate date);

    List<MovieSession> getAll();

    MovieSession getById(Long id);
}
