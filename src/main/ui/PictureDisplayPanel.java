package ui;

import model.Obstacle;

import javax.swing.*;
import java.awt.*;

/**
 * Citation for parts of code (especially the loading images from dir):
 * https://github.students.cs.ubc.ca/CPSC210/C3-LectureLabStarter
 */
// represents the main JPanel in GameAppGraphical, displays needed image as a JLabel in the JPanel
public class PictureDisplayPanel extends JPanel {

    private static final int IMAGE_SCALE_FACTOR = 3;
    private ImageIcon toDisplay;
    private String sep;
    private JLabel actualDisplay;

    private ImageIcon staticImg;
    private ImageIcon intro;
    private ImageIcon goodEnd;
    private ImageIcon badEnd;
    private ImageIcon chestImage;
    private ImageIcon obs1Intro;
    private ImageIcon obs1A;
    private ImageIcon obs1B;
    private ImageIcon obs1C;
    private ImageIcon obs2Intro;
    private ImageIcon obs2A;
    private ImageIcon obs2B;
    private ImageIcon obs2C;
    private ImageIcon obs3Intro;
    private ImageIcon obs3A;
    private ImageIcon obs3B;
    private ImageIcon obs3C;
    private ImageIcon obs4Intro;
    private ImageIcon obs4A;
    private ImageIcon obs4B;
    private ImageIcon obs4C;
    private ImageIcon obs5Intro;
    private ImageIcon obs5A;
    private ImageIcon obs5B;
    private ImageIcon obs5C;

    //EFFECTS: instantiates fields
    public PictureDisplayPanel() {
        sep = System.getProperty("file.separator");
        toDisplay = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "intro.png");
        toDisplay = scaleImage(toDisplay);
        actualDisplay = new JLabel(toDisplay);
        add(actualDisplay);

        setUpImages();
    }

    //MODIFIES: this
    //EFFECTS: removes the current image from this and adds the new image based
    //         off the string input
    public void setImage(String str) {
        remove(actualDisplay);
        if (str.equals("intro")) {
            actualDisplay = new JLabel(intro);
        } else if (str.equals("good ending")) {
            actualDisplay = new JLabel(goodEnd);
        } else if (str.equals("bad ending")) {
            actualDisplay = new JLabel(badEnd);
        } else if (str.equals("static img")) {
            actualDisplay = new JLabel(staticImg);
        } else {
            actualDisplay = new JLabel(chestImage);
        }
        add(actualDisplay);
        validate();
    }

    //MODIFIES: this
    //EFFECTS: removes the current image displayed,
    //         and sets the actualDisplay to the needed obstacle image
    public void setObsImage(Obstacle obs) {
        remove(actualDisplay);
        if (obs.getNum() == 1) {
            setImageObs1(obs);
        } else if (obs.getNum() == 2) {
            setImageObs2(obs);
        } else if (obs.getNum() == 3) {
            setImageObs3(obs);
        } else if (obs.getNum() == 4) {
            setImageObs4(obs);
        } else {
            setImageObs5(obs);
        }
        add(actualDisplay);
    }

    //MODIFIES: this
    //EFFECTS: sets the image based off the obstacles chosenOption
    private void setImageObs1(Obstacle obs) {
        if (obs.getChosenOption().equals("Z")) {
            actualDisplay = new JLabel(obs1Intro);
        } else if (obs.getChosenOption().equals("A")) {
            actualDisplay = new JLabel(obs1A);
        } else if (obs.getChosenOption().equals("B")) {
            actualDisplay = new JLabel(obs1B);
        } else {
            actualDisplay = new JLabel(obs1C);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the image based off the obstacles chosenOption
    private void setImageObs2(Obstacle obs) {
        if (obs.getChosenOption().equals("Z")) {
            actualDisplay = new JLabel(obs2Intro);
        } else if (obs.getChosenOption().equals("A")) {
            actualDisplay = new JLabel(obs2A);
        } else if (obs.getChosenOption().equals("B")) {
            actualDisplay = new JLabel(obs2B);
        } else {
            actualDisplay = new JLabel(obs2C);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the image based off the obstacles chosenOption
    private void setImageObs3(Obstacle obs) {
        if (obs.getChosenOption().equals("Z")) {
            actualDisplay = new JLabel(obs3Intro);
        } else if (obs.getChosenOption().equals("A")) {
            actualDisplay = new JLabel(obs3A);
        } else if (obs.getChosenOption().equals("B")) {
            actualDisplay = new JLabel(obs3B);
        } else {
            actualDisplay = new JLabel(obs3C);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the image based off the obstacles chosenOption
    private void setImageObs4(Obstacle obs) {
        if (obs.getChosenOption().equals("Z")) {
            actualDisplay = new JLabel(obs4Intro);
        } else if (obs.getChosenOption().equals("A")) {
            actualDisplay = new JLabel(obs4A);
        } else if (obs.getChosenOption().equals("B")) {
            actualDisplay = new JLabel(obs4B);
        } else {
            actualDisplay = new JLabel(obs4C);
        }
    }

    //MODIFIES: this
    //EFFECTS: sets the image based off the obstacles chosenOption
    private void setImageObs5(Obstacle obs) {
        if (obs.getChosenOption().equals("Z")) {
            actualDisplay = new JLabel(obs5Intro);
        } else if (obs.getChosenOption().equals("A")) {
            actualDisplay = new JLabel(obs5A);
        } else if (obs.getChosenOption().equals("B")) {
            actualDisplay = new JLabel(obs5B);
        } else {
            actualDisplay = new JLabel(obs5C);
        }
    }

    //MODIFIES: this
    //EFFECTS: instantiates fields that are images, corresponds to an image in the dir
    private void setUpImages() {
        intro = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "intro.png");
        goodEnd = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "goodEnd.png");
        badEnd = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "badEnd.png");
        chestImage = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "chest.png");
        staticImg = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "static.png");

        intro = scaleImage(intro);
        goodEnd = scaleImage(goodEnd);
        badEnd = scaleImage(badEnd);
        chestImage = scaleImage(chestImage);
        staticImg = scaleImage(staticImg);

        setUpObstacleImages();
    }

    //MODIFIES: this
    //EFFECTS: instantiates fields that are images, corresponds to an image in the dir
    private void setUpObstacleImages() {
        obs1Intro = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs1Intro.png");
        obs1A = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs1A.png");
        obs1B = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs1B.png");
        obs1C = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs1C.png");

        obs2Intro = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs2Intro.png");
        obs2A = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs2A.png");
        obs2B = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs2BC.png");
        obs2C = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs2BC.png");

        obs3Intro = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs3IntroB.png");
        obs3A = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs3A.png");
        setUpObstacleImagesPart2();
    }

    //MODIFIES: this
    //EFFECTS: instantiates fields that are images, corresponds to an image in the dir
    private void setUpObstacleImagesPart2() {
        obs3B = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs3IntroB.png");
        obs3C = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs3C.png");

        obs4Intro = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs4Intro.png");
        obs4A = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs4A.png");
        obs4B = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs4B.png");
        obs4C = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs4C.png");

        obs5Intro = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs5Intro.png");
        obs5A = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs5A.png");
        obs5B = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs5B.png");
        obs5C = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "Obstacle Images" + sep + "obs5C.png");

        scaleAllObsImages();
    }

    //MODIFIES: this
    //EFFECTS: scales all the images by the IMAGE_SCALE_FACTOR
    private void scaleAllObsImages() {
        obs1Intro = scaleImage(obs1Intro);
        obs1A = scaleImage(obs1A);
        obs1B = scaleImage(obs1B);
        obs1C = scaleImage(obs1C);

        obs2Intro = scaleImage(obs2Intro);
        obs2A = scaleImage(obs2A);
        obs2B = scaleImage(obs2B);
        obs2C = scaleImage(obs2C);

        obs3Intro = scaleImage(obs3Intro);
        obs3A = scaleImage(obs3A);
        obs3B = scaleImage(obs3B);
        obs3C = scaleImage(obs3C);

        obs4Intro = scaleImage(obs4Intro);
        obs4A = scaleImage(obs4A);
        obs4B = scaleImage(obs4B);
        obs4C = scaleImage(obs4C);

        obs5Intro = scaleImage(obs5Intro);
        obs5A = scaleImage(obs5A);
        obs5B = scaleImage(obs5B);
        obs5C = scaleImage(obs5C);
    }

    //EFFECTS: returns the scaled image, scaled by IMAGE_SCALE_FACTOR
    private ImageIcon scaleImage(ImageIcon img) {
        Image scaled = img.getImage();
        scaled = scaled.getScaledInstance(img.getIconWidth() / IMAGE_SCALE_FACTOR,
                img.getIconHeight() / IMAGE_SCALE_FACTOR, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }
}
