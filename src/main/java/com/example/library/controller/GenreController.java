package com.example.library.controller;

import com.example.library.model.Genre;
import com.example.library.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping
    public String listGenres(@RequestParam(value = "search", required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("genres", genreService.searchGenres(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("genres", genreService.getAllGenres());
        }
        return "genres/list";
    }

    @GetMapping("/add")
    public String showAddGenreForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "genres/add";
    }

    @PostMapping("/add")
    public String addGenre(@Valid @ModelAttribute("genre") Genre genre,
                           BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "genres/add";
        }

        // Проверка уникальности названия жанра
        if (genreService.genreNameExists(genre.getName())) {
            model.addAttribute("nameError", "Жанр с таким названием уже существует");
            return "genres/add";
        }

        genreService.saveGenre(genre);
        return "redirect:/genres";
    }

    @GetMapping("/edit/{id}")
    public String showEditGenreForm(@PathVariable("id") Long id, Model model) {
        Optional<Genre> genre = genreService.getGenreById(id);
        if (genre.isPresent()) {
            model.addAttribute("genre", genre.get());
            return "genres/edit";
        } else {
            return "redirect:/genres";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateGenre(@PathVariable("id") Long id,
                              @Valid @ModelAttribute("genre") Genre genre,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "genres/edit";
        }

        // Проверка уникальности названия жанра (исключая текущий жанр)
        Genre existingGenre = genreService.getGenreById(id).orElse(null);
        if (existingGenre != null &&
                !genre.getName().equals(existingGenre.getName()) &&
                genreService.genreNameExists(genre.getName())) {
            model.addAttribute("nameError", "Жанр с таким названием уже существует");
            return "genres/edit";
        }

        genre.setId(id);
        genreService.saveGenre(genre);
        return "redirect:/genres";
    }

    @GetMapping("/delete/{id}")
    public String deleteGenre(@PathVariable("id") Long id) {
        genreService.deleteGenre(id);
        return "redirect:/genres";
    }
}