package com.devsenior.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.devsenior.exception.NotFoundException;
import com.devsenior.model.Book;
import com.devsenior.model.LoanState;
import com.devsenior.model.User;

public class LoanServiceTest {

    private BookService bookService;
    private UserService userService;
    private LoanService service;

    @BeforeEach
    void setup(){
        bookService = Mockito.mock(BookService.class);
        userService = Mockito.mock(UserService.class);
        // Mockito.mock - reemplza la instancia real de una clase, es decir new BookService y solo simula la clase para usar sus metodos

        service = new LoanService(bookService, userService); 
        //Creamos el servicio con los objetos simulados
    }

    @DisplayName("Agregar un prestamo con usuario y libro existente")
    @Test
    void testAddLoanWithExistingUserAndExistingBook() throws NotFoundException {
        // GIVEN
        var id = "123";
        var isbn = "1234567890";
        var mockUser = new User(id, "Jhon", "Jhon@email.com");
        var mockBook = new Book(isbn, "Aprendiendo Java", "Cesar Diaz");

        Mockito.when(userService.getUserById(id)).thenReturn(mockUser);
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(mockBook);

        // WHEN
        service.addLoan(id, isbn);

        // THEN
        var loans = service.getLoans();
        assertEquals(1, loans.size());

        var loan = loans.get(0);
        assertNotNull(loan.getUser());
        assertNotNull(loan.getBook());
        assertEquals(LoanState.STARTED, loan.getState());
    }

    @Test
    void testReturnBook() throws NotFoundException {
        // GIVEN
        var id = "123";
        var isbn = "1234567890";
        var mockUser = new User(id, "Jhon", "Jhon@email.com");
        var mockBook = new Book(isbn, "Aprendiendo Java", "Cesar Diaz");

        Mockito.when(userService.getUserById(id)).thenReturn(mockUser);
        Mockito.when(bookService.getBookByIsbn(isbn)).thenReturn(mockBook);

        service.addLoan(mockUser.getId(), mockBook.getIsbn());
        var loans = service.getLoans();

        // WHEN

        service.returnBook(id, isbn);
        
        // THEN
        assertEquals(1, loans.size());

        var loan = loans.get(0);
        assertEquals(LoanState.FINISHED, loan.getState());
        assertEquals(id, loan.getUser().getId());
        assertEquals(isbn, loan.getBook().getIsbn());

    }
}
