package com.example.library.service;

import com.example.library.model.Author;
import com.example.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public List<Author> searchAuthors(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllAuthors();
        }
        List<Author> bySurname = authorRepository.findBySurnameContainingIgnoreCase(keyword);
        List<Author> byName = authorRepository.findByNameContainingIgnoreCase(keyword);

        // Объединяем результаты, исключая дубликаты
        bySurname.addAll(byName.stream()
                .filter(author -> !bySurname.contains(author))
                .toList());

        return bySurname;
    }

    public boolean emailExists(String email) {
        return authorRepository.existsByEmail(email);
    }
}