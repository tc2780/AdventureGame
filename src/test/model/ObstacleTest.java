package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ObstacleTest { //15 possible scenarios (listed below)

    private Obstacle a;
    private Obstacle b;
    private Obstacle c;
    private Obstacle d;
    private Obstacle e;
    private Obstacle f;
    private Obstacle g;
    private Obstacle h;
    private Obstacle i;
    private Obstacle j;
    private Obstacle k;
    private Obstacle l;
    private Obstacle m;
    private Obstacle n;
    private Obstacle o;
    private Obstacle ran;

    @BeforeEach
    public void setUp() {
        a = new Obstacle(1);
        b = new Obstacle(1);
        c = new Obstacle(1);
        d = new Obstacle(2);
        e = new Obstacle(2);
        f = new Obstacle(2);
        g = new Obstacle(3);
        h = new Obstacle(3);
        i = new Obstacle(3);
        j = new Obstacle(4);
        k = new Obstacle(4);
        l = new Obstacle(4);
        m = new Obstacle(5);
        n = new Obstacle(5);
        o = new Obstacle(5);
        ran = new Obstacle();
        setUpChosenOptions();
    }

    public void setUpChosenOptions() {
        a.setChosenOption("A");
        b.setChosenOption("B");
        c.setChosenOption("C");
        d.setChosenOption("A");
        e.setChosenOption("B");
        f.setChosenOption("C");
        g.setChosenOption("A");
        h.setChosenOption("B");
        i.setChosenOption("C");
        j.setChosenOption("A");
        k.setChosenOption("B");
        l.setChosenOption("C");
        m.setChosenOption("A");
        n.setChosenOption("B");
        o.setChosenOption("C");
        ran.setChosenOption("A");
    }

    @Test  //test set and get
    public void testChosenOption() {
        Obstacle test = new Obstacle(1);
        assertEquals("Z", test.getChosenOption());
        test.setChosenOption("C");
        assertEquals("C", test.getChosenOption());
    }

    @Test
    public void testChangeInProgress() {
        assertEquals(20, a.getChangeInProgress());
        assertEquals(10, b.getChangeInProgress());
        assertEquals(30, c.getChangeInProgress());

        assertEquals(-10, d.getChangeInProgress());
        assertEquals(20, e.getChangeInProgress());
        assertEquals(10, f.getChangeInProgress());

        assertEquals(-10, g.getChangeInProgress());
        assertEquals(10, h.getChangeInProgress());
        assertEquals(20, i.getChangeInProgress());

        assertEquals(20, j.getChangeInProgress());
        assertEquals(-20, k.getChangeInProgress());
        assertEquals(40, l.getChangeInProgress());

        assertEquals(-10, m.getChangeInProgress());
        assertEquals(30, n.getChangeInProgress());
        assertEquals(-20, o.getChangeInProgress());
    }

    @Test
    public void testChangeInProgressRandom() {
        int x = 0;
        switch (ran.getNum()) {
            case 1:
                x = 20;
                break;
            case 2:
                x = -10;
                break;
            case 3:
                x = -10;
                break;
            case 4:
                x = 20;
                break;
            default:
                x = -10;
        }
        assertEquals(x, ran.getChangeInProgress());
    }

    @Test
    public void testChangeInHealth() {
        assertEquals(-30, a.getChangeInHealth());
        assertEquals(-25, b.getChangeInHealth());
        assertEquals(0, c.getChangeInHealth());

        assertEquals(-30, d.getChangeInHealth());
        assertEquals(30, e.getChangeInHealth());
        assertEquals(0, f.getChangeInHealth());

        assertEquals(-30, g.getChangeInHealth());
        assertEquals(0, h.getChangeInHealth());
        assertEquals(-10, i.getChangeInHealth());

        assertEquals(-40, j.getChangeInHealth());
        assertEquals(-30, k.getChangeInHealth());
        assertEquals(10, l.getChangeInHealth());

        assertEquals(-40, m.getChangeInHealth());
        assertEquals(20, n.getChangeInHealth());
        assertEquals(-40, o.getChangeInHealth());
    }

    @Test
    public void testRandomChangeInHealth() {
        int x = 0;
        switch (ran.getNum()) {
            case 1:
                x = -30;
                break;
            case 2:
                x = -30;
                break;
            case 3:
                x = -30;
                break;
            case 4:
                x = -40;
                break;
            default:
                x = -40;
        }
        assertEquals(x, ran.getChangeInHealth());
    }

    @Test
    public void testLookUpObstacle() {
        assertEquals("You encounter a living tree. It looks happy to see you.", a.lookUpObstacle());
        assertEquals("You encounter a living tree. It looks happy to see you.", b.lookUpObstacle());
        assertEquals("You encounter a living tree. It looks happy to see you.", c.lookUpObstacle());

        assertEquals("You encounter a blue and white panda. He seems angry...", d.lookUpObstacle());
        assertEquals("You encounter a blue and white panda. He seems angry...", e.lookUpObstacle());
        assertEquals("You encounter a blue and white panda. He seems angry...", f.lookUpObstacle());

        assertEquals("You encounter a paper plane. It's lying on the floor menacingly", g.lookUpObstacle());
        assertEquals("You encounter a paper plane. It's lying on the floor menacingly", h.lookUpObstacle());
        assertEquals("You encounter a paper plane. It's lying on the floor menacingly", i.lookUpObstacle());

        assertEquals("You encounter a lemon. It is a very bright yellow.", j.lookUpObstacle());
        assertEquals("You encounter a lemon. It is a very bright yellow.", k.lookUpObstacle());
        assertEquals("You encounter a lemon. It is a very bright yellow.", l.lookUpObstacle());

        assertEquals("You encounter a huge boulder. On top is a goat.", m.lookUpObstacle());
        assertEquals("You encounter a huge boulder. On top is a goat.", n.lookUpObstacle());
        assertEquals("You encounter a huge boulder. On top is a goat.", o.lookUpObstacle());
    }

    @Test
    public void testRandomLookUpObstacle() {
        String str;
        switch (ran.getNum()) {
            case 1:
                str = "You encounter a living tree. It looks happy to see you.";
                break;
            case 2:
                str = "You encounter a blue and white panda. He seems angry...";
                break;
            case 3:
                str = "You encounter a paper plane. It's lying on the floor menacingly";
                break;
            case 4:
                str = "You encounter a lemon. It is a very bright yellow.";
                break;
            default:
                str = "You encounter a huge boulder. On top is a goat.";
        }

        assertEquals(str, ran.lookUpObstacle());
    }

    @Test
    public void testGetOptions() {
        assertEquals("A: Try to walk around it. \nB: Set it on fire. \nC: Wave.", a.getOptions());
        assertEquals("A: Try to walk around it. \nB: Set it on fire. \nC: Wave.", b.getOptions());
        assertEquals("A: Try to walk around it. \nB: Set it on fire. \nC: Wave.", c.getOptions());

        assertEquals("A: Give it some water. \nB: Poke it. \nC: Engage in a stare off.", d.getOptions());
        assertEquals("A: Give it some water. \nB: Poke it. \nC: Engage in a stare off.", e.getOptions());
        assertEquals("A: Give it some water. \nB: Poke it. \nC: Engage in a stare off.", f.getOptions());

        assertEquals("A: Pick it up. \nB: Put a rock on it. \nC: Make it a friend.", g.getOptions());
        assertEquals("A: Pick it up. \nB: Put a rock on it. \nC: Make it a friend.", h.getOptions());
        assertEquals("A: Pick it up. \nB: Put a rock on it. \nC: Make it a friend.", i.getOptions());

        assertEquals("A: Pick it up and eat it. \nB: Step over it. \nC: Give it googly eyes.", j.getOptions());
        assertEquals("A: Pick it up and eat it. \nB: Step over it. \nC: Give it googly eyes.", k.getOptions());
        assertEquals("A: Pick it up and eat it. \nB: Step over it. \nC: Give it googly eyes.", l.getOptions());

        assertEquals("A: Try to climb the boulder. \nB: Throw a leaf at the goat. \nC: Sit and wait.", m.getOptions());
        assertEquals("A: Try to climb the boulder. \nB: Throw a leaf at the goat. \nC: Sit and wait.", n.getOptions());
        assertEquals("A: Try to climb the boulder. \nB: Throw a leaf at the goat. \nC: Sit and wait.", o.getOptions());
    }

    @Test
    public void testRandomGetOptions() {
        String str;
        switch (ran.getNum()) {
            case 1:
                str = "A: Try to walk around it. \nB: Set it on fire. \nC: Wave.";
                break;
            case 2:
                str = "A: Give it some water. \nB: Poke it. \nC: Engage in a stare off.";
                break;
            case 3:
                str = "A: Pick it up. \nB: Put a rock on it. \nC: Make it a friend.";
                break;
            case 4:
                str = "A: Pick it up and eat it. \nB: Step over it. \nC: Give it googly eyes.";
                break;
            default:
                str = "A: Try to climb the boulder. \nB: Throw a leaf at the goat. \nC: Sit and wait.";
        }

        assertEquals(str, ran.getOptions());
    }

    @Test
    public void testGetResult() {
        assertEquals("You get smacked by a branch. Make 20 progress, but lose 30HP.", a.getResult());
        assertEquals("The tree burns to ashes, but you feel tremendous guilt. Make 10 progress, but lose 25HP.", b.getResult());
        assertEquals("The tree waves back. Make 30 progress.", c.getResult());

        assertEquals("He spills the water. Now there's an angry wet panda after you. Lose 10 progress and 20HP", d.getResult());
        assertEquals("The panda turns out to be ticklish. Make 20 progress, gain 30 HP.", e.setUpResult());
        assertEquals("You stare into it's eyes. The panda wins and seems happy about it. Make 10 progress.", f.getResult());

        assertEquals("Turns out it's actually a really good camouflaging chameleon."
                + " And he is angry you disturbed him. Lose 10 progress and 30 HP.", g.getResult());
        assertEquals("Nothing happens. Make 20 progress.", h.getResult());
        assertEquals("The airplane starts bouncing in joy."
                + " It accidentally hits your leg. Make 20 progress, lose 10HP.", i.getResult());

        assertEquals("It is extremely sour. Make 20 progress, but lose 40HP.", j.getResult());
        assertEquals("You step over it and fall right into a trap. Lose 20 progress, and lose 30HP.", k.getResult());
        assertEquals("A toothy smile appears below the eyes. Make 40 progress, gain 10 HP..", l.getResult());

        assertEquals("You fall and break your little toe. Lose 10 progress, and lose 40 HP.", m.getResult());
        assertEquals("The goat eats the leaf. In thanks, it opens a tunnel for you to pass"
                + " through and gives you a potion. Make 20 progress, and gain 20HP.", n.getResult());
        assertEquals("After 10 days, it starts to snow and you get a cold. The goat laughs at you. "
                + "Lose 20 progress, and lose 40HP.", o.getResult());
    }

    @Test
    public void testRandomGetResult() {
        String str;
        switch (ran.getNum()) {
            case 1:
                str = "You get smacked by a branch. Make 20 progress, but lose 30HP.";
                break;
            case 2:
                str = "He spills the water. Now there's an angry wet panda after you. Lose 10 progress and 20HP";
                break;
            case 3:
                str = "Turns out it's actually a really good camouflaging chameleon. And he is angry you disturbed him. Lose 10 progress and 30 HP.";
                break;
            case 4:
                str = "It is extremely sour. Make 20 progress, but lose 40HP.";
                break;
            default:
                str = "You fall and break your little toe. Lose 10 progress, and lose 40 HP.";
        }
        assertEquals(str, ran.setUpResult());
    }

    @Test
    public void testChoiceNotChosenYet() {
        Obstacle test = new Obstacle();
        assertEquals("Z", test.getChosenOption());
        assertEquals("User has not selected option yet.", test.setUpResult());

    }
}

/*
All scenatios:

obs:
1. "You encounter a living tree. It looks happy to see you."
    options:
    A: Try to walk around it.     (a)
    B: Set it on fire.            (b)
    C: Wave."                     (c)

2. "You encounter a blue and white panda. He seems angry..."
    options:
    A: Give it some water.       (d)
    B: Poke it.                  (e)
    C: Engage in a stare off.    (f)

3. "You encounter a paper plane. It's lying on the floor menacingly"
    options:
    A: Pick it up.               (g)
    B: Put a rock on it.         (h)
    C: Make it a friend.         (i)

4. "You encounter a lemon. It is a very bright yellow."
    options:
    A: Pick it up and eat it.    (j)
    B: Step over it.             (k)
    C: Give it googly eyes.      (l)

5. "You encounter a huge boulder. On top is a goat."
    options:
    A: Try to climb the boulder. (m)
    B: Throw a leaf at the goat. (n)
    C: Sit and wait.             (o)


 */