package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chest extends Inventory {

    //extends inventory cause its like a tiny inventory
    private static int MAX_CHEST_SIZE = 2;
    private List<Item> chest;

    //makes a chest with 2 items
    public Chest() {
        super(new Item(), new Item());
    }
}
