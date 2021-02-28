package ui;

import model.*;
import model.Character;
import org.json.JSONObject;
import persistence.JsonReader;
import persistence.JsonWriter;
import persistence.Writable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

//represents the game being played
public class GameApp implements Writable {

    private static final String JSON_STORE = "./data/gameApp.json";

    private Character user;      //the user, has a name, health and progress bar
    private Inventory inventory; //the current inventory
    private Scanner scanner;     //scanner for user input
    private String input;        //used for user input where possible
    private boolean cont;        //true if user continues to play
    private Obstacle obs;        //declaration for each obstacle, instantiated each play to ensure diff scenarios
    private Chest chest;         //declaration of chest, instantiated each time user gets chest to ensure new items
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;




    //EFFECTS: instantiates inventory and scanner. input set to default "". cont is true at this point->
    //         will proceed to run app
    public GameApp() throws FileNotFoundException {
        inventory = new Inventory();
        scanner = new Scanner(System.in);
        cont = true;
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        runApp();
    }

//    public GameApp(Character user, Inventory inventory) throws FileNotFoundException {
//        this.inventory = inventory;
//        this.user = user;
//        scanner = new Scanner(System.in);
//        cont = true;
//        jsonReader = new JsonReader(JSON_STORE);
//        jsonWriter = new JsonWriter(JSON_STORE);
//
//        runApp();
//    }

    public Inventory getInventory() {
        return inventory;
    }

    public Character getCharacter() {
        return user;
    }

    //MODIFIES: this
    //EFFECTS: runs entire game -> this is the basic structure (influenced by the tellerApp example)
    //         - while loop will keep game going if user doesn't quit and is still alive
    public void runApp() {
        intro();
        introCharacter();
        getCommand();
        while (cont && user.stillAlive()) {
            getCommand();
            if (user.getProgress() == 100) {
                winMessage();
                cont = false;
            } else if (user.getHealth() == 0) {
                loseMessage();
                cont = false;
            }
        }
        endMessage();
    }

    //MODIFIES: this
    //EFFECTS: does a step -> involves showing menu for user, and user chooses what they to do next
    //         - S -> will display current status
    //         - I -> user uses inventory
    //         - Q -> sets cont to false (-> user quits and game ends)
    //         - N -> user chooses to change name
    //         - C -> user chooses to continue on journey -> will lead to obstacle
    public void getCommand() {
        displayMainMenu();
        input = scanner.next().toUpperCase();
        while (!(input.equals("S") || input.equals("I") || input.equals(
                "Q") || input.equals("C") || input.equals("N") || input.equals("L") || input.equals("P"))) {
            System.out.println("Invalid command. Please enter q, c, s, n, p, l or i:");
            input = scanner.next().toUpperCase();
        }
        doNextStep();
    }

    public void doNextStep() {
        if (input.equals("S")) {
            System.out.println("Current Status:");
            displayStatus();
        } else if (input.equals("I")) {
            useInventory();
        } else if (input.equals("Q")) {
            System.out.println("You give up on your journey and decide "
                    + "to live out the rest of your days in the forest.");
            cont = false;
        } else if (input.equals("N")) {
            changeName();
        } else if (input.equals("P")) {
            saveGameApp();
        } else if (input.equals("L")) {
            loadGameApp();
        } else {
            doObstacle();
            cont = true;
        }
    }

//    public void doStep() {
//        displayMainMenu();
//        input = scanner.next().toUpperCase();
//        while (!(input.equals("S") || input.equals("I") || input.equals(
//                "Q") || input.equals("C") || input.equals("N") || input.equals("L") || input.equals("P"))) {
//            System.out.println("Invalid command. Please enter q, c, s, n, p, l or i:");
//            input = scanner.next().toUpperCase();
//        }
//        if (input.equals("S")) {
//            System.out.println("Current Status:");
//            displayStatus();
//        } else if (input.equals("I")) {
//            useInventory();
//        } else if (input.equals("Q")) {
//            System.out.println("You give up on your journey and decide "
//                    + "to live out the rest of your days in the forest.");
//            cont = false;
//        } else if (input.equals("N")) {
//            changeName();
//        } else if (input.equals("P")) {
//            saveGameApp();
//        } else if (input.equals("L")) {
//            loadGameApp();
//        } else {
//            doObstacle();
//            cont = true;
//        }
//    }

    //MODIFIES: this
    //EFFECTS: a new obstacle is instantiated for user to do, and gets diplayed.
    //         user chooses an option regarding the obstacle,
    //         and effects are applies to user stats.
    //         At random after the obstacle, a chest will appear, where user will be able to choose an item
    //         to add to inventory. Then, current status will be printed
    public void doObstacle() {
        obs = new Obstacle();
        System.out.println("You continue on your journey.");
        System.out.println(obs.lookUpObstacle());
        System.out.println("You have 3 options:\n" + obs.getOptions() + "\nWhat will you do? (enter a, b, or c)");
        input = scanner.next().toUpperCase();
        while (!input.equals("A") && !input.equals("B") && !input.equals("C")) {
            System.out.println("You did not enter a viable option. Pleaser enter a, b, or c.");
            input = scanner.next().toUpperCase();
        }
        obs.setChosenOption(input);
        System.out.println(obs.getResult() + "\n");
        user.gainLoseHealth(obs.getChangeInHealth());
        user.gainLoseProgress(obs.getChangeInProgress());
        Random r = new Random();
        int ran = r.nextInt(2);
        if (ran == 1) {
            getChest();
        }
        System.out.println("Current Status:");
        displayStatus();
    }

    //MODIFIES: this
    //EFFECTS: a new chest is instantiated for the user. there are a total of 2 items in the chest,
    //         and user can pick up to 1 to add to inventory
    public void getChest() {
        chest = new Chest();
        printChestIntro();
        input = scanner.next();
        while (!input.equals("1") && !input.equals("2") && !input.equals("-1")) {
            System.out.println("You did not enter a viable option. Please enter 1, 2, or -1.");
            input = scanner.next();
        }
        if (input.equals("-1")) {
            System.out.println("You did not pick up an item. The chest looks at you glumly and walks away.\n");
        } else {
            if (inventory.isFull()) {
                System.out.println("Your inventory is full.");
                String saveInput = input;
                chooseGetRidOfItem();
                if (inventory.isFull()) {
                    System.out.println("You did not pick up an item. The chest looks at you glumly and walks away.\n");
                } else {
                    pickFromChestAndDisplayInventory(saveInput);
                }
            } else {
                pickFromChestAndDisplayInventory(input);
            }
        }
    }

    //REQUIRES: chest to be instantiated
    //EFFECTS: prints out you got a chest, names of item in chest, and next possible options
    public void printChestIntro() {
        System.out.println("You got a chest! Items in chest:\n" + chest.getAllItemNames());
        System.out.println("Please enter 1 or 2 to choose an item to pick up. Or -1 if you do not want an item.");
    }

    //REQUIRES: str to be "1" or "2"
    //MODIFIES: this
    //EFFECTS: picks up an item from chest and saves it to inventory. then displays new inventory
    public void pickFromChestAndDisplayInventory(String str) {
        pickUpItemFromChest(str);
        displayInventory();
    }

    //REQUIRES: string index is equal to "1" or "2"
    //MODIFIES: this
    //EFFECTS: (main purpose is to help shorten getChest method)
    //         based on string, will return item in chest at given index
    public void pickUpItemFromChest(String index) {
        int choice = getIndexOfString(index);
        Item chosen = chest.getItemAtSpot(choice);
        System.out.println("You picked up " + chosen.getName().toLowerCase());
        inventory.addItem(chosen);
    }

    //MODIFIES: this
    //EFFECTS: if user wants to, removes an item from inventory
    public void chooseGetRidOfItem() {
        System.out.println("Would you like to get rid of an item? (y/n)");
        input = scanner.next().toLowerCase();
        while (!input.equals("y") && !input.equals("n")) {
            System.out.println("Please enter y/n");
            input = scanner.next();
        }
        if (input.equals("y")) {
            displayInventory();
            System.out.println("What would you like to get rid of? (1, 2, 3, or -1 to quit)");
            input = scanner.next();
            input = check3Choice(input);
            if (input.equals("-1")) {
                System.out.println("You have chosen not to get rid of anything.");
            } else {
                int index = getIndexOfString(input);
                index = checkInIndexOfInventory(index);
                Item getRidOf = inventory.getItemAtSpot(index);
                System.out.println("Getting rid of " + getRidOf.getName().toLowerCase());
                displayInventory();
            }
        } else {
            System.out.println("You have chosen not to get rid of anything.");
        }
    }

    //MODIFIES: this
    //EFFECTS: checks if an item at given index-1 exists. else, will keep asking user to choose different item,
    //         and if it exists, will return the index of that existing item
    public int checkInIndexOfInventory(int index) {
        while (index > inventory.length()) {
            System.out.println("That does not correspond to a filled space in the inventory."
                    + " Please enter a valid number.");
            input = scanner.next();
            input = check3Choice(input);
            index = getIndexOfString(input);
        }
        return index;
    }

    //MODIFIES: this
    //EFFECTS: lets the user use the inventory. however, if inventory is empty, user can't do anything to it yet.
    //          else, user can choose to either get rid of an item, use an item, or exit back to main menu
    public void useInventory() {
        if (inventory.isEmpty()) {
            System.out.println("Inventory is currently empty.\n");
        } else {
            System.out.println("Inventory: " + inventory.getAllItemNames());
            System.out.println("To get rid of an item, input t. To use an item, input u.");
            input = scanner.next().toLowerCase();
            while (!input.equals("t") && !input.equals("u")) {
                System.out.println("You did not enter a valid command. "
                        + "Please enter t to get of an item, or u to use an item.");
                input = scanner.next().toLowerCase();
            }
            if (input.equals("t")) {
                chooseGetRidOfItem();
            } else {
                System.out.println("Please enter 1, 2, or 3 to use item. Or -1 to go back to main menu.");
                input = scanner.next();
                input = check3Choice(input);
                if (!input.equals("-1")) {
                    useItemAt(input);
                }
            }
        }
    }

    //EFFECTS: checks if given string is -1, 1, 2, or 3. if it is not, then will keep asking user
    //         to enter different numbers until it is -1, 1, 2, or 3, and will return the user's input
    private String check3Choice(String choice) {
        while (!choice.equals("-1") && !choice.equals("1") && !choice.equals("2") && !choice.equals("3")) {
            System.out.println("You did not enter a viable option. Please enter 1, 2, 3, or -1.");
            choice = scanner.next();
        }
        return choice;
    }

    //REQUIRES: string choice to be 1, 2, or 3
    //MODIFIES: this
    //EFFECTS: first, gets corresponding index from given string, and checks if there is an item at that index.
    //         if there is not, then checkInIndexOfInventory.
    //         Will retrieve an item from the inventory, and modify users health and progress to reflect
    //         item's effects. Will print out the effects and the new status
    public void useItemAt(String choice) {
        int index = getIndexOfString(choice);
        index = checkInIndexOfInventory(index);
        Item chosenItem = inventory.getItemAtSpot(index);
        System.out.println("You use " + chosenItem.getName().toLowerCase() + ".");
        user.gainLoseProgress(chosenItem.getChangeInProgress());
        user.gainLoseHealth(chosenItem.getChangeInHealth());
        System.out.println("Item gives " + chosenItem.getChangeInHealth() + "HP and "
                + chosenItem.getChangeInProgress() + " progress.");
        System.out.println();
        System.out.println("New Status:");
        displayStatus();
    }

    //REQUIRES: str to be "1", "2", or "3"
    //EFFECTS: if string is "1", return 1.
    //         if string is "2", returns 2.
    //         if string is "3", returns 3
    public int getIndexOfString(String str) {
        if (str.equals("1")) {
            return 1;
        } else if (str.equals("2")) {
            return 2;
        } else {
            return 3;
        }
    }

    //MODIFIES: this
    //EFFECTS: changes the users name to a string input from user
    public void changeName() {
        System.out.println("What would you like to change your name to?");
        String name = scanner.next();
        user.setName(name);
        System.out.println("Name successfully changed.\n");
    }

    //EFFECTS: prints out the current inventory
    public void displayInventory() {
        System.out.println("Current Inventory: ");
        System.out.println(inventory.getAllItemNames());
        System.out.println();
    }

    //EFFECTS: prints out the current status of the user
    public void displayStatus() {
        System.out.println("Name: " + user.getName());
        System.out.println("Health: " + user.getHealth() + "HP");
        System.out.println("Progress: " + user.getProgress() + "%");
        System.out.println();
    }

    //EFFECTS: prints out the main menu
    public void displayMainMenu() {
        System.out.println("Main Menu: (please enter a letter)");
        System.out.println("- Status(s)\n- Inventory(i)\n- Continue on journey(c)\n- Change name(n) \n- Quit(q)"
                + "\n- Load saved progress(l)\n- Save progress(p)");
    }

    //EFFECTS: prints out the introduction to the game
    public void intro() {
        System.out.println("Welcome to The Forest of NAME TO BE DETERMINED!");
        System.out.println("You are surrounded by trees, and don't know the way out.");
        System.out.println("Your status is currently at 100HP, and 0% progress.");
        System.out.println("Inventory is currently at 0/3 items.");
        System.out.println("Items will be able to raise or lower status bars. "
                + "However, specific effects of each item are unknown to you.");
        System.out.println("To make it out of the forest, try to get"
                + " progress to 100% by choosing the correct option in each scenario.");
        System.out.println("If health ever hits 0, you die and the game is over.");
        System.out.println();
    }

    //MODIFIES: this
    //EFFECTS: takes in a string from the user to instantiate the user character with the given name
    //         prints out small intro to character
    public void introCharacter() {
        System.out.println("Please enter a name for your character:");
        user = new Character(scanner.next());

        System.out.println("Hi " + user.getName() + "! What a great name.");
        System.out.println("After making sure everything is in order, you start on your journey.");
        System.out.println();
    }

    //EFFECTS: prints out the winning message -> when progress is 100%
    public void winMessage() {
        System.out.println("Your progress has reached 100%.\nYou've made it out!");
        System.out.println("Unfortunately, all you can see are endless hills of sand."
                + " There is nothing outside the forest.");
        System.out.println();
    }

    //EFFECTS: prints out the losing message -> health has reached 0HP
    public void loseMessage() {
        System.out.println("Oh no! Your health has reached 0HP.");
        System.out.println("You succumb to the dangers of the forest and die.");
        System.out.println();
    }

    //EFFECTS: prints out the ending message for the game, and the end stats of the user
    public void endMessage() {
        System.out.println("Thanks for playing!\nEnding stats: ");
        displayStatus();
    }

    private void saveGameApp() {
        try {
            GameAppData g = new GameAppData(user, inventory);
            jsonWriter.open();
            jsonWriter.write(g);
            jsonWriter.close();
            System.out.println("Saved game progress to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void loadGameApp() {
        try {
            GameAppData data = jsonReader.read();
            user = data.getUser();
            inventory = data.getInventory();
            System.out.println("Loaded saved progress from" + JSON_STORE + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    @Override
    public JSONObject toJson() {
        GameAppData g = new GameAppData(user, inventory);
        JSONObject jsonObject = g.toJson();

        return jsonObject;
    }
}
