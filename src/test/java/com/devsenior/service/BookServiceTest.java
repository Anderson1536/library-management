package com.devsenior.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsenior.exception.NotFoundException;

public class BookServiceTest {

    private BookService service;

    @BeforeEach // @BeforeEach: significa que este metodo se va a ejecutar antes de cada una de
                // las pruebas unitarias
    void setup() {
        service = new BookService();
    }

    @Test
    void testAddBook() throws NotFoundException {
        // GIVEN - Preparar los datos de la prueba

        var isbn = "123456789";
        var title = "Aprendiendo Java";
        var author = "Cesar Diaz";

        // WHEN - Ejectutar el metodo a probar
        service.addBook(isbn, title, author);

        // THEN - Verificaciones que el metodo se ejecutó bien
        var book = service.getBookByIsbn(isbn);
        assertNotNull(book);
        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
    }

    @Test
    void testDeleteExistingdBook() throws NotFoundException {
        // GIVEN
        var isbn = "123456789";
        var title = "Aprendiendo Java";
        var author = "Cesar Diaz";
        service.addBook(isbn, title, author);

        // WHEN
        service.deleteBook(isbn);

        // THEN
        try {
            service.getBookByIsbn(isbn);
            fail();
        } catch (NotFoundException e) {
            assertTrue(true);
        }
    }

    @Test
    void testDeleteNonExistingdBook() {
        // GIVEN
        service.addBook("9806543", "Aprendiendo Java", "Anderson Mesa");
        var isbn = "123456789";

        // WHEN - THEN
        assertThrows(NotFoundException.class,
                () -> {
                    service.deleteBook(isbn);
                });
    }

    @Test
    void testGetAllBooks() {
        // GIVEN
        var isbn1 = "123";
        var title1 = "Aprendiendo Java";
        var author1 = "Anderson Mesa";
        service.addBook(isbn1, title1, author1);

        var isbn2 = "456";
        var title2 = "Programacion";
        var author2 = "Anderson Mesa";
        service.addBook(isbn2, title2, author2);

        // WHEN

        var books = service.getAllBooks();

        // THEN

        assertNotEquals(books.size(), 0);
        assertFalse(books.isEmpty());

    }

    @Test
    void testGetBookByIsbn() throws NotFoundException {

        // GIVEN
        var isbn = "123456789";
        var title = "Aprendiendo Java";
        var author = "Cesar Diaz";
        service.addBook(isbn, title, author);

        // WHEN

        var book = service.getBookByIsbn(isbn);

        // THEN
        assertNotNull(book);
        assertEquals(isbn, book.getIsbn());

    }

    @Test
    void testGetBookByIsbnWithWrongIsbn() throws NotFoundException {

        // GIVEN
        var isbn = "123456789";
        var title = "Aprendiendo Java";
        var author = "Anderson Mesa";
        service.addBook("12345", title, author);

        // WHEN - THEN
        assertThrows(NotFoundException.class, () -> service.getBookByIsbn(isbn));
    }
}
