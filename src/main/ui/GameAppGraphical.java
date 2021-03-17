package ui;

import model.*;
import model.Character;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

//represents the game being played
public class GameAppGraphical extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/gameApp.json";

    private Character user;      //the user, has a name, health and progress bar
    private Inventory inventory; //the current inventory
    private Scanner scanner;     //scanner for user input
    private String input;        //used for user input where possible
    private boolean cont;        //true if user continues to play
    private Obstacle obs;        //declaration for each obstacle, instantiated each play to ensure diff scenarios
    private Chest chest;         //declaration of chest, instantiated each time user gets chest to ensure new items
    private JsonWriter jsonWriter;  //writer to save data to file
    private JsonReader jsonReader;  //reader to read data from file

    private JTextArea textToDisplay;
    private JTextField field;

    private PictureDisplayPanel pictureDisplayPanel;
    private InventoryAndChestDisplayPanel inventoryAndChestDisplayPanel;

    private static final int LABEL_WIDTH = 200;
    private static final int LABEL_HEIGHT = 50;

    private static final int BOX_HEIGHT = 1000;
    private static final int BOX_WIDTH = 1000;

    private int itemUseChoice; //1 if is to be picked up, 2 if to be used, 3 if to be get rid of


    private JPanel obstacleChoicePanel;
    private JPanel mainMenuPanel;
    private JPanel inventoryChoicePanel;
    private JPanel chestChoicePanel;
    private JPanel inventoryMenuPanel;
    private JPanel enterNamePanel;
    private JPanel changeNamePanel;

    //EFFECTS: instantiates inventory and scanner. input set to default "". cont is true at this point->
    //         will proceed to run app
    public GameAppGraphical() throws FileNotFoundException {
        super("Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inventory = new Inventory();
        scanner = new Scanner(System.in);
        cont = true;
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);
//        actionInput = "";


//        textToDisplay = new JLabel("hello");
        field = new JTextField(10);
        field.setMaximumSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));

//        makeObstacleChoicePanel();
        makeMainMenuPanel();
        makeInventoryChoicePanel();
        makeChestChoicePanel();
        makeInventoryMenuPanel();
        makeEnterNamePanel();


//        inventory.addItem(new Item(1));
//        inventory.addItem(new Item(2));
//        inventory.addItem(new Item(4));
//        inventoryAndChestDisplayPanel = new InventoryAndChestDisplayPanel(inventory); //TODO: remove after testing

        pictureDisplayPanel = new PictureDisplayPanel();


//        add(textToDisplay, BorderLayout.CENTER);
        add(pictureDisplayPanel, BorderLayout.NORTH);
//        add(enterNamePanel, BorderLayout.SOUTH);

//        add(obstacleChoicePanel, BorderLayout.AFTER_LAST_LINE);
//        add(mainMenuPanel);
//        add(chestChoicePanel, BorderLayout.SOUTH);
//        add(inventoryChoicePanel, BorderLayout.SOUTH);
        pack();

        setSize(BOX_WIDTH, BOX_HEIGHT);
        centreOnScreen();
        setVisible(true);
        user = new Character("");

        runApp();
    }

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
//        if (actionInput.equals("ok for name")) {
//            user = new Character(field.getText());
//            welcomeCharacter();
//            remove(enterNamePanel);
//            getCommand();
//            while (cont && user.stillAlive()) {
//                getCommand();
//                if (user.getProgress() == 100) {
//                    winMessage();
//                    cont = false;
//                } else if (user.getHealth() == 0) {
//                    loseMessage();
//                    cont = false;
//                }
//            }
//        }
//        endMessage();
    }

    //MODIFIES: this
    //EFFECTS: does a step -> involves showing menu for user, and user chooses what they to do next
    //         - S -> will display current status
    //         - I -> user uses inventory
    //         - Q -> sets cont to false (-> user quits and game ends)
    //         - N -> user chooses to change name
    //         - C -> user chooses to continue on journey -> will lead to obstacle
    //         - L -> loads data from saved file
    //         - P -> saves users status and inventory to file
    //         this method in particular gets a valid command from user before continuing
//    public void getCommand() {
//        displayMainMenu();
//        input = scanner.next().toUpperCase();
//        while (!(input.equals("S") || input.equals("I") || input.equals(
//                "Q") || input.equals("C") || input.equals("N") || input.equals("L") || input.equals("P"))) {
//            System.out.println("Invalid command. Please enter q, c, s, n, p, l or i:");
//            input = scanner.next().toUpperCase();
//        }
//        doNextStep();
//    }

    public void getCommand() {
//        remove(textToDisplay);
//        textToDisplay = new JTextArea("Main menu:");
//        add(textToDisplay, BorderLayout.CENTER);
//
//        remove(enterNamePanel);
        add(mainMenuPanel, BorderLayout.SOUTH);
        validate();
    }

    //REQUIRES: input is a valid input (either S, I, Q, N, C, L, or P)
    //MODIFIES: this
    //EFFECTS: does a step based on what input is
    public void doNextStep() {
        if (input.equals("S")) {
            System.out.println("Current Status:");
            getStatusMessage();
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
            introObstacle();
            cont = true;
        }
    }

    //MODIFIES: this
    //EFFECTS: a new obstacle is instantiated for user to do, and gets diplayed.
    //         user chooses an option regarding the obstacle,
    //         and effects are applies to user stats.
    //         At random after the obstacle, a chest will appear, where user will be able to choose an item
    //         to add to inventory. Then, current status will be printed
    public void introObstacle() {
        obs = new Obstacle();
//        System.out.println("You continue on your journey.");
//        System.out.println(obs.lookUpObstacle());
//        System.out.println("You have 3 options:\n" + obs.getOptions() + "\nWhat will you do? (enter a, b, or c)");
//        input = scanner.next().toUpperCase();
//        while (!input.equals("A") && !input.equals("B") && !input.equals("C")) {
//            System.out.println("You did not enter a viable option. Pleaser enter a, b, or c.");
//            input = scanner.next().toUpperCase();
//        }
//        obs.setChosenOption(input);
//        System.out.println(obs.getResult() + "\n");
//        user.gainLoseHealth(obs.getChangeInHealth());
//        user.gainLoseProgress(obs.getChangeInProgress());
//        Random r = new Random();
//        int ran = r.nextInt(2);
//        if (ran == 1) {
//            getChest();
//        }
//        System.out.println("Current Status:");
//        getStatusMessage();
        remove(textToDisplay);
        remove(pictureDisplayPanel);
        remove(mainMenuPanel);
        String text = "You continue on your journey.\n\n" + obs.lookUpObstacle()
                + "\n\nWhat will you do?\n" + obs.getOptions();
        textToDisplay = new JTextArea(text, 10, 100);
        textToDisplay.setEditable(false);
        pictureDisplayPanel.setObsImage(obs);

        makeObstacleChoicePanel();

        add(pictureDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
        add(obstacleChoicePanel, BorderLayout.SOUTH);
        validate();
    }


    //TODO: refactor so shows obs outro, and then a continue button that either goes to main menu, gets a chest, or death
    private void doObsOutro() {
        remove(pictureDisplayPanel);
        remove(textToDisplay);
        remove(obstacleChoicePanel);
        pictureDisplayPanel.setObsImage(obs);
        user.gainLoseProgress(obs.getChangeInProgress());
        user.gainLoseHealth(obs.getChangeInHealth());

        JPanel temp = new JPanel();
        JButton ok = new JButton("Continue");
        ok.setActionCommand("get result of obs"); //command for displayMain meny, basically go back
        ok.addActionListener(this);
        temp.add(ok);
        textToDisplay = new JTextArea(obs.getResult());
        textToDisplay.setEditable(false);
        add(pictureDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
        add(temp, BorderLayout.SOUTH);
        validate();


//        Random r = new Random();
//        int ran = r.nextInt(2);   //TODO: uncomment aftertesting
//        if (ran == 1) {  //no chest here, so go back to main menu after
//            JPanel temp = new JPanel();
//            JButton ok = new JButton("Accept changes");
//            ok.setActionCommand("-1"); //command for displayMain meny, basically go back
//            ok.addActionListener(this);
//            temp.add(ok);
//            textToDisplay = new JTextArea(obs.getResult());
//            textToDisplay.setEditable(false);
//            add(pictureDisplayPanel, BorderLayout.NORTH);
//            add(textToDisplay, BorderLayout.CENTER);
//            add(temp, BorderLayout.SOUTH);
//            validate();
//        } else { //get Chest
//            add(pictureDisplayPanel, BorderLayout.NORTH);
//            textToDisplay = new JTextArea(obs.getResult() + "\nYou've found a chest! Click open to view items inside.");
//            textToDisplay.setEditable(false);
//
//            JPanel temp = new JPanel();
//            JButton openChest = new JButton("Open the chest!");
//            temp.add(openChest);
//            openChest.setActionCommand("opening the chest");
//            openChest.addActionListener(this);
//
//            add(pictureDisplayPanel, BorderLayout.NORTH);
//            add(textToDisplay, BorderLayout.CENTER);
//            add(temp, BorderLayout.SOUTH);
//            validate();
//    }
    }

    //TODO: death, win,  chest, or main menu
    private void resultOfObs() {
        getContentPane().removeAll();

        Random r = new Random();
        int ran = r.nextInt(2);
        if (user.getHealth() == 0) {
            textToDisplay = new JTextArea("Oh no! Your health has reached 0."
                    + "You succumb to the dangers of the forest and die");
            pictureDisplayPanel.setImage("bad ending");
            add(pictureDisplayPanel, BorderLayout.NORTH);
            add(textToDisplay, BorderLayout.CENTER);
        } else if (user.getProgress() == 100) {
            textToDisplay = new JTextArea("Your progress has reached 100%.\nYou've made it out!\nUnfortunately, "
                    + "all you can see are endless hills of sand.\nThere is nothing outside the forest.");
            pictureDisplayPanel.setImage("good end");
            add(pictureDisplayPanel, BorderLayout.NORTH);
            add(textToDisplay, BorderLayout.CENTER);
        } else if (ran == 1) { //no chest here, so go back to main menu after
            JPanel temp = new JPanel();
            JButton ok = new JButton("Accept changes");
            ok.setActionCommand("-1"); //command for displayMain meny, basically go back
            ok.addActionListener(this);
            temp.add(ok);
            textToDisplay = new JTextArea(obs.getResult() + "Fortunately these changes will not cause you to die.");
            textToDisplay.setEditable(false);
            add(pictureDisplayPanel, BorderLayout.NORTH);
            add(textToDisplay, BorderLayout.CENTER);
            add(temp, BorderLayout.SOUTH);
            textToDisplay = new JTextArea("Main menu");
        } else {
            textToDisplay = new JTextArea(obs.getResult() + "\nYou've found a chest! Click open to view items inside.");
            textToDisplay.setEditable(false);
            pictureDisplayPanel.setImage("chest img");

            JPanel temp = new JPanel();
            JButton openChest = new JButton("Open the chest!");
            temp.add(openChest);
            openChest.setActionCommand("opening the chest");
            openChest.addActionListener(this);

            add(pictureDisplayPanel, BorderLayout.NORTH);
            add(textToDisplay, BorderLayout.CENTER);
            add(temp, BorderLayout.SOUTH);
        }
        validate();
    }

    //MODIFIES: this
    //EFFECTS: a new chest is instantiated for the user. there are a total of 2 items in the chest,
    //         and user can pick up to 1 to add to inventory
    public void getChest() {
        chest = new Chest();
//        printChestIntro();
//        input = scanner.next();
//        while (!input.equals("1") && !input.equals("2") && !input.equals("-1")) {
//            System.out.println("You did not enter a viable option. Please enter 1, 2, or -1.");
//            input = scanner.next();
//        }
//        if (input.equals("-1")) {
//            System.out.println("You did not pick up an item. The chest looks at you glumly and walks away.\n");
//        } else {
//            if (inventory.isFull()) {
//                System.out.println("Your inventory is full.");
//                String saveInput = input;
//                chooseGetRidOfItem();
//                if (inventory.isFull()) {
//                    System.out.println("You did not pick up an item. The chest looks at you glumly and walks away.\n");
//                } else {
//                    pickFromChestAndDisplayInventory(saveInput);
//                }
//            } else {
//                pickFromChestAndDisplayInventory(input);
//            }
//        }
        getContentPane().removeAll();
        String text = "Items in chest:\n" + chest.getAllItemNames() + "\nPlease choose an item.";
        textToDisplay = new JTextArea(text);
        textToDisplay.setEditable(false);
        makeChestChoicePanel();
        Chest temp = new Chest(chest.getItems());
        inventoryAndChestDisplayPanel = new InventoryAndChestDisplayPanel(temp, 1);

        add(inventoryAndChestDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
        add(chestChoicePanel, BorderLayout.SOUTH);

        validate();
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
    public void pickFromChestAndDisplayInventory(int spot) { //TODO: add in if inventory is full option
//        pickUpItemFromChest(str);
//        displayInventory();
//        int choice = getIndexOfString(str);
        Item chosen = chest.getItemAtSpot(spot);
        if (inventory.isFull()) {
            textToDisplay = new JTextArea("Could not add item to inventory as inventory is currently full.");
        } else {
            inventory.addItem(chosen);
            textToDisplay = new JTextArea("Current Inventory:\n" + inventory.getAllItemNames());
        }
        textToDisplay.setEditable(false);

        getContentPane().removeAll();
        pictureDisplayPanel.setImage("static img");
        makeMainMenuPanel();

        add(pictureDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
        add(mainMenuPanel, BorderLayout.SOUTH);
        validate();
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
    public void chooseGetRidOfItem(int choice) {
//        System.out.println("Would you like to get rid of an item? (y/n)");
//        input = scanner.next().toLowerCase();
//        while (!input.equals("y") && !input.equals("n")) {
//            System.out.println("Please enter y/n");
//            input = scanner.next();
//        }
//        if (input.equals("y")) {
//            displayInventory();
//            System.out.println("What would you like to get rid of? (1, 2, 3, or -1 to quit)");
//            input = scanner.next();
//            input = check3Choice(input);
//            if (input.equals("-1")) {
//                System.out.println("You have chosen not to get rid of anything.");
//            } else {
//                int index = getIndexOfString(input);
//                index = checkInIndexOfInventory(index);
//                Item getRidOf = inventory.getItemAtSpot(index);
//                System.out.println("Getting rid of " + getRidOf.getName().toLowerCase());
//                displayInventory();
//            }
//        } else {
//            System.out.println("You have chosen not to get rid of anything.");
//        }
        if (choice > inventory.length()) {
            remove(textToDisplay);
            textToDisplay = new JTextArea("There are currently no items at that spot. Please choose an item you have."
                    + "\n\nInventory:\n" + inventory.getAllItemNames());
            add(textToDisplay, BorderLayout.CENTER);
            textToDisplay = new JTextArea("Main menu");
        } else {
            getContentPane().removeAll();
            makeMainMenuPanel();
            pictureDisplayPanel.setImage("static img");
            Item getRidOf = inventory.getItemAtSpot(choice);

            String str = "Threw away " + getRidOf.getName().toLowerCase()
                    + ".\n\nCurrent inventory:\n" + inventory.getAllItemNames();
            textToDisplay = new JTextArea(str);
            add(pictureDisplayPanel, BorderLayout.NORTH);
            add(textToDisplay, BorderLayout.CENTER);
            add(mainMenuPanel, BorderLayout.SOUTH);
        }
        validate();


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
//        if (inventory.isEmpty()) {
//            System.out.println("Inventory is currently empty.\n");
//        } else {
//            System.out.println("Inventory: " + inventory.getAllItemNames());
//            System.out.println("To get rid of an item, input t. To use an item, input u.");
//            input = scanner.next().toLowerCase();
//            while (!input.equals("t") && !input.equals("u")) {
//                System.out.println("You did not enter a valid command. "
//                        + "Please enter t to get of an item, or u to use an item.");
//                input = scanner.next().toLowerCase();
//            }
//            if (input.equals("t")) {
//                chooseGetRidOfItem();
//            } else {
//                System.out.println("Please enter 1, 2, or 3 to use item. Or -1 to go back to main menu.");
//                input = scanner.next();
//                input = check3Choice(input);
//                if (!input.equals("-1")) {
//                    useItemAt(input);
//                }
//            }
//        }
        getContentPane().removeAll();
        if (inventory.isEmpty()) {
            textToDisplay = new JTextArea("Inventory is currently empty");
            pictureDisplayPanel.setImage("static img");
            makeMainMenuPanel();

            add(pictureDisplayPanel, BorderLayout.NORTH);
            add(textToDisplay, BorderLayout.CENTER);
            add(mainMenuPanel, BorderLayout.SOUTH);
            validate();
        } else {
            textToDisplay = new JTextArea("Items currently in inventory:\n" + inventory.getAllItemNames());
            inventoryAndChestDisplayPanel = new InventoryAndChestDisplayPanel(inventory.getItems(), 2);
            makeInventoryMenuPanel();

            add(inventoryAndChestDisplayPanel, BorderLayout.NORTH);
            add(textToDisplay, BorderLayout.CENTER);
            add(inventoryMenuPanel, BorderLayout.SOUTH);
            validate();
            textToDisplay = new JTextArea("Main menu");
        }
    }

    private void pickItemFromInventory() {
        String temp = "";
        if (itemUseChoice == 2) {
            temp = "to use:\n";
        } else {
            temp = "to get rid of:\n";
        }
        getContentPane().removeAll();
        inventoryAndChestDisplayPanel = new InventoryAndChestDisplayPanel(inventory.getItems(), 2);
        makeInventoryChoicePanel();
        textToDisplay = new JTextArea("Please choose an item " + temp + inventory.getAllItemNames());

        add(inventoryAndChestDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
        add(inventoryChoicePanel, BorderLayout.SOUTH);
        validate();
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
    public void useItemAt(int choice) {
//        int index = getIndexOfString(choice);
//        index = checkInIndexOfInventory(index);
//        Item chosenItem = inventory.getItemAtSpot(index);
//        System.out.println("You use " + chosenItem.getName().toLowerCase() + ".");
//        user.gainLoseProgress(chosenItem.getChangeInProgress());
//        user.gainLoseHealth(chosenItem.getChangeInHealth());
//        System.out.println("Item gives " + chosenItem.getChangeInHealth() + "HP and "
//                + chosenItem.getChangeInProgress() + " progress.");
//        System.out.println();
//        System.out.println("New Status:");
//        getStatusMessage();

        if (choice > inventory.length()) {
            remove(textToDisplay);
            textToDisplay = new JTextArea("There are currently no items at that spot. Please choose an item you have."
                    + "\n\nInventory:\n" + inventory.getAllItemNames());
            add(textToDisplay, BorderLayout.CENTER);
        } else {
            getContentPane().removeAll();
            makeMainMenuPanel();
            pictureDisplayPanel.setImage("static img");
            Item chosenItem = inventory.getItemAtSpot(choice);
            user.gainLoseProgress(chosenItem.getChangeInProgress());
            user.gainLoseHealth(chosenItem.getChangeInHealth());
            String str = "You use " + chosenItem.getName().toLowerCase() + ".\nIt gives "
                    + chosenItem.getChangeInHealth() + "HP and "
                    + chosenItem.getChangeInProgress() + " progress.\n\nNew status:\n" + getStatusMessage()
                    + "\n\nCurrent Inventory:\n" + inventory.getAllItemNames();
            textToDisplay = new JTextArea(str);
            add(pictureDisplayPanel, BorderLayout.NORTH);
            add(textToDisplay, BorderLayout.CENTER);
            add(mainMenuPanel, BorderLayout.SOUTH);
        }
        validate();
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
        remove(textToDisplay);
        remove(mainMenuPanel);
        textToDisplay = new JTextArea("You've chosen to change your current name.\n"
                + " What would you like to change it to?");
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);
        add(changeNamePanel, BorderLayout.SOUTH);
        validate();
    }

    //EFFECTS: prints out the current inventory
    public void displayInventory() {
        System.out.println("Current Inventory: ");
        System.out.println(inventory.getAllItemNames());
        System.out.println();
    }

    //EFFECTS: prints out the current status of the user
    public String getStatusMessage() {
        return "Name: " + user.getName() + "\nHealth: " + user.getHealth()
                + "HP\nProgress: " + user.getProgress() + "%";
    }

    public void displayCurrentStatus() {
        remove(textToDisplay);
        textToDisplay = new JTextArea("Current Status:\n" + getStatusMessage());
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);
        validate();
    }

    //REQUIRES: textTODIsplay is what is needed to be displayed //TODO: make sure is true
    //EFFECTS: prints out the main menu
    public void displayMainMenu() {
        getContentPane().removeAll();

        pictureDisplayPanel.setImage("static img");
        add(pictureDisplayPanel, BorderLayout.NORTH);
//        textToDisplay = new JTextArea("Main Menu");
//        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);
        makeMainMenuPanel();
        add(mainMenuPanel, BorderLayout.SOUTH);
        validate();
    }

    public void intro() {
        String introText = "Welcome to The Forest of NAME TO BE DETERMINED!\nYou are surrounded by trees,"
                + " and don't know the way out.\nYour status is currently at 100HP, and 0% progress.\nInventory is"
                + " currently at 0/3 items.\nItems will be able to raise or lower status bars. However, specific "
                + "effects of each "
                + "item are unknown to you.\nTo make it out of the forest, try to get progress to 100% by choosing "
                + "the correct option in each scenario.\nIf health ever hits 0, you die and the game is over.\n";
        textToDisplay = new JTextArea(introText, 5, 100);
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);
    }

    public void introCharacter() {
        add(enterNamePanel, BorderLayout.SOUTH);
    }

    public void welcomeCharacter() {
        remove(textToDisplay);
        textToDisplay = new JTextArea("Hi " + user.getName() + "! What a great name.\nAfter making sure everything is"
                + " in order, you start on your journey.", 5, 100);
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);
        validate();
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
//        System.out.println("Thanks for playing!\nEnding stats: ");
//        displayStatus();

        remove(mainMenuPanel);
        remove(pictureDisplayPanel);
        remove(textToDisplay);
        pictureDisplayPanel.setImage("static img"); //TODO: make a thanks for playing image
        add(pictureDisplayPanel, BorderLayout.NORTH);
        textToDisplay = new JTextArea("Thanks for playing!\nEnding stats:\n" + getStatusMessage());
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);

        validate();
    }


    //EFFECTS: creates GameAppData (user and inventory) based off of this, and saves as JSONObbject to JSON_STORE,
    //         will throw file not found exception if file with name JSON_STORE not found
    private void saveGameApp() {
        remove(textToDisplay);
        try {
            GameAppData g = new GameAppData(user, inventory);
            jsonWriter.open();
            jsonWriter.write(g);
            jsonWriter.close();
            textToDisplay = new JTextArea("Saved game progress to " + JSON_STORE);
//            System.out.println("Saved game progress to " + JSON_STORE + "\n");
        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
            textToDisplay = new JTextArea("Unable to write to file: " + JSON_STORE);
        }
        textToDisplay.setEditable(false);
        add(textToDisplay);
        validate();
    }

    //MODIFIES: this
    //EFFECTS: loads GameAppData from file, modifies user and inventory to match saved stats.
    //         if file with JSON_STORE name is not found, IOException is thrown
    private void loadGameApp() {
        remove(textToDisplay);
        try {
            GameAppData data = jsonReader.read();
            user = data.getUser();
            inventory = data.getInventory();
//            System.out.println("Loaded saved progress from" + JSON_STORE + "\n");
//            System.out.println("New status: ");
            textToDisplay = new JTextArea("Loaded saved progress from" + JSON_STORE
                    + "\n\nNew status:\n" + getStatusMessage());
        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
            textToDisplay = new JTextArea("Unable to read from file: " + JSON_STORE);
        }
        textToDisplay.setEditable(false);
        add(textToDisplay);
        validate();
    }

    private void makeObstacleChoicePanel() {
        obstacleChoicePanel = new JPanel();
//        obstacleChoicePanel.setBackground(new Color(255, 255, 255));
//        obstacleChoicePanel.setPreferredSize(new Dimension(BOX_WIDTH, 200));

//        JLabel prompt = new JLabel(obs.getOptions());
//        prompt.setSize(new Dimension(BOX_WIDTH, LABEL_HEIGHT));

        JButton choiceA = new JButton("A");
        JButton choiceB = new JButton("B");
        JButton choiceC = new JButton("C");

        choiceA.setActionCommand("obs choice A");
        choiceB.setActionCommand("obs choice B");
        choiceC.setActionCommand("obs choice C");

        choiceA.addActionListener(this);
        choiceB.addActionListener(this);
        choiceC.addActionListener(this);

//        obstacleChoicePanel.add(prompt);
        obstacleChoicePanel.add(choiceA);
        obstacleChoicePanel.add(choiceB);
        obstacleChoicePanel.add(choiceC);
    }

    private void makeMainMenuPanel() {
        mainMenuPanel = new JPanel();
        JLabel menuTitle = new JLabel("Main menu");
        mainMenuPanel.add(menuTitle, BorderLayout.NORTH);
        mainMenuPanel.setSize(BOX_WIDTH, LABEL_HEIGHT);
        JButton save = new JButton("Save");
        JButton load = new JButton("Load");
        JButton quit = new JButton("Quit");
        JButton changeName = new JButton("Change Name");
        JButton cont = new JButton("Continue on Journey");
        JButton invent = new JButton("Inventory");
        JButton stats = new JButton("Status");

        save.setActionCommand("P");
        load.setActionCommand("L");
        quit.setActionCommand("Q");
        changeName.setActionCommand("N");
        cont.setActionCommand("C");
        invent.setActionCommand("I");
        stats.setActionCommand("S");

        addLisAndButtonToMenu(save, load, quit, changeName, cont, invent, stats);
    }

    private void addLisAndButtonToMenu(JButton sa, JButton l, JButton q, JButton cn, JButton c, JButton i, JButton s) {

        sa.addActionListener(this);
        l.addActionListener(this);
        q.addActionListener(this);
        cn.addActionListener(this);
        c.addActionListener(this);
        i.addActionListener(this);
        s.addActionListener(this);

        mainMenuPanel.add(c);
        mainMenuPanel.add(s);
        mainMenuPanel.add(cn);
        mainMenuPanel.add(i);
        mainMenuPanel.add(sa);
        mainMenuPanel.add(l);
        mainMenuPanel.add(q);
    }

    private void makeInventoryChoicePanel() {
        inventoryChoicePanel = new JPanel();
        inventoryChoicePanel.setSize(BOX_WIDTH, LABEL_HEIGHT);

        JButton button1 = new JButton("1");
        JButton button2 = new JButton("2");
        JButton button3 = new JButton("3");
        JButton back = new JButton("Back");

        button1.setActionCommand("1");
        button2.setActionCommand("2");
        button3.setActionCommand("3");
        back.setActionCommand("-1");

        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        back.addActionListener(this);

        inventoryChoicePanel.add(button1);
        inventoryChoicePanel.add(button2);
        inventoryChoicePanel.add(button3);
        inventoryChoicePanel.add(back);

    }

    private void makeInventoryMenuPanel() {
        inventoryMenuPanel = new JPanel();
        inventoryMenuPanel.setSize(BOX_WIDTH, LABEL_HEIGHT);

        JButton useItem = new JButton("Use Item");
        JButton getRidOfItem = new JButton("Get rid of item");
//        JButton back = new JButton("Back");

        useItem.setActionCommand("U");
        getRidOfItem.setActionCommand("T");

        useItem.addActionListener(this);
        getRidOfItem.addActionListener(this);

        inventoryMenuPanel.add(useItem);
        inventoryMenuPanel.add(getRidOfItem);
    }

    private void makeChestChoicePanel() {
        chestChoicePanel = new JPanel();
        JButton item1 = new JButton("Pick up item 1");
        JButton item2 = new JButton("Pick up item 2");
        JButton no = new JButton("Do not pick up an item");

        item1.setActionCommand("1");
        item2.setActionCommand("2");
        no.setActionCommand("-1");

        item1.addActionListener(this);
        item2.addActionListener(this);
        no.addActionListener(this);

        chestChoicePanel.add(item1);
        chestChoicePanel.add(item2);
        chestChoicePanel.add(no);

    }

    private void makeChangeNamePanel() {
        changeNamePanel = new JPanel();
        JButton enter = new JButton("Continue");
        enter.setActionCommand("change name");
        enter.addActionListener(this);

        field = new JTextField(10);
        changeNamePanel.add(field);
        changeNamePanel.add(enter);

    }

    private void makeEnterNamePanel() {
        enterNamePanel = new JPanel();

        JLabel text = new JLabel("Please enter a name for your character:");

        JButton ok = new JButton("Continue");

        ok.setActionCommand("ok for name");

        ok.addActionListener(this);
//        ok.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
////                if (e.getActionCommand().equals("ok for name")) {
//                user.setName(field.getText());
//                welcomeCharacter();
////                }
//            }
//        });

//        enterNamePanel.setLayout(new BoxLayout(enterNamePanel, BoxLayout.Y_AXIS));
        enterNamePanel.add(text, BorderLayout.NORTH);
        enterNamePanel.add(field, BorderLayout.CENTER);
        enterNamePanel.add(ok, BorderLayout.SOUTH);
    }

    //code taken from alarm controller UI, C like 5
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("ok for name")) {
            doTheIntroNameStuff();
        } else if (e.getActionCommand().equals("U")) {
            itemUseChoice = 2;
            pickItemFromInventory();
        } else if (e.getActionCommand().equals("T")) {
            itemUseChoice = 3;
            pickItemFromInventory();
        } else if (e.getActionCommand().equals("1")) {
            if (itemUseChoice == 1) { //pick up
                pickFromChestAndDisplayInventory(1);
            } else if (itemUseChoice == 2) { //use
                useItemAt(1);
            } else { //trash
                chooseGetRidOfItem(1);
            }
        } else if (e.getActionCommand().equals("2")) {
            if (itemUseChoice == 1) {
                pickFromChestAndDisplayInventory(2);
            } else if (itemUseChoice == 2) {
                useItemAt(2);
            } else {
                chooseGetRidOfItem(2);
            }
        } else if (e.getActionCommand().equals("3")) {
            if (itemUseChoice == 2) {
                useItemAt(3);
            } else {
                chooseGetRidOfItem(3);
            }
        } else if (e.getActionCommand().equals("opening the chest")) {
            itemUseChoice = 1; //pick up item
            getChest();
        } else if (e.getActionCommand().equals("obs choice A")) {
            obs.setChosenOption("A");
            doObsOutro();
        } else if (e.getActionCommand().equals("obs choice B")) {
            obs.setChosenOption("B");
            doObsOutro();
        } else if (e.getActionCommand().equals("obs choice C")) {
            obs.setChosenOption("C");
            doObsOutro();
        } else if (e.getActionCommand().equals("-1")) {
            displayMainMenu();   //TODO: this  //pre req is that the text to display is already set up to what it should be
        } else if (e.getActionCommand().equals("P")) {
            saveGameApp();
        } else if (e.getActionCommand().equals("L")) {
            loadGameApp();
        } else if (e.getActionCommand().equals("N")) {
            changeName();
        } else if (e.getActionCommand().equals("change name")) {
            doTheChangeName();
        } else if (e.getActionCommand().equals("C")) {
            introObstacle();
        } else if (e.getActionCommand().equals("I")) {
            useInventory(); //TODO: and this
        } else if (e.getActionCommand().equals("S")) {
            displayCurrentStatus();
        } else if (e.getActionCommand().equals("get result of obs")) {
            resultOfObs();
        } else {
            endMessage();
        }
//        switch (e.getActionCommand()) {
//            case "ok for name":
//                doTheIntroNameStuff();
//                break;
//            case "U": //use
//                itemUseChoice = 2;
//                pickItemFromInventory();
//                break;
//            case "T": //trash item
//                itemUseChoice = 3;
//                pickItemFromInventory();
//                break;
//            case "1":
//                if (itemUseChoice == 1) { //pick up
//                    pickFromChestAndDisplayInventory(1);
//                } else if (itemUseChoice == 2) { //use
//                    useItemAt(1);
//                } else { //trash
//                    chooseGetRidOfItem(1);
//                }
//                break;
//            case "2":
//                if (itemUseChoice == 1) {
//                    pickFromChestAndDisplayInventory(2);
//                } else if (itemUseChoice == 2) {
//                    useItemAt(2);
//                } else {
//                    chooseGetRidOfItem(2);
//                }
//                break;
//            case "3":
//                if (itemUseChoice == 2) {
//                    useItemAt(3);
//                } else {
//                    chooseGetRidOfItem(3);
//                }
//                break;
//            case "opening the chest":
//                itemUseChoice = 1; //pick up item
//                getChest();
//                break;
//            case "obs choice A":
//                obs.setChosenOption("A");
//                doObsOutro();
//                break;
//            case "obs choice B":
//                obs.setChosenOption("B");
//                doObsOutro();
//                break;
//            case "obs choice C":
//                obs.setChosenOption("C");
//                doObsOutro();
//                break;
//            case "-1":
//                displayMainMenu();   //TODO: this  //pre req is that the text to display is already set up to what it should be
//                break;
//            case "P":
//                saveGameApp();
//                break;
//            case "L":
//                loadGameApp();
//                break;
//            case "N":
//                changeName();
//                break;
//            case "change name":
//                doTheChangeName();
//                break;
//            case "C":
//                introObstacle();
//                break;
//            case "I":
//                useInventory(); //TODO: and this
//                break;
//            case "S":
//                displayCurrentStatus();
//                break;
//            default: //quit case
//                endMessage();
//                break;
//        }

    }

    private void doTheIntroNameStuff() {
        user.setName(field.getText());
        welcomeCharacter();
        remove(enterNamePanel);
        add(mainMenuPanel, BorderLayout.SOUTH);
        makeChangeNamePanel();
        validate();
    }

    private void doTheChangeName() {
        user.setName(field.getText());
        remove(textToDisplay);
        remove(changeNamePanel);
        textToDisplay = new JTextArea("You've successfully changed your name to "
                + user.getName() + ".");
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);
        makeMainMenuPanel();
        add(mainMenuPanel, BorderLayout.SOUTH);
        validate();
    }
}
