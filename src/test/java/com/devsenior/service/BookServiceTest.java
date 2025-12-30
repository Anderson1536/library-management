package com.devsenior.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        var isbn = "123456789";

        // WHEN - THEN
        assertThrows(NotFoundException.class, 
            () -> {
                service.deleteBook(isbn);
            });
    }

    //TERMINAR DE PROBAR LOS DOS ULTIMOS METODOS Y UserService.java

    @Test
    void testGetAllBooks() {

    }

    @Test
    void testGetBookByIsbn() {

    }
}
