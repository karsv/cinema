package com.dev.cinema.dao;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.CinemaHall;

import java.util.List;

public interface CinemaHallDao {
    CinemaHall add(CinemaHall cinemaHall) throws DataProcessingException;

    List<CinemaHall> getAll() throws DataProcessingException;

    CinemaHall getById(Long id) throws DataProcessingException;
}
