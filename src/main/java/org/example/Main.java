package org.example;

import org.example.user.NotValidMailException;
import org.example.user.account;
import org.example.user.user;
import org.example.user.userController;

import java.util.*;

public class Main {
    public static void main(String[] args) throws NotValidMailException {
        userController control = new userController();
        control.login();
        //
//        userController userController = new userController();
//        List<user> userList = new ArrayList<>();
//        userController.setUserList(userList);
//        account account1 = new account("Savings", 1000.0);
//        account account2 = new account("Current", 500.0);
//        Set<account> accountSet = new HashSet<>();
//        accountSet.add(account1);
//        accountSet.add(account2);
//        user user1 = new user("user1", "t1@gmail.com", "pass1", accountSet);
//        account1.setAccountOwnerID(user1.getID());
//        account2.setAccountOwnerID(user1.getID());
//        System.out.println(user1);
//        userController.withdrawService(user1);
//        System.out.println(user1);
//        Optional<account> account = user1.getAccountset().stream().filter(a -> (a.getID() + "").equals("5")).findAny();
//        if (account.isPresent()) {
//            account account3 = account.get();
//            System.out.println("yes account");
//
//            System.out.println(account3.toString());
//        }
//        else {
//            System.out.println("no account");
//        }
        //



    }
}