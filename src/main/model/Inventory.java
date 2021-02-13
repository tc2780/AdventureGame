package model;

import java.util.ArrayList;

//represents an inventory that can hold items -> an array list of items
public class Inventory {

    private static final int MAX_INVENTORY_SPACE = 3;      //max inventory space
    private ArrayList<Item> items;                   //arraylist of items

    //constructs a new inventory (arraylist of items)
    public Inventory() {
        items = new ArrayList<>();
    }

    //constructor specifically for chest
    public Inventory(Item a, Item b) {
        items = new ArrayList<>();
        items.add(a);
        items.add(b);
    }

    //EFFECTS: returns all items
    public ArrayList<Item> getItems() {
        return items;
    }

    //REQUIRES: an Item to be passed
    //MODIFIES: this
    //EFFECTS: if inventory has space, adds item to items, and returns true;
    //         else does not modify items, and return false;
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
    //MODIFIES: this
    //EFFECTS: return Item in items with given name, and removes from
    //         inventory
    public Item getItem(String name) {
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

    //REQUIRES: there must be an item at the index of i - 1
    //MODIFIES: this
    //EFFECTS: return Item in items with given name, and removes from
    //         inventory
    public Item getItemAtIndex(int i) {
        i--;
        if (i >= items.size() || i < 0) {
            throw new IllegalArgumentException("no item at this index");
        }

        Item item = items.get(i);
        items.remove(item);
        return item;
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
