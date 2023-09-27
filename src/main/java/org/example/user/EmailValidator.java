package org.example.user;

public class EmailValidator {
    public  void validateEmail(String email) throws NotValidMailException {
        if (!isValidEmail(email)) {
            throw new NotValidMailException("Email address is not valid.");
        }
    }

    private static boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }
}
