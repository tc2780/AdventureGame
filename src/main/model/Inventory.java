package model;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<Item> items;   //arraylist of items

    public Inventory() {
        items = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    //REQUIRES: an Item to be passed
    //MODIFIES: this
    //EFFECTS: adds an object to items
    public void addItem(Item obj) {
        items.add(obj);
    }

    //EFFECTS: return true if item of given name is in items, else false
    public Boolean haveItem(String name) {
        for (Item o : items) {
            if (o.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    //REQUIRES: item of given name to be in items
    //EFFECTS: return Item in items with given name
    public Item getItem(String name) {
        int i = 0;
        for (Item o : items) {
            i++;
            if (o.getName().equals(name)) {
                break;
            }
        }
        return items.get(i);
    }
}
