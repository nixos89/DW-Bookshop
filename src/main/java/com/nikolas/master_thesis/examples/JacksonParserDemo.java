package com.nikolas.master_thesis.examples;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;

class PersonJ {
    public final String name;
    public final String place;

    @JsonCreator
    public PersonJ(@JsonProperty("name") String name, @JsonProperty("place") String place) {
        this.name = name;
        this.place = place;
    }

}// Person

public class JacksonParserDemo {

    public static void main(String[] args) throws JsonGenerationException, JsonProcessingException, IOException {
        HashMap<String, String> jsonData = new HashMap<String, String>();
        jsonData.put("name", "sanaulla");
        jsonData.put("place", "bangalore");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(jsonData);
        System.out.println("Json from map: " + jsonString);

        PersonJ person = objectMapper.readValue(jsonString, PersonJ.class);
        System.out.println("Json from Person : " + objectMapper.writeValueAsString(person));
    }

}
