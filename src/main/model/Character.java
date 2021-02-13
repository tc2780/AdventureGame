package model;

import java.util.ArrayList;

// Represents a character, with a name, progress and health
public class Character {
    private String name;  //characters name
    private int progress; //current progress of character -> [0, 100]
    private int health;   //[0, 100], once 0, can not raise cause death
    //private ArrayList<String> log;

    //EFFECTS: create new character with given name, base health is 100,
    //         and progress = 0
    public Character(String name) {
        this.name = name;
        progress = 0;
        health = 100;
        //log = new ArrayList<>();
    }

    //EFFECTS: returns characters name
    public String getName() {
        return name;
    }

    //MODIFIES: this
    //EFFECTS: sets the characters name to given name
    public void setName(String name) {
        this.name = name;
    }

    //EFFECTS: returns progress
    public int getProgress() {
        return progress;
    }

    //MODIFIES: this
    //EFFECTS: resets progress to given p
    public void setProgress(int p) {
        progress = p;
    }

    //REQUIRES: 0 <= h <= 100
    //MODIFIES: this (health)
    //EFFECTS: sets health as given health
    public void setHealth(int h) {
        health = h;
    }

    //EFFECTS: returns characters current health
    public int getHealth() {
        return health;
    }

    //MODIFIES: this (health)
    //EFFECTS: health raises or lowers depending on num given,
    //         lowest possible health is 0, highest is 100
    public void healOrDamage(int num) {
        if (health + num <= 0) {
            health = 0;
        } else if (health + num >= 100) {
            health = 100;
        } else {
            health += num;
        }
    }

    //MODIFIES: this (progress)
    //EFFECTS: progress raises or lowers depending on num given,
    //         lowest is 0, highest 100
    public void gainLoseProgress(int num) {
        if (progress + num <= 0) {
            progress = 0;
        } else if (progress + num >= 100) {
            progress = 100;
        } else {
            progress += num;
        }
    }

    //EFFECTS: returns false if health is 0, else returns true
    public boolean stillAlive() {
        if (health == 0) {
            return false;
        }
        return true;
    }
}
