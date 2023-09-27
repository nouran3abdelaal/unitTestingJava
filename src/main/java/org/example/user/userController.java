package org.example.user;

import java.util.*;

public class userController {


    private List<user> userList = new ArrayList<>(  Arrays.asList(
            new user("user1","t1@gmail.com","pass1"),
            new user("user2","t2@gmail.com","pass2")

    ));
    EmailValidator emailValidator = new EmailValidator();
    Scanner scanner = new Scanner(System.in);

    public void login() throws NotValidMailException {

        System.out.print("Enter your email: ");

        String email = scanner.nextLine();
//        try {
            emailValidator.validateEmail(email);
            System.out.print("Enter your password: ");
//         scanner = new Scanner(System.in);

        String pass = scanner.nextLine();

            Optional<user> userOptional = userList.stream()
                    .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(pass))
                    .findFirst();

            if (userOptional.isPresent()) {
                user user = userOptional.get();
                System.out.println("Welcome "+user.getName());

            }

            else {
                System.out.println("There is no user with email and pass! \n " +
                        "Do you want to register instead? \n" +
                        "Y/N/?");
//                scanner = new Scanner(System.in);

                String userInput = scanner.nextLine().trim().toUpperCase();

                if (userInput.equals("Y")) {
                    signUp();
                } else if (userInput.equals("N")) {
                    login();

                } else {
                    System.out.println("Invalid input. Please enter either 'Y' or 'N'."); // Invalid input
                }

                scanner.close();
            }
//        } catch (NotValidMailException e) {
//            System.out.println("Caught an exception: " + e.getMessage());
//            login();
////            return;
//        }





    }

    public void signUp() throws NotValidMailException {

        System.out.print("Enter your email: ");

        String email = scanner.nextLine();
//        try {
            emailValidator.validateEmail(email);
//        } catch (NotValidMailException e) {
//            System.out.println("Caught an exception: " + e.getMessage());
//            signUp();
//            return;
       // }

        System.out.print("Enter your password: ");

        String password = scanner.nextLine();
        System.out.print("Enter your Name: ");

        String name = scanner.nextLine();

        Optional<user> userOptional = userList.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst();

        if (userOptional.isPresent()) {
//            user user = userOptional.get();
            System.out.println("User already exits! \n" +
                    "Do you want to login instead? \n" +
                    "press Y or N:");
            String userInput = scanner.nextLine().trim().toUpperCase();

            if (userInput.equals("Y")) {
                login();
                return;
            } else if (userInput.equals("N")) {
                signUp();
                return;

            } else {
                System.out.println("Invalid input. Please enter either 'Y' or 'N'."); // Invalid input
                signUp();
                return;
            }
        }
        user user1 = new user(name,email,password);
        userList.add(user1);
        login();


    }
    public List<user> getUserList() {
        return userList;
    }

    public void setUserList(List<user> userList) {
        this.userList = userList;
    }
}
