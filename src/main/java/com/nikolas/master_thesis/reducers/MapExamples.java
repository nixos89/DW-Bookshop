package com.nikolas.master_thesis.reducers;

import java.util.HashMap;
import java.util.Map;

public class MapExamples {

    public static void main(String[] args) {
        Map<Integer, String> users = new HashMap<>();
        users.put(1, "Pera");
        users.put(2, "Mika");
        users.put(3, "Zolika");
        users.put(4, "Djoka");

        for (Map.Entry<Integer, String> user : users.entrySet()) {
            System.out.println(user.getKey() + " : " + user.getValue());
        }

        String newUser = users.computeIfAbsent(5, user -> "Novajlija").toUpperCase();
        System.out.println(newUser);

        for (Map.Entry<Integer, String> user : users.entrySet()) {
            System.out.println(user.getKey() + " : " + user.getValue());
        }
    }// main

}
