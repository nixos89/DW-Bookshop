package com.nikolas.master_thesis.core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/* For testing purposes */
public class Account {

    private int id;

    private String name;

    private List<UserA> userAS;

    public Account() {
        userAS = new ArrayList<>(); // added to resolve issue with NPE
    }

//    public Account(String name) {
//        this.name = name;
//    }
//
//    public Account(int id, String name) {
//        this.id = id;
//        this.name = name;
//    }

    public Account(int id, String name, List<UserA> userAS) {
        this.id = id;
        this.name = name;
        this.userAS = userAS;
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

    @JsonProperty("user_a_s")
    public List<UserA> getUserAS() {
        return userAS;
    }

    public void setUserAS(List<UserA> userAS) {
        this.userAS = userAS;
    }

    public void addUser(UserA u) {
        userAS.add(u);
    }

    @Override
    public String toString() {
        return "Account {" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
