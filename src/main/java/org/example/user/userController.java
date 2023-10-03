package org.example.user;

import java.util.*;
import java.util.stream.Collectors;

public class userController {
    private List<user> userList;
    public userController() {
        account account1 = new account("Savings",  1000.0);
        account account2 = new account("Current",  500.0);
        account account3 = new account("Savings",  1000.0);
        account account4 = new account("Current",  500.0);


        Set<account> accountSet = new HashSet<>();
        accountSet.add(account1);
        accountSet.add(account2);
        user user1 = new user("user1","t1@gmail.com","pass1",accountSet);
        account1.setAccountOwnerID(user1.getID());
        account2.setAccountOwnerID(user1.getID());
        accountSet = new HashSet<>();
        accountSet.add(account3);
        accountSet.add(account4);
        user user2 =  new user("user2","t2@gmail.com","pass2",accountSet);
        account3.setAccountOwnerID(user2.getID());
        account4.setAccountOwnerID(user2.getID());


        userList = new ArrayList<>(  Arrays.asList(
                user1
                ,user2
        ));


    }
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
                bankServices(user);


            }

            else {
                System.out.println("There is no user with email and pass! \n " +
                        "Do you want to register instead? \n" +
                        "Y/N/?");

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






    }
    public void bankServices(user user){
        System.out.println("Please type:-\n1 to list all your accouts\n" +
                "2 to Deposit\n" +
                "3 to Withdraw\n" +
                "4 to Open new Account\n" +
                "5 to end ");
        String option = scanner.nextLine();

        if (option.equals("1")) {
            user.getAccountset().forEach(account -> {
                System.out.println(account.toString());
            });
            bankServices(user);

        } else if (option.equals("2")) {
            System.out.println("Please enter the account ID");
            DepositService(user);
            bankServices(user);


        } else if (option.equals("3")) {
            System.out.println("Please enter the account ID");
            withdrawService(user);
            bankServices(user);

        }
        else if (option.equals("4")) {
            createAccount(user);
            bankServices(user);

        }
        else if(option.equals("5")){
            System.out.println("Thanks for using our services");
        }

    }

    private void createAccount(user user) {
        boolean flag = true;
        while (flag){
            System.out.println("Please choose the account Type:-\n" +
                    "Write C for Current\n" +
                    "Write S for Saving");
            String type = scanner.nextLine().trim().toUpperCase();
            if(type.equals("C")|| type.equals("S")){
                flag = false;
                type = type.equals("S")?"Saving":"Current";
                account newAccount = new account(type,user.getID(),0);
                user.getAccountset().add(newAccount);

            }
        }
    }

    public void withdrawService(user user) {
        String ID = scanner.nextLine();
        Optional<account>account =  user.getAccountset().stream().filter(a->(a.getID()+"").equals(ID)).findAny();
        if(!account.isPresent()){
            System.out.println("You don't have an account with this ID please recheck and try again");
        }
        else {
            System.out.println("Please enter the Amount");
            account account1 = account.get();

            String amount = scanner.nextLine();
            if(Double.parseDouble(amount)>account1.getAccountBalance()) {
                System.out.println("The amount entered greater that your current balance please try again!");
            }
            else {
//               List<account> temp =
                user.setAccountset(
                       user.getAccountset().stream().map(e->{
                    if((e.getID()+"").equals(ID)){
                        e.setAccountBalance(e.getAccountBalance()-Double.parseDouble(amount));
                    }
                    System.out.println(e.toString());

                        return e;

                }).collect(Collectors.toSet()));
            }
//                user.getAccountset().stream()
//                        .map(existingEmployee -> {
//                            if ((existingEmployee.getID()+"").equals(ID)) {
//
//                                return employee;
//                            } else {
//                                return existingEmployee;
//                            }
//                        }).collect(Collectors.toList());
        }

    }

    public void DepositService(user user){
        String ID = scanner.nextLine();
        Optional<account>account =  user.getAccountset().stream().filter(a->(a.getID()+"").equals(ID)).findAny();
        if(!account.isPresent()){
            System.out.println("You don't have an account with this ID please recheck and try again");
        }
        else {
            System.out.println("Please enter the Amount");
            String amount = scanner.nextLine();
            user.setAccountset(
                    user.getAccountset().stream().map(e->{
                        if((e.getID()+"").equals(ID)){
                            e.setAccountBalance(e.getAccountBalance()+Double.parseDouble(amount));
                        }
                        System.out.println(e.toString());

                        return e;

                    }).collect(Collectors.toSet()));
            user.getAccountset().forEach(account1 -> {
                System.out.println(account1.toString());
            });

        }

    }

    public void signUp() throws NotValidMailException {

        System.out.print("Enter your email: ");

        String email = scanner.nextLine();
            emailValidator.validateEmail(email);


        System.out.print("Enter your password: ");

        String password = scanner.nextLine();
        System.out.print("Enter your Name: ");

        String name = scanner.nextLine();

        Optional<user> userOptional = userList.stream()
                .filter(u -> u.getEmail().equals(email) && u.getPassword().equals(password))
                .findFirst();

        if (userOptional.isPresent()) {
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
        user user1 = new user(name,email,password,new HashSet<>());
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
