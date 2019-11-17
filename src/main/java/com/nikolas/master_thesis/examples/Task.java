package com.nikolas.master_thesis.examples;

import java.time.LocalDateTime;

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
}
