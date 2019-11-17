package com.nikolas.master_thesis.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonDTO {

    private Long personId;
    private String firstName;
    private String lastName;

    public PersonDTO() {
    }

    public PersonDTO(Long personId, String firstName, String lastName) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @JsonProperty("person_id")
    public long getPersonId() { return personId; }

    @JsonProperty("first_name")
    public String getFirstName() { return firstName; }

    @JsonProperty("last_name")
    public String getLastName() { return lastName; }
}
