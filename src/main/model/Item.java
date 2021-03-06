package model;

import exceptions.NoSuchItemExistsException;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Random;

//represents an item that has a string name, an int that represents the item, and a
//     a change in health and progress that represents the effects of the item
public class Item implements Writable {
    private String name;            //name of item
    private int changeInHealth;     //item either heals or gives damage
    private int changeInProgress;   //item either raises or lowers progress
    private int num;                //between 1-5, set's up the item and it's characteristics

    //EFFECTS: init change in health and progress to be 0 (some items don't change health and/or progress);
    //         also assigns item a random int between 1-5 which correlates to a specific item;
    //         setUpItem will set fields to match an items effects
    public Item() {
        changeInHealth = 0;
        changeInProgress = 0;
        Random r = new Random();
        num = r.nextInt(5) + 1; //returns num in range [1, 5]
        setUpItem();                   //sets up item
    }

    //constructor for testing purposes, and for easy loading of saved item nums
    //EFFECTS: if n is passed in such that n < 1 or n > 5, throws NoSuchItemExistsException, else
    //         init change in health and progress to be 0 (some items don't change health and/or progress);
    //         also assigns item a random int between 1-5 which correlates to a specific item;
    //         setUpItem will set fields to match an items effects
    public Item(int n) throws NoSuchItemExistsException {
        changeInHealth = 0;
        changeInProgress = 0;
        num = n;
        if (num < 1 || num > 5) {
            throw new NoSuchItemExistsException("There does not exist an item with the number" + num);
        }
        setUpItem();
    }

    //EFFECTS: returns item number
    public int getNum() {
        return num;
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

    //*****REQUIRES: num is between 1-5*** should be set up now so that no number other than 1-5 can be num
    //MODIFIES: this
    //EFFECTS: sets up item according to (random) num assigned in constructor,
    //         - currently, 5 possible items, each which modifies
    //           changeInHealth, changeInProgress and name as needed
    public void setUpItem() {

        if (num == 1) {
            changesForA();
            name = "A purple kiwi";
        } else if (num == 2) {
            changesForB();
            name = "A water bottle filled with an unknown substance";
        } else if (num == 3) {
            changeInProgress = 30;
            name = "A feather that shimmers brown and gold";
        } else if (num == 4) {
            changesForD();
            name = "A book that looks like it's been dipped in mud and dried under the sun";
        } else {
            changeInHealth = -30;
            name = "A ladybug. An actual live animal";
        }
    }

    //methods below to (mostly) help shorten setUpItem

    //MODIFIES: this
    // EFFECTS: the changes that happen when int is 1, sets fields to match item's effects
    private void changesForA() {
        changeInHealth = 20;
        changeInProgress = -20;
    }

    //MODIFIES: this
    // EFFECTS: the changes that happen when int is 1, sets fields to match item's effects
    private void changesForB() {
        changeInProgress = 20;
        changeInHealth = -10;
    }

    //MODIFIES: this
    // EFFECTS: the changes that happen when int is 1, sets fields to match item's effects
    private void changesForD() {
        changeInProgress = -10;
        changeInHealth = 20;
    }

    //EFFECTS: returns item stored as a JSONObject -> by storing its item num
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("item num", num);
        return jsonObject;
    }
}
