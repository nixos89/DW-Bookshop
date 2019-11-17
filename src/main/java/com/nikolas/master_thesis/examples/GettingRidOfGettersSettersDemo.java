package com.nikolas.master_thesis.examples;

import java.time.LocalDateTime;

public class GettingRidOfGettersSettersDemo {

    public static void main(String[] args) {
        // use of Factory method to initialize data
        Task task1 = new Task("Task 1", "some notes", LocalDateTime.now().plusDays(5), "sana" );
        // clear approach to class fields without getter methods
        System.out.println(task1.title + " assigned to " + task1.assignedTo);
        Task task2 = new Task("Task 2", "some notes", LocalDateTime.now().plusDays(6), "raj" );
        System.out.println(task2.title + " assigned to " + task2.assignedTo);
    }
}
