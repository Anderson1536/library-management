package com.devsenior.service;

import java.util.ArrayList;
import java.util.List;

import com.devsenior.exception.NotFoundException;
import com.devsenior.model.Loan;
import com.devsenior.model.LoanState;

public class LoanService {

    private List<Loan> loans;
    private BookService bookService;
    private UserService userService;

    public LoanService(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loans = new ArrayList<>();
    }

    public void addLoan(String id, String isbn) throws NotFoundException {
        var user = userService.getUserById(id);
        var book = bookService.getBookByIsbn(isbn);

        for (var loan : loans){
            if (loan.getBook().getIsbn().equals(isbn) && loan.getState().equals(LoanState.STARTED)){
                throw new NotFoundException("El libro con isbn " + isbn + " ya se encuentra prestado y debe ser devuelto antes de volver a prestar.");
            }
        }

        loans.add(new Loan(user, book));
    }

    public void returnBook(String id, String isbn) throws NotFoundException {
        for (var loan : loans) {
            if (loan.getUser().getId().equals(id)
                    && loan.getBook().getIsbn().equals(isbn)
                    && loan.getState().equals(LoanState.STARTED)) {
                loan.setState(LoanState.FINISHED);
                return; 
            }
        }
        throw new NotFoundException("No hay un prestamo del libro: " + isbn + " para el usuario: " + id);
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public List<Loan> getLoansByUserId(String id) throws NotFoundException{

        for (var loan : loans) {
            if (loan.getUser().getId().equals(id)) {
                return loans;
            }
        }

        throw new NotFoundException("No se encontraron prestamos para el usuario con id: " + id);
    }
}
