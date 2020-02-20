package com.dev.cinema.service;

import com.dev.cinema.exception.DataProcessingException;
import com.dev.cinema.model.CinemaHall;

import java.util.List;

public interface CinemaHallService {
    CinemaHall add(CinemaHall cinemaHall) throws DataProcessingException;
    
    List<CinemaHall> getAll() throws DataProcessingException;

    CinemaHall getById(Long id) throws DataProcessingException;
}
