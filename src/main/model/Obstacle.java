package model;

import java.util.Random;

public class Obstacle {

    private int obs; // 1-5
    private char chosenOption; //given options A-C, this is users chosen option
    private int changeInHealth; //change in health based on scenario
    private int changeInProgress; // change in progress based on scenario
    private String result;

    public Obstacle() {
        Random r = new Random();
        obs = r.nextInt(5) + 1; //returns int btwn [1, 5]
        chosenOption = 'a'; //default option set to d, which is not a-c
        changeInHealth = 0;
        changeInProgress = 0;
        result = "";
    }

    //constructor for testing purposes, 1 <= n <= 5
    public Obstacle(int n) {
        obs = n;
        chosenOption = 'a';
        changeInHealth = 0;
        changeInProgress = 0;
        result = "";
    }

    public String getResult() {
        return result;
    }

    public int getNum() {
        return obs;
    }

    public char getChosenOption() {
        return chosenOption;
    }

    public void setChosenOption(char x) {
        chosenOption = x;
        result = setUpResult();
    }

    public int getChangeInHealth() {
        return changeInHealth;
    }

    public int getChangeInProgress() {
        return changeInProgress;
    }
    // NOTE: no set change in progress/health -> should not be able to set fields in places other
    //       than here, as scenario & effects are all set up below

    //EFFECTS: returns the scenario based on int randomly assigned in constructor
    public String lookUpObstacle() {
        if (obs == 1) {
            return "You encountered a living tree. It looks happy to see you.";
        } else if (obs == 2) {
            return "You encountered a blue and white panda. He seems angry...";
        } else if (obs == 3) {
            return "You encountered a paper plane. It's lying on the floor menacingly";
        } else if (obs == 4) {
            return "You encountered a lemon. It is a very bright yellow.";
        } else {
            return "You encountered a huge boulder. On top is a goat.";
        }

//        switch (obs) {
//            case 1:
//                return "You encountered a living tree. It looks happy to see you.";
//            case 2:
//                return "You encountered a blue and white panda. He seems angry...";
//            case 3:
//                return "You encountered a paper plane. It's lying on the floor menacingly";
//            case 4:
//                return "You encountered a lemon. It is a very bright yellow.";
//            default:
//                return "You encountered a huge boulder. On top is a goat.";
//        }
    }

    // EFFECTS: returns 3 options for each scenario
    public String getOptions() {
        if (obs == 1) {
            return "A: Try to walk around it. \nB: Set it on fire. \nC: Wave.";
        } else if (obs == 2) {
            return "A: Give it some water. \nB: Poke it. \nC: Engage in a stare off.";
        } else if (obs == 3) {
            return "A: Pick it up. \nB: Put a rock on it. \nC: Make it a friend.";
        } else if (obs == 4) {
            return "A: Pick it up and eat it. \nB: Step over it. \nC: Give it googly eyes.";
        } else {
            return "A: Try to climb the boulder. \nB: Throw a leaf at the goat. \nC: Sit and wait.";
        }

//        switch (obs) {
//            case 1:
//                return "A: Try to walk around it. \nB: Set it on fire. \nC: Wave.";
//            case 2:
//                return "A: Give it some water. \nB: Poke it. \nC: Engage in a stare off.";
//            case 3:
//                return "A: Pick it up. \nB: Put a rock on it. \nC: Make it a friend.";
//            case 4:
//                return "A: Pick it up and eat it. \nB: Step over it. \nC: Give it googly eyes.";
//            default:
//                return "A: Try to climb the boulder. \nB: Throw a leaf at the goat. \nC: Sit and wait.";
//        }
    }

    //REQUIRES: chosenOption to be A-C,
    //EFFECTS: return a result string based on obs and on chosenOption
    public String setUpResult() {
        if (chosenOption == 'a') {
            return "User has not selected option yet.";
        } else if (obs == 1) {
            return getAresult();
        } else if (obs == 2) {
            return getBresult();
        } else if (obs == 3) {
            return getCresult();
        } else if (obs == 4) {
            return getDresult();
        } else {
            return getEresult();
        }
//        switch (obs) {
//            case 1:
//                return getAresult();
//            case 2:
//                return getBresult();
//            case 3:
//                return getCresult();
//            case 4:
//                return getDresult();
//            default:
//                return getEresult();
//        }
    }

    //REQUIRES: obs == 'A';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getAresult() {
        if (chosenOption == 'A') {
            changeInProgress = 20;
            changeInHealth = -30;
            return "You get smacked by a branch. Make 20 progress, but lose 30HP.";
        } else if (chosenOption == 'B') {
            changeInProgress = 10;
            changeInHealth = -25;
            return "The tree burns to ashes, but you feel tremendous guilt. Make 10 progress, but lose 25HP.";
        } else {
            changeInProgress = 30;
            return "The tree waves back. Make 30 progress.";
        }
//        switch (chosenOption) {
//            case 'A':
//                changeInProgress = 20;
//                changeInHealth = -30;
//                return "You get smacked by a branch. Make 20 progress, but lose 30HP.";
//            case 'B':
//                changeInProgress = 10;
//                changeInHealth = -25;
//                return
//                        "The tree burns to ashes, but you feel tremendous guilt. Make 10 progress, but lose 25HP.";
//            default:
//                changeInProgress = 30;
//                return "The tree waves back. Make 30 progress.";
//        }
    }

    //REQUIRES: obs == 'B';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getBresult() {
        if (chosenOption == 'A') {
            changeInHealth = -30;
            changeInProgress = -10;
            return "He spills the water. Now there's an angry wet panda after you. Lose 10 progress and 20HP";
        } else if (chosenOption == 'B') {
            changeInProgress = 20;
            changeInHealth = 30;
            return "The panda turns out to be ticklish. Make 20 progress, gain 30 HP.";
        } else {
            changeInProgress = 10;
            return "You stare into it's eyes. The panda wins and seems happy about it. Make 10 progress.";
        }
//        switch (chosenOption) {
//            case 'A':
//                changeInHealth = -30;
//                changeInProgress = -10;
//                return "He spills the water. Now there's an angry wet panda after you. Lose 10 progress and 20HP";
//            case 'B':
//                changeInProgress = 20;
//                changeInHealth = 30;
//                return "The panda turns out to be ticklish. Make 20 progress, gain 30 HP.";
//            default:
//                changeInProgress = 10;
//                return "You stare into it's eyes. The panda wins and seems happy about it. Make 10 progress.";
//        }
    }

    //REQUIRES: obs == 'C';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getCresult() {
        if (chosenOption == 'A') {
            changeInProgress = -10;
            changeInHealth = -30;
            return "Turns out it's actually a really good camouflaging chameleon."
                    + " And he is angry you disturbed him. Lose 10 progress and 30 HP.";
        } else if (chosenOption == 'B') {
            changeInProgress = 10;
            return "Nothing happens. Make 20 progress.";
        } else {
            changeInProgress = 20;
            changeInHealth = -10;
            return "The airplane starts bouncing in joy."
                    + " It accidentally hits your leg. Make 20 progress, lose 10HP.";
        }
//        switch (chosenOption) {
//            case 'A':
//                changeInProgress = -10;
//                changeInHealth = -30;
//                return "Turns out it's actually a really good camouflaging chameleon."
//                        + " And he is angry you disturbed him. Lose 10 progress and 30 HP.";
//            case 'B':
//                changeInProgress = 10;
//                return "Nothing happens. Make 20 progress.";
//            default:
//                changeInProgress = 20;
//                changeInHealth = -10;
//                return "The airplane starts bouncing in joy."
//                        + " It accidentally hits your leg. Make 20 progress, lose 10HP.";
//        }
    }

    //REQUIRES: obs == 'D';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getDresult() {
        if (chosenOption == 'A') {
            changeInProgress = 20;
            changeInHealth = -40;
            return "It is extremely sour. Make 20 progress, but lose 40HP.";
        } else if (chosenOption == 'B') {
            changeInProgress = -20;
            changeInHealth = -30;
            return "You step over it and fall right into a trap. Lose 20 progress, and lose 30HP.";
        } else {
            changeInProgress = 40;
            changeInHealth = 10;
            return "A toothy smile appears below the eyes. Make 40 progress, gain 10 HP..";
        }


//        switch (chosenOption) {
//            case 'A':
//                changeInProgress = 20;
//                changeInHealth = -40;
//                return "It is extremely sour. Make 20 progress, but lose 40HP.";
//            case 'B':
//                changeInProgress = -20;
//                changeInHealth = -30;
//                return "You step over it and fall right into a trap. Lose 20 progress, and lose 30HP.";
//            default:
//                changeInProgress = 40;
//                changeInHealth = 10;
//                return "A toothy smile appears below the eyes. Make 40 progress, gain 10 HP..";
//        }
    }

    //REQUIRES: obs == 'E';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getEresult() {
        if (chosenOption == 'A') {
            changeInHealth = -40;
            changeInProgress = -10;
            return "You fall and break your little toe. Lose 10 progress, and lose 40 HP.";
        } else if (chosenOption == 'B') {
            changeInProgress = 30;
            changeInHealth = 20;
            return "The goat eats the leaf. In thanks, it opens a tunnel for you to pass"
                    + " through and gives you a potion. Make 20 progress, and gain 20HP.";
        } else {
            changeInProgress = -20;
            changeInHealth = -40;
            return "After 10 days, it starts to snow and you get a cold. The goat laughs at you. "
                    + "Lose 20 progress, and lose 40HP.";
        }


//        switch (chosenOption) {
//            case 'A':
//                changeInHealth = -40;
//                changeInProgress = -10;
//                return "You fall and break your little toe. Lose 10 progress, and lose 40 HP.";
//            case 'B':
//                changeInProgress = 30;
//                changeInHealth = 20;
//                return "The goat eats the leaf. In thanks, it opens a tunnel for you to pass"
//                        + " through and gives you a potion. Make 20 progress, and gain 20HP.";
//            default:
//                changeInProgress = -20;
//                changeInHealth = -40;
//                return "After 10 days, it starts to snow and you get a cold. The goat laughs at you. "
//                        + "Lose 20 progress, and lose 40HP.";
//        }
    }
}
