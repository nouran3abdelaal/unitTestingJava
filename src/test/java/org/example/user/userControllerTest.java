package org.example.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Mock
    private EmailValidator emailValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    void testLoginValidUser() throws NotValidMailException {
        String input = "t1@gmail.com\npass1\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Create an instance of UserController
        userController userController = new userController();

        // Mock the user list
        List<user> userList = List.of(
                new user("user1", "t1@gmail.com", "pass1"),
                new user("user2", "t2@gmail.com", "pass2")
        );
        userController.setUserList(userList);
        userController.login();

        String printedOutput = outContent.toString();
        assertTrue(printedOutput.contains("Welcome user1"));    }

    @Test
    void testLoginInvalidUserLogin() {
        // Prepare test input
        String input = "invalid-email\ninvalid-pass\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userController userController = new userController();

        List<user> userList = List.of(
                new user("user1", "t1@gmail.com", "pass1"),
                new user("user2", "t2@gmail.com", "pass2")
        );
        userController.setUserList(userList);
        assertThrows(NotValidMailException.class, userController::login);

    }
    @Test
    void testSignUpValidUser() throws NotValidMailException {
        // Prepare test input
        String input = "t3@gmail.com\npass3\nUser3\nt3@gmail.com\npass3\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        // Create an instance of UserController
        userController userController = new userController();

        // Mock the user list
        List<user> userList = new ArrayList<>();
        userController.setUserList(userList);

        userController.signUp();

        // Check the output
        String printedOutput = outContent.toString();
        assertTrue(printedOutput.contains("Welcome User3"));
    }
    @Test
    void testSignUpInvalidUserSignUp() {
        // Prepare test input
        String input = "invalid-email\ninvalid-pass\nanyName\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userController userController = new userController();

        List<user> userList = new ArrayList<>();

        userController.setUserList(userList);
        assertThrows(NotValidMailException.class, userController::signUp);

    }
    @Test
    void testSignUpvalidUserSignUp() {
        // Prepare test input
        String input = "t3@gmail.com\npass3\nUser3\nt3@gmail.com\npass3\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userController userController = new userController();

        List<user> userList = new ArrayList<>();

        userController.setUserList(userList);
        assertDoesNotThrow( userController::signUp);

    }




        }
