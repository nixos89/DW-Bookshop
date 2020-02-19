package com.nikolas.master_thesis.examples;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Task {

    public final String title;
    public final String notes;
    public final LocalDateTime deadline;
    public final String assignedTo;

    public Task(String title, String notes, LocalDateTime deadline, String assignedTo) {
        this.title = title;
        this.notes = notes;

        this.deadline = deadline;
        this.assignedTo = assignedTo;
    }

    public static void main(String[] args) {
        List<Long> lista = new ArrayList<>();
        lista.add(2L);
        lista.add(12L);
        lista.add(13L);
        Long catId = 2L;
        System.out.println("(pre) lista = " + lista);
        lista.remove(catId);
        System.out.println("(posle) lista = " + lista);
    }
}
