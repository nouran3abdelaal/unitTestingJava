package org.example.user;

import java.util.HashSet;
import java.util.Set;

public class user {
    private String name;
    private String email;
    private String password;
    private Set<account> accountset;
    public int getID() {
        return ID;
    }

    private int ID;
    private static int lastAssignedID = 0;

    public user(String name, String email, String password, Set<account> accountset) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountset = accountset;
        this.ID = ++lastAssignedID;

    }

    public user(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountset = new HashSet<>();
        this.ID = ++lastAssignedID;

    }
    public Set<account> getAccountset() {
        return accountset;
    }

    public void setAccountset(Set<account> accountset) {
        this.accountset = accountset;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "user{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", ID=" + ID +
                ", accountset=" + accountset +
                '}';
    }
}
