package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//Represents a chest with exactly 2 items. Acts as a tiny inventory
public class Chest extends Inventory {

//    private static int MAX_CHEST_SIZE = 2;
//    private List<Item> chest;

    //EFFECTS: makes a chest with exactly 2 items
    public Chest() {
        super(new Item(), new Item());
    }
}
