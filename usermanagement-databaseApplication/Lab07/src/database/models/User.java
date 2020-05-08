package database.models;

public class User {
    private String name;
    private String secondName;
    private String accountNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public User(String name, String secondName, String accountNumber) {
        this.name = name;
        this.secondName = secondName;
        this.accountNumber = accountNumber;
    }
}
