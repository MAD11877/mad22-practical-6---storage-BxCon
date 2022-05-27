package com.example.mad_practical_2;

import java.io.Serializable;
import java.util.Random;

public class User implements Serializable {
    String name;
    String description;
    Integer id;
    Boolean followed;

    public User() {
        Random rnd = new Random();
        Integer Rn = rnd.nextInt(999999999);
        Integer Rd = rnd.nextInt(999999999);

        this.name = "MAD" + Rn;
        this.description = "Description" + Rd;
        this.id = null;
        this.followed = rnd.nextBoolean();
    }

    public User(String name, String description, Integer id, Boolean followed) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.followed = followed;
    }
}
