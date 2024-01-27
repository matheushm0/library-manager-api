-- Create the BOOK table
CREATE TABLE BOOK (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INTEGER NOT NULL,
    isbn VARCHAR(13) NOT NULL
);

-- Create the PATRON table
CREATE TABLE PATRON (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Create the BORROWING_RECORD table
CREATE TABLE BORROWING_RECORD (
    id BIGINT PRIMARY KEY,
    book_id BIGINT REFERENCES BOOK(id) ON DELETE CASCADE,
    patron_id BIGINT REFERENCES PATRON(id) ON DELETE CASCADE,
    borrowing_date DATE NOT NULL,
    return_date DATE
);

CREATE TABLE LIBRARIAN (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO LIBRARIAN (first_name, last_name, email, password, role)
VALUES ('John', 'Doe', 'admin@test.com', '$2a$10$W9AOalJ1VAH2vdbjfw5jjeWJy90/6t.Wo0Wm3pqEIHP.V5HpauKWa', 'ADMIN');
