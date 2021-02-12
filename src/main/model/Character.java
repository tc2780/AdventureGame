package model;

import java.util.ArrayList;

public class Character {
    private String name;
    //  private Inventory inventory;
    private int progress;
    private int health; //[0, 100], once 0, can not raise cause death
    //private ArrayList<String> log;

    //EFFECTS: create new character with given name, base health is 100,
    //         and progress = 0
    public Character(String name) {
        this.name = name;
        //     inventory = new Inventory();
        progress = 0;
        health = 100;
        //log = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Inventory getInventory() {
//        return inventory;
//    }

//    public void setInventory(Inventory i) {
//        this.inventory = i;
//    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int p) {
        progress = p;
    }

    public int getHealth() {
        return health;
    }

    //REQUIRES: 0 <= h <= 100
    //MODIFIES: this (health)
    //EFFECTS: sets health as given health
    public void setHealth(int h) {
        health = h;
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
}
