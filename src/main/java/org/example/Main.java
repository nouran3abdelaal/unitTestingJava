package org.example;

import org.example.user.NotValidMailException;
import org.example.user.userController;

public class Main {
    public static void main(String[] args) throws NotValidMailException {
        userController control = new userController();
        control.login();
    }
}