package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
//        try {
//            new GameAppConsole();
//        } catch (FileNotFoundException e) {
//            System.out.println("Can't run application - file not found");
//        }

        try {
            new GameAppGraphical();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        new InputAndMessagesConsole("HELLO");
    }
}
