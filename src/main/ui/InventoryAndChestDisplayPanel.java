package ui;

import model.Inventory;
import model.Item;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class InventoryAndChestDisplayPanel extends JPanel {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 500;
    private static final int IMAGE_SCALE_FACTOR = 3;
    private ImageIcon item1;
    private ImageIcon item2;
    private ImageIcon item3;
    private ImageIcon item4;
    private ImageIcon item5;
    private Inventory inventory;
    private ImageIcon chestImage;

    private String sep;
    private int num; //if num = 1, set up as chest image, else as inventory

    public InventoryAndChestDisplayPanel(Inventory inventory, int num) {
        this.inventory = inventory;
        this.num = num;
        sep = System.getProperty("file.separator");
        setLayout(new BorderLayout());
//        setLayout(new GridBagLayout()); //TODO: maybe make the layout better after
        setUpItemImages();
        if (num == 1) {
            JLabel chestImg = new JLabel(chestImage);
            add(chestImg, BorderLayout.NORTH);
        } else {
            JLabel inventImg = new JLabel(chestImage);
            add(inventImg, BorderLayout.NORTH); //TODO: make a inventory image
        }
        if (inventory.length() == 1) {
            displayOne();
        } else if (inventory.length() == 2) {
            displayTwo();
        } else {
            displayThree();
        }
        validate();
//        setMaximumSize(new Dimension(1000, 500));
    }

    public InventoryAndChestDisplayPanel(ArrayList<Item> stuff, int num) {
        this.inventory = new Inventory();
        for (int i = 0; i < stuff.size(); i++) {
            inventory.addItem(stuff.get(i));
        }
        this.num = num;
        sep = System.getProperty("file.separator");
        setLayout(new BorderLayout());
//        setLayout(new GridBagLayout()); //TODO: maybe make the layout better after
        setUpItemImages();
        if (num == 1) {
            JLabel chestImg = new JLabel(chestImage);
            add(chestImg, BorderLayout.NORTH);
        } else {
            JLabel inventImg = new JLabel(chestImage);
            add(inventImg, BorderLayout.NORTH); //TODO: make a inventory image
        }
        if (inventory.length() == 1) {
            displayOne();
        } else if (inventory.length() == 2) {
            displayTwo();
        } else {
            displayThree();
        }
        validate();
    }

    private void displayOne() {
        Item i = inventory.getItemAtSpot(1);
        JLabel img = findImage(i.getNum());

        add(img, BorderLayout.CENTER);
    }

    private void displayTwo() {
        Item one = inventory.getItemAtSpot(1);
        Item two = inventory.getItemAtSpot(1);

        JLabel firstItem = findImage(one.getNum());
        JLabel secItem = findImage(two.getNum());

        add(firstItem, BorderLayout.WEST);
        add(secItem, BorderLayout.EAST);
    }

    private void displayThree() {
        Item one = inventory.getItemAtSpot(1);
        Item two = inventory.getItemAtSpot(1);
        Item three = inventory.getItemAtSpot(1);

        JLabel firstItem = findImage(one.getNum());
        JLabel secItem = findImage(two.getNum());
        JLabel thirdItem = findImage(three.getNum());

        add(firstItem, BorderLayout.WEST);
        add(secItem, BorderLayout.CENTER);
        add(thirdItem, BorderLayout.EAST);
    }

    private JLabel findImage(int i) {
        if (i == 1) {
            return new JLabel(item1);
        } else if (i == 2) {
            return new JLabel(item2);
        } else if (i == 3) {
            return new JLabel(item3);
        } else if (i == 4) {
            return new JLabel(item4);
        } else {
            return new JLabel(item5);
        }
    }

    private void setUpItemImages() {
        item1 = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "1kiwi.png");
        item2 = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "2waterbottle.png");
        item3 = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "3feather.png");
        item4 = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "4book.png");
        item5 = new ImageIcon(System.getProperty("user.dir") + sep
                + "data" + sep + "5ladybug.png");
        chestImage = new ImageIcon (System.getProperty("user.dir") + sep
        + "data" + sep + "chest.png");

        item1 = scaleImage(item1);
        item2 = scaleImage(item2);
        item3 = scaleImage(item3);
        item4 = scaleImage(item4);
        item5 = scaleImage(item5);
        chestImage = scaleImage(chestImage, 5);


    }

    private ImageIcon scaleImage(ImageIcon img) {
        Image scaled = img.getImage();
        scaled = scaled.getScaledInstance(img.getIconWidth() / IMAGE_SCALE_FACTOR,
                img.getIconHeight() / IMAGE_SCALE_FACTOR, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private ImageIcon scaleImage(ImageIcon img, int factor) {
        Image scaled = img.getImage();
        scaled = scaled.getScaledInstance(img.getIconWidth() / factor,
                img.getIconHeight() / factor, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }


}
