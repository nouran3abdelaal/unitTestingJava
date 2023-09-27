package org.example.user;

//import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {
    @Test
    public void testValidEmail() {
        EmailValidator emailValidator = new EmailValidator();
        String validEmail = "valid@example.com";

        // This should not throw an exception
        assertDoesNotThrow(() -> emailValidator.validateEmail(validEmail));
    }
    @Test
    public void testInvalidEmail() {
        EmailValidator emailValidator = new EmailValidator();
        String invalidEmail = "invalid-email";

        // This should throw a NotValidMailException
        assertThrows(NotValidMailException.class, () -> emailValidator.validateEmail(invalidEmail));
    }

}