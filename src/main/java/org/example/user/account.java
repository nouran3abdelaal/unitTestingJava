package org.example.user;

public class account {
  private String accountType;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    private  int ID;
  private  int accountOwnerID;
  private double accountBalance;
    private static int lastAssignedID = 0;

    public account(String accountType, double accountBalance) {
        this.accountType = accountType;
        this.accountBalance = accountBalance;
        this.ID = ++lastAssignedID;

    }

    public account(String accountType, int accountOwnerID, double accountBalance) {
        this.accountType = accountType;
        this.accountOwnerID = accountOwnerID;
        this.accountBalance = accountBalance;
        this.ID = ++lastAssignedID;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getAccountOwnerID() {
        return accountOwnerID;
    }

    public void setAccountOwnerID(int accountOwnerID) {
        this.accountOwnerID = accountOwnerID;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public static int getLastAssignedID() {
        return lastAssignedID;
    }

    @Override
    public String toString() {
        return "account{" +
                "accountType='" + accountType + '\'' +
                ", ID=" + ID +
                ", accountOwnerID=" + accountOwnerID +
                ", accountBalance=" + accountBalance +
                '}';
    }

    public static void setLastAssignedID(int lastAssignedID) {
        account.lastAssignedID = lastAssignedID;
    }
}
