package ui;

import model.*;
import model.Character;

import java.util.Scanner;

public class GameApp {
    private Character user;
    private Inventory inventory;
    private Scanner scanner;
    private String input;
    private boolean cont;
    private Obstacle obs;
    private Chest chest;

    public GameApp() {
        inventory = new Inventory();
        scanner = new Scanner(System.in);
        input = "";
        cont = true;

        runApp();
    }

    public void runApp() {
        intro();
        introCharacter();
        doStep();
        while (cont && user.stillAlive()) {
            doStep();
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

    public void doStep() {
        displayMainMenu();
        input = scanner.next().toUpperCase();
        if (!(input.equals("S") || input.equals("I") || input.equals("Q") || input.equals("C"))) {
            while (!(input.equals("S") || input.equals("I") || input.equals("Q") || input.equals("C"))) {
                System.out.println("Invalid command. Please enter q, c, s, or i:");
                input = scanner.next().toUpperCase();
            }
        }
        if (input.equals("S")) {
            System.out.println("Current Status:");
            displayStatus();
        } else if (input.equals("I")) {
            useInventory();
        } else if (input.equals("Q")) {
            cont = false;
        } else if (input.equals("C")) {
            doObstacle();
            cont = true;
        }
    }

    public void doObstacle() {
        obs = new Obstacle();
        System.out.println("You continue on your journey.");
        System.out.println(obs.lookUpObstacle());
        System.out.println("You have 3 options:\n" + obs.getOptions());
        System.out.println("What will you do? (enter a, b, or c)");
        obs.setChosenOption(scanner.next().toUpperCase());
        System.out.println(obs.getResult());
        System.out.println();
        user.healOrDamage(obs.getChangeInHealth());
        user.gainLoseProgress(obs.getChangeInProgress());
        getChest();
        System.out.println("New Status:");
        displayStatus();
    }

    public void getChest() {
        chest = new Chest();
        System.out.println("You got a chest! Items in chest:");
        System.out.println(chest.getAllItemNames());
        System.out.println("Please enter 1/2 to choose an item to pick up. Or 0 if you do not want an item.");
        int choice = scanner.nextInt();
        if (inventory.isFull()) {
            System.out.println("Your inventory is full.");
            chooseGetRidOfItem();
        } else {
            Item chosen = chest.getItemAtIndex(choice);
            System.out.println("You picked up " + chosen.getName().toLowerCase());
            inventory.addItem(chosen);
            displayInventory();
        }
    }

    public void chooseGetRidOfItem() {
        System.out.println("Would you like to get rid of an item? (y/n)");
        String choice = scanner.next().toLowerCase();
        if (choice.equals("y")) {
            System.out.println("What would you like to get rid of? (1/2/3)");
            int x = scanner.nextInt();
            Item getRidOf = inventory.getItemAtIndex(x);
            System.out.println("Getting rid of " + getRidOf.getName().toLowerCase());
            displayInventory();
        } else {
            System.out.println("You have chosen not to get rid of anything.");
        }
    }

    public void useInventory() {
        if (!inventory.isEmpty()) {
            System.out.println("Inventory: " + inventory.getAllItemNames());
            System.out.println("Please enter 1, 2, or 3 to use item.");
            int choice = scanner.nextInt();
            Item chosenItem = inventory.getItemAtIndex(choice);
            System.out.println("You use " + chosenItem.getName().toLowerCase() + ".");
            user.gainLoseProgress(chosenItem.getChangeInProgress());
            user.healOrDamage(chosenItem.getChangeInHealth());
            System.out.println("New Status:");
            displayStatus();
        } else {
            System.out.println("Inventory is currently empty.");
        }
    }

    public void displayInventory() {
        System.out.println("Current Inventory: ");
        System.out.println(inventory.getAllItemNames());
        System.out.println();
    }

    public void introCharacter() {
        System.out.println("Please enter a name for your character:");
        user = new Character(scanner.next());

        System.out.println("Hi " + user.getName() + "! What a great name.");
        System.out.println("With your new name in mind, you start on your journey.");
        System.out.println();
    }

    public void displayStatus() {
        System.out.println("Name: " + user.getName());
        System.out.println("Health: " + user.getHealth() + "HP");
        System.out.println("Progress: " + user.getProgress() + "%");
        System.out.println();
    }

    public void displayMainMenu() {
        System.out.println("Main Menu:");
        System.out.println("- Status(s)\n- Inventory(i)\n- Continue on journey(c)\n- Quit(q)");
    }

    public void intro() {
        System.out.println("Welcome to The Forest of Lore!");
        System.out.println("You are surrounded by trees, and don't know the way out.");
        System.out.println("Your status is currently at 100% health, and 0% progress.");
        System.out.println("Try to get progress to 100% by choosing the correct option in each scenario.");
        System.out.println("If health ever hits 0, you die and the game is over.");
        System.out.println();
    }

    public void winMessage() {
        System.out.println("Your progress has reached 100%.\nYou've made it out!");
        System.out.println("Sadly, there is absolutely nothing interesting outside of the forest:(");
        System.out.println();
    }

    public void loseMessage() {
        System.out.println("Oh no! Your health has reached 0HP.");
        System.out.println("You succumb to the dangers of the forest and die.");
        System.out.println();
    }

    public void endMessage() {
        System.out.println("Thanks for playing!\nEnding stats: ");
        displayStatus();
    }
}
