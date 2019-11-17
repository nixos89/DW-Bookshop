package com.nikolas.master_thesis.core;

/* For testing purposes */
public class UserA {

    private int id;

    private String name;

    private Account account; // it must be bi-directional so it can queried both ways

    public UserA() {
    }

    public UserA(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserA(int id, String name, Account account) {
        this.id = id;
        this.name = name;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "UserA{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account= " + account +
                '}';
    }
}
