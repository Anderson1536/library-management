package com.devsenior.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsenior.exception.NotFoundException;

public class UserServiceTest {

    private UserService service;

    @BeforeEach // @BeforeEach: significa que este metodo se va a ejecutar antes de cada una de
                // las pruebas unitarias
    void setUp(){
        service = new UserService();
    }

    @Test
    void testAddUser() throws NotFoundException {
        // GIVEN
        var id = "123456";
        var name = "Anderson Mesa";
        var email = "anderson@email.com";

        // WHEN

        service.addUser(id, name, email);

        // THEN

        var user = service.getUserById(id);
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
    }

    @Test
    void testAddUserWhitRegisterDate() throws NotFoundException {
        // GIVEN
        var id = "123456";
        var name = "Anderson Mesa";
        var email = "anderson@email.com";
        var registerDate = LocalDate.now();

        // WHEN

        service.addUser(id, name, email,registerDate);

        // THEN

        var user = service.getUserById(id);
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(email, user.getEmail());
        assertEquals(LocalDate.now(), user.getRegisterDate());
    }

    @Test
    void testDeleExistingteUser() throws NotFoundException {
        //GIVEN
        var id = "123456";
        var name = "Anderson Mesa";
        var email = "anderson@email.com";
        
        service.addUser(id, name, email);

        //WHEN
        service.deleteUser(id);

        //THEN

        try {
            service.getUserById(id);
            fail();
        } catch (NotFoundException e) {
            assertTrue(true);
        }
    }


    @Test
    void testGetAllUsers() {
        // GIVEN
        var id1 = "12345";
        var name1 = "Anderson Mesa";
        var email1 = "anderson@email.com";

        service.addUser(id1, name1, email1);

        var id2 = "67890";
        var name2 = "Andres Mesa";
        var email2 = "andres@email.com";

        service.addUser(id2, name2, email2);

        // WHEN
        var users = service.getAllUsers();

        // THEN
        assertFalse(users.isEmpty());
        assertNotEquals(users.size(), 0);
    }

    @Test
    void testGetUserById() throws NotFoundException {
        // GIVEN
        var id = "123456";
        var name = "Anderson Mesa";
        var email = "anderson@email.com";

        service.addUser(id, name, email);

        // WHEN
        var user = service.getUserById(id);

        //THEN
        assertNotNull(user);
        assertEquals(id, user.getId());
    }

    @Test
    void testGetUserByIdWithWrongId() throws NotFoundException {
        // GIVEN
        var id = "123456";
        var name = "Anderson Mesa";
        var email = "anderson@email.com";

        service.addUser("1234567890", name, email);

        // WHEN - THEN
        assertThrows(NotFoundException.class, () -> service.getUserById(id));
        
    }

    @Test
    void testUpdateUserEmail() throws NotFoundException {
        // GIVEN
        var id = "123456";
        var name = "Anderson Mesa";
        var email = "anderson@email.com";

        service.addUser(id, name, email);

        // WHEN

        var newEmail = "anderson.mesa@email.com";
        service.updateUserEmail(id, newEmail);

        //THEN
        assertEquals(newEmail, service.getUserById(id).getEmail());
        assertFalse(email == service.getUserById(id).getEmail());
    }

    @Test
    void testUpdateUserName() throws NotFoundException {
        // GIVEN
        var id = "123456";
        var name = "Anderson Mesa";
        var email = "anderson@email.com";

        service.addUser(id, name, email);

        //WHEN 

        var newName = "Andres Alonso";
        service.updateUserName(id, newName);

        //THEN
        assertEquals(newName, service.getUserById(id).getName());
        assertFalse(name == service.getUserById(id).getName());

    }
}
