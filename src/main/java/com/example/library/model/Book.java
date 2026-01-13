package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название книги обязательно")
    @Size(min = 1, max = 200, message = "Название книги должно содержать от 1 до 200 символов")
    @Column(nullable = false)
    private String title;

    @Column(unique = true, length = 20)
    private String isbn;

    @Size(max = 1000, message = "Аннотация не должна превышать 1000 символов")
    @Column(columnDefinition = "TEXT", length = 1000)
    private String description;

    @NotNull(message = "Год издания обязателен")
    @Min(value = 1000, message = "Год издания должен быть не меньше 1000")
    @Max(value = 2025, message = "Год издания должен быть не больше 2100")
    @Column(name = "publication_year", nullable = false)
    private Integer publicationYear;

    @DecimalMin(value = "0.0", message = "Цена не может быть отрицательной")
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Количество страниц обязательно")
    @Min(value = 1, message = "Количество страниц должно быть не меньше 1")
    @Column(name = "page_count", nullable = false)
    private Integer pageCount;

    @NotNull(message = "Автор обязателен")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @ManyToMany
    @JoinTable(
            name = "book_genres",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Конструкторы
    public Book() {
        this.createdAt = LocalDateTime.now();
    }

    public Book(String title, String description, Integer publicationYear,
                BigDecimal price, Integer pageCount, Author author, Genre genre) {
        this();
        this.title = title;
        this.description = description;
        this.publicationYear = publicationYear;
        this.price = price;
        this.pageCount = pageCount;
        this.author = author;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return title + " (" + publicationYear + ")";
    }
}