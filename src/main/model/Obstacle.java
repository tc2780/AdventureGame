package model;

import java.util.Random;

//represents an obstacle that has an int corresponding to its specific scenario, a string for the chosen option
//     in face of the obstacle, a string result resulting from specific scenario and chosen option,
//     and change in health and progress that correlate to effects given from result
public class Obstacle {

    private int obs;                //an int representing the specific scenario (1 <= obs <= 5)
    private String chosenOption;    //given options A-C, this is users chosen option
    private int changeInHealth;     //change in health based on scenario
    private int changeInProgress;   //change in progress based on scenario
    private String result;          //result based on chosen option and obs

    //EFFECTS: obs is given a random scenario number btwn 1-5;
    //         chosenOption is set to Z, representing the fact that the user has not selected an option,
    //         init change in health and progress to be 0 (some obstacles don't change health and/or progress)
    //         and init result to be ""
    public Obstacle() {
        Random r = new Random();
        obs = r.nextInt(5) + 1; //returns int btwn [1, 5]
        chosenOption = "Z";            //default option set to z, which is not a-c
        changeInHealth = 0;            //init to 0 because some obstacles don't change health
        changeInProgress = 0;
        result = "";
    }

    //REQUIRES: 1<= n<= 5
    //EFFECTS: constructor mostly for testing purposes, 1 <= n <= 5
    public Obstacle(int n) {
        obs = n;
        chosenOption = "Z";
        changeInHealth = 0;
        changeInProgress = 0;
        result = "";
    }

    //EFFECTS: returns the result; default is ""
    public String getResult() {
        return result;
    }

    //EFFECTS: returns scenario number of obstacle
    public int getNum() {
        return obs;
    }

    //EFFECTS: returns chosen option
    public String getChosenOption() {
        return chosenOption;
    }

    //REQUIRES: x to be "A", "B", or "C"
    //MODIFIES: this
    //EFFECTS: sets chosenOption to given string x, and modifies result to match the expected result
    //         from the scenario and the given chosen option
    public void setChosenOption(String x) {
        chosenOption = x;
        result = setUpResult();
    }

    //EFFECTS: returns change in health of obstacle
    public int getChangeInHealth() {
        return changeInHealth;
    }

    //EFFECTS: returns change in progress of obstacle
    public int getChangeInProgress() {
        return changeInProgress;
    }
    // NOTE: no set change in progress/health -> should not be able to set fields in places other
    //       than here, as scenario & effects are all set up below

    //EFFECTS: returns the scenario based on int randomly assigned in constructor
    public String lookUpObstacle() {
        if (obs == 1) {
            return "You encounter a living tree. It looks happy to see you.";
        } else if (obs == 2) {
            return "You encounter a blue and white panda. He seems angry...";
        } else if (obs == 3) {
            return "You encounter a paper plane. It's lying on the floor menacingly";
        } else if (obs == 4) {
            return "You encounter a lemon. It is a very bright yellow.";
        } else {
            return "You encounter a huge boulder. On top is a goat.";
        }
    }

    // EFFECTS: returns a string representing the 3 options for each specific scenario
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
    }

    //REQUIRES: chosenOption to be A-C,
    //MODIFIES: this
    //EFFECTS: return a result string based on obs and chosenOption
    public String setUpResult() {
        if (chosenOption.equals("Z")) {
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
    }

    //REQUIRES: obs == 'A';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getAresult() {
        if (chosenOption.equals("A")) {
            changeInProgress = 20;
            changeInHealth = -30;
            return "You get smacked by a branch. Make 20 progress, but lose 30HP.";
        } else if (chosenOption.equals("B")) {
            changeInProgress = 10;
            changeInHealth = -25;
            return "The tree burns to ashes, but you feel tremendous guilt. Make 10 progress, but lose 25HP.";
        } else {
            changeInProgress = 30;
            return "The tree waves back. Make 30 progress.";
        }
    }

    //REQUIRES: obs == 'B';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getBresult() {
        if (chosenOption.equals("A")) {
            changeInHealth = -30;
            changeInProgress = -10;
            return "He spills the water. Now there's an angry wet panda after you. Lose 10 progress and 20HP";
        } else if (chosenOption.equals("B")) {
            changeInProgress = 20;
            changeInHealth = 30;
            return "The panda turns out to be ticklish. Make 20 progress, gain 30 HP.";
        } else {
            changeInProgress = 10;
            return "You stare into its eyes. The panda wins and seems happy about it. Make 10 progress.";
        }
    }

    //REQUIRES: obs == 'C';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getCresult() {
        if (chosenOption.equals("A")) {
            changeInProgress = -10;
            changeInHealth = -30;
            return "Turns out it's actually a really good camouflaging chameleon."
                    + " And he is angry you disturbed him. Lose 10 progress and 30 HP.";
        } else if (chosenOption.equals("B")) {
            changeInProgress = 10;
            return "Nothing happens. Make 20 progress.";
        } else {
            changeInProgress = 20;
            changeInHealth = -10;
            return "The airplane starts bouncing in joy."
                    + " It accidentally hits your leg. Make 20 progress, lose 10HP.";
        }
    }

    //REQUIRES: obs == 'D';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getDresult() {
        if (chosenOption.equals("A")) {
            changeInProgress = 20;
            changeInHealth = -40;
            return "It is extremely sour. Make 20 progress, but lose 40HP.";
        } else if (chosenOption.equals("B")) {
            changeInProgress = -20;
            changeInHealth = -30;
            return "You step over it and fall right into a trap. Lose 20 progress, and lose 30HP.";
        } else {
            changeInProgress = 40;
            changeInHealth = 10;
            return "A toothy smile appears below the eyes. Make 40 progress, gain 10 HP.";
        }
    }

    //REQUIRES: obs == 'E';
    //MODIFIES: this
    //EFFECTS: returns result based on option chosen, and also changes
    //         changeInProgress & changeInHealth to reflect chosen option
    public String getEresult() {
        if (chosenOption.equals("A")) {
            changeInHealth = -40;
            changeInProgress = -10;
            return "You fall and break your little toe. Lose 10 progress, and lose 40 HP.";
        } else if (chosenOption.equals("B")) {
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
    }
}
