package ui;

import exceptions.NoSuchItemExistsException;
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

//represents the game being played as a JFrame with various JComponents and uses the actionListener interface
public class GameAppGraphical extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/gameApp.json";

    private Character user;      //the user, has a name, health and progress bar
    private Inventory inventory; //the current inventory
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

    //EFFECTS: instantiates various fields and starts the game off
    public GameAppGraphical() throws FileNotFoundException {
        super("Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        inventory = new Inventory();
        jsonReader = new JsonReader(JSON_STORE);
        jsonWriter = new JsonWriter(JSON_STORE);

        field = new JTextField(10);
        field.setMaximumSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));

        makeMainMenuPanel();
        makeEnterNamePanel();

        pictureDisplayPanel = new PictureDisplayPanel();

        add(pictureDisplayPanel, BorderLayout.NORTH);
        pack();

        setSize(BOX_WIDTH, BOX_HEIGHT);
        centreOnScreen();
        setVisible(true);
        user = new Character("");

        startGame();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Character getCharacter() {
        return user;
    }

    //MODIFIES: this
    //EFFECTS: starts the game by adding the start image to the display, a string of information for the user,
    //         and makes an enter name panel that takes user input and makes a new character with that name
    public void startGame() {
        String introText = "Welcome to The Forest of NAME TO BE DETERMINED!\nYou are surrounded by trees,"
                + " and don't know the way out.\nYour status is currently at 100HP, and 0% progress.\nInventory is"
                + " currently at 0/3 items.\nItems will be able to raise or lower status bars. However, specific "
                + "effects of each "
                + "item are unknown to you.\nTo make it out of the forest, try to get progress to 100% by choosing "
                + "the correct option in each scenario.\nIf health ever hits 0, you die and the game is over.\n";
        textToDisplay = new JTextArea(introText, 5, 100);
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);
        add(enterNamePanel, BorderLayout.SOUTH);
        validate();
    }

    //MODIFIES: this
    //EFFECTS: a new obstacle is instantiated for user to do, and gets displayed.
    //         the options for the obstacle are displayed as a JTextArea, and
    //         a panel with 3 buttons is shown, and takes the users choice regarding the obstacle
    public void introObstacle() {
        obs = new Obstacle();
        getContentPane().removeAll();
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


    //MODIFIES: this
    //EFFECTS: displays an outro specifying changes in stats
    private void doObsOutro() {
        getContentPane().removeAll();
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
    }

    //MODIFIES: this
    //EFFECTS: if user has no health, the game is over (bad ending)
    //         else if user has reached 100 progress, the user wins (good end)
    //         else, at a random chance, the user can either
    //         get a chest or have to accept the changes to their stats.
    //         one of the 4 scenarios are diplayed on screen with their respective jbuttons needed
    private void obsResult() {
        getContentPane().removeAll();

        Random r = new Random();
        int ran = r.nextInt(2);
        if (user.getHealth() == 0) {
            addBadEndComponentsToScreen();
        } else if (user.getProgress() == 100) {
            addGoodEndComponentsToScreen();
        } else if (ran == 1) { //no chest here, so go back to main menu after
            addGetChestComponentsToScreen();
        } else {
            addAcceptChangesComponentsToScreen();
        }
        validate();
    }

    //REQUIRES: content pane is empty
    //MODIFIES: this
    //EFFECTS: adds jComponents relating to a get chest scenario
    private void addGetChestComponentsToScreen() {
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

    //REQUIRES: content pane is empty
    //MODIFIES: this
    //EFFECTS: adds jComponents relating to an accept changes scenario
    private void addAcceptChangesComponentsToScreen() {
        JPanel temp = new JPanel();
        JButton ok = new JButton("Accept changes");
        ok.setActionCommand("-1"); //command for displayMain menu, basically go back
        ok.addActionListener(this);
        temp.add(ok);
        textToDisplay = new JTextArea(obs.getResult() + "\nFortunately these changes will not cause you to die.");
        textToDisplay.setEditable(false);
        add(pictureDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
        add(temp, BorderLayout.SOUTH);
        textToDisplay = new JTextArea("Main menu");
    }

    //REQUIRES: content pane is empty
    //MODIFIES: this
    //EFFECTS: adds jComponents relating to a good ending scenario
    private void addGoodEndComponentsToScreen() {
        textToDisplay = new JTextArea("Your progress has reached 100%.\nYou've made it out!\nUnfortunately, "
                + "all you can see are endless hills of sand.\n"
                + "There is nothing outside the forest.\n\n" + getEndMessage());
        pictureDisplayPanel.setImage("good ending");
        add(pictureDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
    }

    //REQUIRES: content pane is empty
    //MODIFIES: this
    //EFFECTS: adds jComponents relating to a bad ending scenario
    private void addBadEndComponentsToScreen() {
        textToDisplay = new JTextArea("Oh no! Your health has reached 0.\n"
                + "You succumb to the dangers of the forest and die :(\n\n" + getEndMessage());
        pictureDisplayPanel.setImage("bad ending");
        add(pictureDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
    }

    //MODIFIES: this
    //EFFECTS: a new chest is instantiated for the user. there are a total of 2 items in the chest, displayed
    //         in the inventoryAndChestDisplayPanel,
    //         and user can pick up to 1 to add to inventory -> by using jButtons
    public void getChest() {
        chest = new Chest();
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
        textToDisplay = new JTextArea("Main menu");
    }

    //MODIFIES: this
    //EFFECTS: picks up an item from chest and saves it to inventory
    //         if the inventory is full, then can not pick up the item and goes back to main menu
    //         else, goes back to main menu, and displays current inventory
    public void pickFromChestAndDisplayInventory(int spot) { //TODO: if time, add in if inventory is full option
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

    //MODIFIES: this
    //EFFECTS: if the item chosen does not exist (choice > inventory.length()),
    //         then display a new JTextArea that tells user of that issue
    //         else if item exists, will remove chosen item from inventory, and go back to
    //         main menu while displaying the new inventory
    public void chooseGetRidOfItem(int choice) {
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

            String str = "You threw away " + getRidOf.getName().toLowerCase()
                    + ".\n\nCurrent inventory:\n" + inventory.getAllItemNames();
            textToDisplay = new JTextArea(str);
            add(pictureDisplayPanel, BorderLayout.NORTH);
            add(textToDisplay, BorderLayout.CENTER);
            add(mainMenuPanel, BorderLayout.SOUTH);
        }
        validate();
        textToDisplay = new JTextArea("You have chosen not to get rid of anything.");
    }

    //MODIFIES: this
    //EFFECTS: lets the user use the inventory. however, if inventory is empty, user can't do anything to it yet.
    //          else, user can choose to either get rid of an item, use an item, or exit back to main menu
    public void useInventory() {
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

    //MODIFIES: this
    //EFFECTS: displays the current inventory and a row of buttons to choose
    //         between 3 (possible) currently in the inventory, string displayed
    //         will vary depending on if user wants to get rid of or use item
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
        textToDisplay = new JTextArea("Main menu");
    }

    //MODIFIES: this
    //EFFECTS: if there is no item at the chosen index, then will only change the
    //         JTextArea displayed to ask user to choose a different item.
    //         else, user uses item and has its stats changed.
    //          goes back to main menu will diplaying changes to status
    public void useItemAt(int choice) {
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
        textToDisplay = new JTextArea("You did not use an item.");
    }

    //MODIFIES: this
    //EFFECTS: displays panels asking what the user wants to change their name to,
    //         and adds the changeNamePanel for user unput
    public void changeName() {
        getContentPane().removeAll();
        textToDisplay = new JTextArea("You've chosen to change your current name.\n"
                + " What would you like to change it to?");
        textToDisplay.setEditable(false);
        makeChangeNamePanel();
        add(pictureDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
        add(changeNamePanel, BorderLayout.SOUTH);
        validate();
    }

    //EFFECTS: returns the current status of the user
    public String getStatusMessage() {
        return "Name: " + user.getName() + "\nHealth: " + user.getHealth()
                + "HP\nProgress: " + user.getProgress() + "%";
    }

    //MODIFIES: this
    //EFFECTS: displays current status as a JTextArea
    public void displayCurrentStatus() {
        remove(textToDisplay);
        textToDisplay = new JTextArea("Current Status:\n" + getStatusMessage());
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);
        validate();
    }

    //REQUIRES: textToDisplay is what is needed to be displayed //TODO: go through later and make sure
    //EFFECTS: displays the main menu -> static image in the pictureDisplayPanel,
    //         a JTextArea with the textToDisplay, and the mainMenuPanel
    public void displayMainMenu() {
        getContentPane().removeAll();

        pictureDisplayPanel.setImage("static img");
        add(pictureDisplayPanel, BorderLayout.NORTH);
        add(textToDisplay, BorderLayout.CENTER);
        makeMainMenuPanel();
        add(mainMenuPanel, BorderLayout.SOUTH);
        validate();
    }

    //MODIFIES: this
    //EFFECTS: changes the JTextArea to welcome the user, is called after user enters name for the first time
    public void welcomeCharacter() {
        remove(textToDisplay);
        textToDisplay = new JTextArea("Hi " + user.getName() + "! What a great name.\nAfter making sure everything is"
                + " in order, you start on your journey.", 5, 100);
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);
        validate();
    }

    //EFFECTS: removes JComponents, and thanks the user for playing.
    //         the user is not able to move from this state (except to exit the application entirely)
    public void endMessage() {
        getContentPane().removeAll();
        pictureDisplayPanel.setImage("static img"); //TODO: if time, make a thanks for playing image
        add(pictureDisplayPanel, BorderLayout.NORTH);
        textToDisplay = new JTextArea(getEndMessage());
        textToDisplay.setEditable(false);
        add(textToDisplay, BorderLayout.CENTER);

        validate();
    }

    //EFFECTS: returns the end message, and the ending stats
    public String getEndMessage() {
        return "Thanks for playing!\n\nEnding stats:\n" + getStatusMessage();
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
        } catch (FileNotFoundException e) {
            textToDisplay = new JTextArea("Unable to write to file: " + JSON_STORE);
        }
        textToDisplay.setEditable(false);
        add(textToDisplay);
        validate();
    }

    //MODIFIES: this
    //EFFECTS: loads GameAppData from file, modifies user and inventory to match saved stats.
    //         displays the new status loaded from the saved file
    //         if file with JSON_STORE name is not found, IOException is thrown
    private void loadGameApp() {
        remove(textToDisplay);
        try {
            GameAppData data = jsonReader.read();
            user = data.getUser();
            inventory = data.getInventory();
            textToDisplay = new JTextArea("Loaded saved progress from" + JSON_STORE
                    + "\n\nNew status:\n" + getStatusMessage());
        } catch (IOException e) {
            textToDisplay = new JTextArea("Unable to read from file: " + JSON_STORE);
        } catch (NoSuchItemExistsException e) {
            textToDisplay = new JTextArea("Tried to add an item with no associated number: " + e.getMessage());
        }
        textToDisplay.setEditable(false);
        add(textToDisplay);
        validate();
    }

    //MODIFIES: this
    //EFFECTS: instantiates the ObstacleChoicePanel, and adds 3 buttons (A, B, C) that
    //         refer to the 3 options user can choose regarding an obstacle
    //         sets the action command for the buttons, and adds this as the action listener
    private void makeObstacleChoicePanel() {
        obstacleChoicePanel = new JPanel();
        JButton choiceA = new JButton("A");
        JButton choiceB = new JButton("B");
        JButton choiceC = new JButton("C");

        choiceA.setActionCommand("obs choice A");
        choiceB.setActionCommand("obs choice B");
        choiceC.setActionCommand("obs choice C");

        choiceA.addActionListener(this);
        choiceB.addActionListener(this);
        choiceC.addActionListener(this);

        obstacleChoicePanel.add(choiceA);
        obstacleChoicePanel.add(choiceB);
        obstacleChoicePanel.add(choiceC);
    }

    //MODIFIES: this
    //EFFECTS: instantiates a new mainMenuPanel, adds the corresponding buttons,
    //         their action commands, and this as the action listener
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

    //MODIFIES: this
    //EFFECTS: adds this as the action listener to buttons passed in, and
    //         adds the buttons to the mainMenuPanel
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

    //MODIFIES: this
    //EFFECTS: instantiates a new inventoryChoice Panel,
    //         adds corresponding buttons, sets their action commands, and
    //         adds this as the action listener to all of them
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

    //MODIFIES: this
    //EFFECTS: instantiates a new makeInventoryMenuPanel, creates the correspinding buttons,
    //         sets their actionCommand, adds this as its action listener, and adds
    //         the buttons to the inventoryMenuPanel
    private void makeInventoryMenuPanel() {
        inventoryMenuPanel = new JPanel();
        inventoryMenuPanel.setSize(BOX_WIDTH, LABEL_HEIGHT);

        JButton useItem = new JButton("Use Item");
        JButton getRidOfItem = new JButton("Get rid of item");
        JButton back = new JButton("Back");

        useItem.setActionCommand("U");
        getRidOfItem.setActionCommand("T");
        back.setActionCommand("-1");

        useItem.addActionListener(this);
        getRidOfItem.addActionListener(this);
        back.addActionListener(this);

        inventoryMenuPanel.add(useItem);
        inventoryMenuPanel.add(getRidOfItem);
        inventoryMenuPanel.add(back);
    }

    //MODIFIES: this
    //EFFECTS: instantiates a new chestChoicePanel, creates the corresponding buttons,
    //         sets their actionCommand, adds this as its action listener, and adds
    //         the buttons to the chestChoicePanel
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

    //MODIFIES: this
    //EFFECTS: instantiates a new changeNamePanel, creates the corresponding buttons and fields,
    //         sets their action command, adds this as their action listener,
    //         and adds them to the changeNamePanel
    private void makeChangeNamePanel() {
        changeNamePanel = new JPanel();
        JButton enter = new JButton("Continue");
        enter.setActionCommand("change name");
        enter.addActionListener(this);

        field = new JTextField(10);
        changeNamePanel.add(field);
        changeNamePanel.add(enter);

    }

    //MODIFIES: this
    //EFFECTS: instantiates a new enternamePanel, creates the corresponding buttons and fields,
    //         sets their action command, adds this as their action listener,
    //         and adds them to the changeNamePanel
    private void makeEnterNamePanel() {
        enterNamePanel = new JPanel();

        JLabel text = new JLabel("Please enter a name for your character:");
        JButton ok = new JButton("Continue");

        ok.setActionCommand("ok for name");
        ok.addActionListener(this);

        enterNamePanel.add(text, BorderLayout.NORTH);
        enterNamePanel.add(field, BorderLayout.CENTER);
        enterNamePanel.add(ok, BorderLayout.SOUTH);
    }

    //code taken from alarm controller UI

    /**
     * citation: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
     */
    //MODIFIES: this
    //EFFECTS: centers the application on the screen
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    //MODIFIES: this
    //EFFECTS: does a step -> called every time a button is pressed
    //         - ok for name -> after the first step of the game, intro character after
    //         - U -> sets itemUseChoice to 2 (use item), next step is to pick an item from the inventory
    //         - T -> sets itemUseChoice to 3 (trash item), next step is to pick an item from the inventory
    //         - "1" -> user picks item 1, next is doActionChosenItem
    //         - "2" -> user picks item 1, next is doActionChosenItem
    //         - "3" -> user picks item 1, next is doActionChosenItem
    //         - opening the chest -> user opens the chest
    //         - obs choice A, B , and C -> user chooses obstacle option, sets obs to the chosen option and does outro
    //         - "-1" -> displays main menu
    //         - P -> saves users status and inventory to file
    //         - L -> loads data from saved file
    //         - N -> user chooses to change name
    //         - change name -> user has entered a name, will now set characters name to users input
    //         - C -> user chooses to continue on journey -> will lead to obstacle
    //         - I -> user uses inventory
    //         - S -> will display current status
    //         - get result of obs -> does the obs result
    //         - else, user quits and will display end msg
    /**
     * Invoked when an action occurs.
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
            doActionChosenItem(1);
        } else if (e.getActionCommand().equals("2")) {
            doActionChosenItem(2);
        } else if (e.getActionCommand().equals("3")) {
            doActionChosenItem(3);
        } else if (e.getActionCommand().equals("opening the chest")) {
            itemUseChoice = 1; //pick up item
            getChest();
        } else {
            actionPerformedPartTwo(e); //continued below cause too long
        }
    }

    //MODIFIES: this
    //EFFECTS: does an action based on users input (specified more clearly in actionPerformed)
    private void actionPerformedPartTwo(ActionEvent e) {
        if (e.getActionCommand().equals("obs choice A")) {
            obs.setChosenOption("A");
            doObsOutro();
        } else if (e.getActionCommand().equals("obs choice B")) {
            obs.setChosenOption("B");
            doObsOutro();
        } else if (e.getActionCommand().equals("obs choice C")) {
            obs.setChosenOption("C");
            doObsOutro();
        } else if (e.getActionCommand().equals("-1")) {
            displayMainMenu();
        } else if (e.getActionCommand().equals("P")) {
            saveGameApp();
        } else if (e.getActionCommand().equals("L")) {
            loadGameApp();
        } else if (e.getActionCommand().equals("N")) {
            changeName();
        } else {
            actionPerformedPartThree(e); //continued below cause too long
        }
    }

    //MODIFIES: this
    //EFFECTS: does an action based on users input (specified more clearly in actionPerformed)
    private void actionPerformedPartThree(ActionEvent e) {
        if (e.getActionCommand().equals("change name")) {
            doTheChangeName();
        } else if (e.getActionCommand().equals("C")) {
            introObstacle();
        } else if (e.getActionCommand().equals("I")) {
            useInventory();
        } else if (e.getActionCommand().equals("S")) {
            displayCurrentStatus();
        } else if (e.getActionCommand().equals("get result of obs")) {
            obsResult();
        } else {
            endMessage();
        }
    }

    //MODIFIES: this
    //EFFECTS: based off of itemUseChoice, will call next method for what to do with item at i
    private void doActionChosenItem(int i) {
        if (itemUseChoice == 1) { //pick up
            pickFromChestAndDisplayInventory(i);
        } else if (itemUseChoice == 2) { //use
            useItemAt(i);
        } else { //trash
            chooseGetRidOfItem(i);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the users name to the users input and welcomes the character
    private void doTheIntroNameStuff() {
        user.setName(field.getText());
        welcomeCharacter();
        remove(enterNamePanel);
        add(mainMenuPanel, BorderLayout.SOUTH);
        validate();
    }

    //MODIFIES: this
    //EFFECTS: changes the characters name to the users input, and displays main menu
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

//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("ok for name")) {
//            doTheIntroNameStuff();
//        } else if (e.getActionCommand().equals("U")) {
//            itemUseChoice = 2;
//            pickItemFromInventory();
//        } else if (e.getActionCommand().equals("T")) {
//            itemUseChoice = 3;
//            pickItemFromInventory();
//        } else if (e.getActionCommand().equals("1")) {
//            doActionChosenItem(1);
//        } else if (e.getActionCommand().equals("2")) {
//            doActionChosenItem(2);
//        } else if (e.getActionCommand().equals("3")) {
//            doActionChosenItem(3);
//        } else if (e.getActionCommand().equals("opening the chest")) {
//            itemUseChoice = 1; //pick up item
//            getChest();
//        } else if (e.getActionCommand().equals("obs choice A")) {
//            obs.setChosenOption("A");
//            doObsOutro();
//        } else if (e.getActionCommand().equals("obs choice B")) {
//            obs.setChosenOption("B");
//            doObsOutro();
//        } else if (e.getActionCommand().equals("obs choice C")) {
//            obs.setChosenOption("C");
//            doObsOutro();
//        } else if (e.getActionCommand().equals("-1")) {
//            displayMainMenu();
//        } else if (e.getActionCommand().equals("P")) {
//            saveGameApp();
//        } else if (e.getActionCommand().equals("L")) {
//            loadGameApp();
//        } else if (e.getActionCommand().equals("N")) {
//            changeName();
//        } else if (e.getActionCommand().equals("change name")) {
//            doTheChangeName();
//        } else if (e.getActionCommand().equals("C")) {
//            introObstacle();
//        } else if (e.getActionCommand().equals("I")) {
//            useInventory();
//        } else if (e.getActionCommand().equals("S")) {
//            displayCurrentStatus();
//        } else if (e.getActionCommand().equals("get result of obs")) {
//            obsResult();
//        } else {
//            endMessage();
//        }
//    }
