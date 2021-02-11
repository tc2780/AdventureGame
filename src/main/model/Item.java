package model;

import java.util.Random;

public class Item {
    private String name;
    private int changeInHealth; //item either heals or gives damage
    private int changeInProgress;  //item either raises or lowers progress
    private int num; //between 1-5, set's up the item and it's characteristics

    public Item() {
        changeInHealth = 0;
        changeInProgress = 0;
        Random r = new Random();
        num = r.nextInt(5) + 1; //returns num in range [1, 5]
        setUpItem(); //sets up item, and returns name, which is stored
    }

    //constructor for testing purposes
    public Item(int n) {
        changeInHealth = 0;
        changeInProgress = 0;
        num = n;
        setUpItem();
    }

    //EFFECTS: returns change in health of item
    public int getChangeInHealth() {
        return changeInHealth;
    }

    //EFFECTS: return change in progress of item
    public int getChangeInProgress() {
        return changeInProgress;
    }

    //EFFECTS: returns name of item
    public String getName() {
        return name;
    }

    //MODIFIES: this
    //EFFECTS: sets up item according to random num assigned in constructor,
    //         - currently, 5 possible items, each which modifies
    //           changeInHealth and changeInProgress as needed
    public void setUpItem() {
        switch (num) {
            case 1:
                changeInHealth = 20;
                name = "A purple kiwi.";
            case 2:
                changeInProgress = 20;
                changeInHealth = -10;
                name = "A water bottle filled with an unknown substance.";
            case 3:
                changeInProgress = 30;
                name = "A feather that shimmers brown and gold.";
            case 4:
                changeInProgress = 10;
                changeInHealth = 20;
                name = "Seems to be a book with the front cover ripped off.";
            default:
                changeInHealth = -30;
                name = "A ladybug. An actual live animal. That was in a chest.";
        }
    }
}
