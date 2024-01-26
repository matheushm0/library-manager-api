-- Create the BOOK table
CREATE TABLE BOOK (
    id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INTEGER NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    UNIQUE(isbn)
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