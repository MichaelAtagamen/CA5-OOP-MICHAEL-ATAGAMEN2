package com.dkit.oop.sd2.Server;
import com.google.gson.Gson;

/**
 * Main author: Michael Atagamen
 * Other contributors:
 *
 */
public class PlayerDTO {
    private int id;
    private String name;
    private float rating;
    private int age;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // Deserialize JSON String to PlayerDTO object
    public static PlayerDTO fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, PlayerDTO.class);
    }

    // Default Constructor
    public PlayerDTO(String name, float rating, int age) {
        this.name = name;
        this.rating = rating;
        this.age = age;
    }

    // Parameterized Constructor
    public PlayerDTO(int id, String name, float rating, int age) {
        this.id = id;
        this.name = name;
        this.rating = rating;
        this.age = age;
    }

    // Getters and Setters
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // toString method for better representation
    @Override
    public String toString() {
        return "PlayerDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", age=" + age +
                '}';
    }
}

