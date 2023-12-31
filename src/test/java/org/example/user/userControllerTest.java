package org.example.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        System.setOut(new PrintStream(outContent));
    }

    @Test
    @DisplayName("Test Log In with correct email and password")
    void testLoginValidUser() throws NotValidMailException {
        String input = "t1@gmail.com\npass1\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userController userController = new userController();

        List<user> userList = List.of(
                new user("user1", "t1@gmail.com", "pass1"),
                new user("user2", "t2@gmail.com", "pass2")
        );
        userController.setUserList(userList);
        userController.login();

        String printedOutput = outContent.toString();
        assertTrue(printedOutput.contains("Welcome user1"));
    }

    @Test
    @DisplayName("Test Log In with correct email and wrong password")
    void testLoginCorrectEmailWrongPassword() throws NotValidMailException {
        String input = "t1@gmail.com\npass11\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userController userController = new userController();

        List<user> userList = List.of(
                new user("user1", "t1@gmail.com", "pass1"),
                new user("user2", "t2@gmail.com", "pass2")
        );
        userController.setUserList(userList);
        userController.login();

        String printedOutput = outContent.toString();
        assertFalse(printedOutput.contains("Welcome user1"));
    }

    @Test
    @DisplayName("Test Log In with wrong email and correct password")
    void testLoginWrongEmailCorrectPassword() throws NotValidMailException {
        String input = "t11@gmail.com\npass1\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userController userController = new userController();

        List<user> userList = List.of(
                new user("user1", "t1@gmail.com", "pass1"),
                new user("user2", "t2@gmail.com", "pass2")
        );
        userController.setUserList(userList);
        userController.login();

        String printedOutput = outContent.toString();
        assertFalse(printedOutput.contains("Welcome user1"));
    }

    @Test
    @DisplayName("Test Log in with wrong email To throw exception")
    void testLoginInvalidUser_ThrowException() {
        String input = "invalid-email\nany_pass\n5\n";
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
    @DisplayName("Test signUp with correct email and password")
    void testSignUpValidUser() throws NotValidMailException {
        // Prepare test input
        String input = "t3@gmail.com\npass3\nUser3\nt3@gmail.com\npass3\n5\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userController userController = new userController();

        List<user> userList = new ArrayList<>();
        userController.setUserList(userList);

        userController.signUp();

        String printedOutput = outContent.toString();
        assertAll(
                () -> assertTrue(printedOutput.contains("Welcome User3")),
                () -> assertEquals(1, userController.getUserList().size())

        );

    }

    @Test
    @DisplayName("Test signUp with invalid email to throw Exception")
    void testSignUpInvalidUserSignUpWithInvalidEmail() {
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
    @DisplayName("Test signUp with correct email so no exception is thrown")
    void testSignUpValidUserNotExceptionThrown() {
        // Prepare test input
        String input = "t3@gmail.com\npass3\nUser3\nt3@gmail.com\npass3\n5\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userController userController = new userController();
        List<user> userList = new ArrayList<>();
        userController.setUserList(userList);
        assertDoesNotThrow(userController::signUp);

    }

    @Test
    @DisplayName("When Deposit to my account")
    void testDepositFromMyAccount() {
        String input = "1\n500\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userController userController = new userController();
        List<user> userList = new ArrayList<>();
        userController.setUserList(userList);
        account.setLastAssignedID(0);
        account account1 = new account("Savings", 1000.0);
        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        user user1 = new user("user4", "t4@gmail.com", "pass4", accountSet);
        account1.setAccountOwnerID(user1.getID());
        userList.add(user1);
        userController.setUserList(userList);
        userController.DepositService(user1);
        Optional<account> accountTemp = user1.getAccountset().stream().filter(a -> (a.getID() + "").equals(1 + "")).findAny();
        if (accountTemp.isPresent()) {
            account account3 = userController.getUserList().get(0).getAccountset().stream().findFirst().get();
            String printedOutput = outContent.toString();
            assertEquals(1500, account3.getAccountBalance());

        } else {
            fail("noo");
        }
    }

    @Test
    @DisplayName("When Deposit to account not mine")
    void testDepositFromAccountNotMine() {
        String input = "10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userController userController = new userController();
        account account1 = new account("Savings", 1000.0);
        account account2 = new account("Current", 500.0);
        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        user user1 = new user("user1", "t1@gmail.com", "pass1", accountSet);
        System.setOut(new PrintStream(outContent));
        userController.withdrawService(user1);
        String printedOutput = outContent.toString();
        assertAll(
                ()->  assertFalse(user1.getAccountset().stream().anyMatch(a -> a.getID() == 10)),
                ()->  assertTrue(printedOutput.contains("You don't have an account with this ID please recheck and try again"))

        );



    }

    @Test
    @DisplayName("when Trying to Withdraw from an account of mine")
    void testWithdrawFromMyAccount() {
        String input = "5\n500\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        userController userController = new userController();
        List<user> userList = new ArrayList<>();
        userController.setUserList(userList);
        account account1 = new account("Savings", 1000.0);
        account account2 = new account("Current", 500.0);
        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        user user1 = new user("user1", "t1@gmail.com", "pass1", accountSet);
        userList.add(user1);
        account1.setAccountOwnerID(user1.getID());
        account2.setAccountOwnerID(user1.getID());
//        System.out.println(user1);
        userController.withdrawService(user1);
        Optional<account> account = user1.getAccountset().stream().filter(a -> (a.getID() + "").equals("5")).findAny();
        if (account.isPresent()) {
            account account3 = account.get();
            assertEquals(500, account3.getAccountBalance(), user1.getAccountset().toString());
        } else {
            fail("nooooo");
        }
    }

    @Test
    @DisplayName("when Trying to Withdraw from an account not mine")
    void testWithdrawFromAccountNotMine() {
        String input = "10\n500\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userController userController = new userController();
        account account1 = new account("Savings", 1000.0);
        account account2 = new account("Current", 500.0);


        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        user user1 = new user("user1", "t1@gmail.com", "pass1", accountSet);
        userController.withdrawService(user1);
        assertFalse(user1.getAccountset().stream().anyMatch(a -> a.getID() == 10));


    }

    @Test
    void testWithdrawFromMyAccountNotAcceptableAmount() {
        String input = "5\n1500\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userController userController = new userController();
        account account1 = new account("Savings", 1000.0);
        account account2 = new account("Current", 500.0);


        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        user user1 = new user("user1", "t1@gmail.com", "pass1", accountSet);
        userController.withdrawService(user1);
        Optional<account> account = user1.getAccountset().stream().filter(a -> (a.getID() + "").equals("5")).findAny();
        if (account.isPresent()) {
            account account3 = account.get();

            assertEquals(1000, account3.getAccountBalance());


        } else {
            fail("there is no account");
        }
    }


}