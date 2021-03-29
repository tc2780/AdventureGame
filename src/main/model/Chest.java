package model;

import java.util.ArrayList;

//Represents a chest with exactly 2 items. Acts as a tiny inventory
public class Chest extends Inventory {

    //EFFECTS: makes a chest with exactly 2 items
    public Chest() {
        super(new Item(), new Item());
    }

    //REQUIRES: an array list of exactly 2 items
    //EFFECTS: makes a chest based on array list of items given
    public Chest(ArrayList<Item> items) {
        super(items.get(0), items.get(1));
    }
}
