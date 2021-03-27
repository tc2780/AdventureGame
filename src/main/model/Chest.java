package model;

import java.util.ArrayList;

//Represents a chest with exactly 2 items. Acts as a tiny inventory
public class Chest extends Inventory {

    //EFFECTS: makes a chest with exactly 2 items
    public Chest() {
        super(new Item(), new Item());
    }

    public Chest(ArrayList<Item> items) {
        super(items.get(0), items.get(1));
    }
}
