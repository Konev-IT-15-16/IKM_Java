-- Очистка таблиц (опционально)
DELETE FROM reviews;
DELETE FROM books;
DELETE FROM authors;


-- Создание таблицы authors
CREATE TABLE IF NOT EXISTS authors (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE,
    birth_date DATE,
    bio TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

-- Создание таблицы books
CREATE TABLE IF NOT EXISTS books (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    author_id BIGINT NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    publication_year INTEGER CHECK (publication_year >= 1000 AND publication_year <= 2100),
    price DECIMAL(10, 2) CHECK (price >= 0),
    page_count INTEGER CHECK (page_count > 0),
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_author
    FOREIGN KEY (author_id)
    REFERENCES authors(id)
    ON DELETE CASCADE
    );

-- Создание таблицы reviews
CREATE TABLE IF NOT EXISTS reviews (
    id BIGSERIAL PRIMARY KEY,
    book_id BIGINT NOT NULL,
    reviewer_name VARCHAR(100) NOT NULL,
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    review_date DATE DEFAULT CURRENT_DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_book
    FOREIGN KEY (book_id)
    REFERENCES books(id)
    ON DELETE CASCADE
    );

-- Индексы для оптимизации
CREATE INDEX IF NOT EXISTS idx_books_author_id ON books(author_id);
CREATE INDEX IF NOT EXISTS idx_reviews_book_id ON reviews(book_id);


-- Вставка авторов
INSERT INTO authors (first_name, last_name, email, birth_date, bio) VALUES
                                                                        ('Лев', 'Толстой', 'tolstoy@example.com', '1828-09-09', 'Русский писатель'),
                                                                        ('Фёдор', 'Достоевский', 'dostoevsky@example.com', '1821-11-11', 'Русский писатель'),
                                                                        ('Антон', 'Чехов', 'chekhov@example.com', '1860-01-29', 'Русский писатель')
    ON CONFLICT (email) DO NOTHING;

-- Вставка книг
INSERT INTO books (title, author_id, isbn, publication_year, price, page_count, description) VALUES
                                                                                                 ('Война и мир', 1, '978-5-389-00001-1', 1869, 1200.00, 1225, 'Роман-эпопея'),
                                                                                                 ('Анна Каренина', 1, '978-5-389-00002-2', 1877, 1000.00, 864, 'Роман о любви'),
                                                                                                 ('Преступление и наказание', 2, '978-5-389-00003-3', 1866, 800.00, 671, 'Психологический роман'),
                                                                                                 ('Вишневый сад', 3, '978-5-389-00004-4', 1904, 500.00, 120, 'Пьеса')
    ON CONFLICT (isbn) DO NOTHING;

-- Вставка отзывов
INSERT INTO reviews (book_id, reviewer_name, rating, comment) VALUES
                                                                  (1, 'Иван Петров', 5, 'Великая книга!'),
                                                                  (1, 'Мария Сидорова', 4, 'Очень длинная, но интересная'),
                                                                  (2, 'Алексей Иванов', 5, 'Трагическая история'),
                                                                  (3, 'Елена Кузнецова', 4, 'Глубокий психологический анализ');