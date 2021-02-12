package model;

import java.util.ArrayList;

public class Inventory {

    private static int MAX_INVENTORY_SPACE = 3;

    private ArrayList<Item> items;   //arraylist of items

    public Inventory() {
        items = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    //REQUIRES: an Item to be passed
    //MODIFIES: this
    //EFFECTS: if inventory adds space, adds item to items, and return true,
    //         else return false;
    public boolean addItem(Item obj) {
        if (items.size() < MAX_INVENTORY_SPACE) {
            items.add(obj);
            return true;
        }
        return false;
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
    //EFFECTS: return Item in items with given name, and removes from
    //         inventory
    public Item getItem(String name) throws IllegalArgumentException {
        int i = 0;
        for (Item o : items) {
            if (o.getName().equals(name)) {
                break;
            }
            i++;
        }
        if (!this.haveItem(name)) {
            throw new IllegalArgumentException("can not pass in");
        }
        Item x = items.get(i);
        items.remove(i);
        return x;
    }

    //EFFECTS: returns a string of all the item names
    public String getAllItemNames() {
        if (isEmpty()) {
            return "[]";
        }
        String str = "[";
        if (items.size() > 1) {
            for (int i = 0; i < items.size() - 1; i++) {
                str += items.get(i).getName() + ", ";
            }
        }
        str += items.get(items.size() - 1).getName() + "]";
        return str;
    }

    //EFFECTS: returns length of items
    public int length() {
        return items.size();
    }

    //EFFECTS: returns true if items is empty, false otherwise
    public boolean isEmpty() {
        return items.size() == 0;
    }

    //EFFECTS: returns true if items is full, false otherwise
    public boolean isFull() {
        return items.size() == MAX_INVENTORY_SPACE;
    }
}
