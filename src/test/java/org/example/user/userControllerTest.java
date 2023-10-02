package org.example.user;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

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
    @DisplayName("Test with correct email and password")
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
        assertTrue(printedOutput.contains("Welcome user1"));    }
    @Test
    @DisplayName("Test with correct email and wrong password")
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
        assertFalse(printedOutput.contains("Welcome user1"));    }
    @Test
    @DisplayName("Test with wrong email and correct password")
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
        assertFalse(printedOutput.contains("Welcome user1"));    }
    @Test
    @DisplayName("Test with wrong email and wrong password")
    void testLoginInvalidUserLogin() {
        // Prepare test input
        String input = "invalid-email\ninvalid-pass\n5\n";
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
        String input = "t3@gmail.com\npass3\nUser3\nt3@gmail.com\npass3\n5\n";
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
        assertEquals(1,userController.getUserList().size());
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

  @Test
  @DisplayName("When Deposit to my account")
    void testDepositFromMyAccount(){
      String input = "t4@gmail.com\npass4\n2\n5\n500\n5\n";
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      System.setOut(new PrintStream(outContent));

      userController userController = new userController();
      account account1 = new account("Savings",  1000.0);
//      account account2 = new account("Current",  500.0);


      Set<account> accountSet = new HashSet<>();
      accountSet.add(account1);
      user user1 = new user("user4","t4@gmail.com","pass4",accountSet);
      account1.setAccountOwnerID(user1.getID());
      userController.bankServices(user1);
      System.out.println("This message will be printed during the test.");

      Optional<account> account =  user1.getAccountset().stream().filter(a->(a.getID()+"").equals(5+"")).findAny();
      if(account.isPresent()){
          account account3 = account.get();
          assertEquals(1500,account3.getAccountBalance());

      }
//      else {
//          fail("noo");
//      }
  }
    @Test
    @DisplayName("When Deposit to account not mine")

    void testDepositFromAccountNotMine(){
        String input = "t1@gmail.com\npass1\n2\n10\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userController userController = new userController();
        account account1 = new account("Savings",  1000.0);
        account account2 = new account("Current",  500.0);


        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        user user1 = new user("user1","t1@gmail.com","pass1",accountSet);
        System.setOut(new PrintStream(outContent));

        userController.bankServices(user1);
        System.setOut(originalOut);

        assertFalse(user1.getAccountset().stream().anyMatch(a -> a.getID() == 10));


//        String printedOutput = outContent.toString();
//        System.out.println("sssss"+printedOutput);
//        assertTrue(printedOutput.contains("You don't have an account with this ID please recheck and try again"));

    }
    @Test
    @DisplayName("when Trying to Withdraw from an account of mine")
    void testWithdrawFromMyAccount(){
        String input = "t1@gmail.com\npass1\n3\n5\n500\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userController userController = new userController();
        List<user> userList = new ArrayList<>();
        userController.setUserList(userList);
        account account1 = new account("Savings",  1000.0);
        account account2 = new account("Current",  500.0);


        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        user user1 = new user("user1","t1@gmail.com","pass1",accountSet);
        account1.setAccountOwnerID(user1.getID());
        account2.setAccountOwnerID(user1.getID());
        System.out.println(user1);
        String printedOutput = outContent.toString();
        System.out.println(printedOutput);

        userController.bankServices(user1);
        Optional<account> account =  user1.getAccountset().stream().filter(a->(a.getID()+"").equals("5"+"")).findAny();
        if(account.isPresent()) {
            account account3 = account.get();
            assertEquals(500, account3.getAccountBalance(),"yes");
        }
        else {
            fail("kkkkkkkk");
        }
    }
    @Test
    @DisplayName("when Trying to Withdraw from an account not mine")
    void testWithdrawFromAccountNotMine(){
        String input = "t1@gmail.com\npass1\n3\n10\n500\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userController userController = new userController();
        account account1 = new account("Savings",  1000.0);
        account account2 = new account("Current",  500.0);


        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        user user1 = new user("user1","t1@gmail.com","pass1",accountSet);
        userController.bankServices(user1);
        assertFalse(user1.getAccountset().stream().anyMatch(a -> a.getID() == 10));


    }

    @Test
    void testWithdrawFromMyAccountNotAcceptableAmount(){
        String input = "t1@gmail.com\npass1\n3\n5\n1500\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        userController userController = new userController();
        account account1 = new account("Savings",  1000.0);
        account account2 = new account("Current",  500.0);


        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        user user1 = new user("user1","t1@gmail.com","pass1",accountSet);
        userController.bankServices(user1);
        Optional<account> account =  user1.getAccountset().stream().filter(a->(a.getID()+"").equals("5")).findAny();
        if(account.isPresent()){
            account account3 = account.get();
            String printedOutput = outContent.toString();
            assertAll(
                    ()-> assertEquals(1000,account3.getAccountBalance())
//                    ()-> assertTrue(printedOutput.contains("hhhhhhh"))

            );


        }
        else {
            fail("there is no account");
        }
    }


        }
