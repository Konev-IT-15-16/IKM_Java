package com.example.library.service;

import com.example.library.model.Genre;
import com.example.library.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    public Genre saveGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }

    public List<Genre> searchGenres(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllGenres();
        }
        return genreRepository.findByNameContainingIgnoreCase(keyword);
    }

    public boolean genreNameExists(String name) {
        return genreRepository.existsByName(name);
    }
}