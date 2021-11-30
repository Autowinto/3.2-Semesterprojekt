package worldofzuul.model;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.beans.Visibility;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room {
    private String description;
    private String dropOffEffect;
    private String dropOffText;
    private EnergyType energyType;
    private HashMap<String, Exit> exits;
    private ArrayList<Item> items = new ArrayList<>();
    private Image image;

    public Room(String description, String imagePath) throws IOException {
        this.description = description;
        this.image = loadImage(imagePath);
        exits = new HashMap<String, Exit>();
    }

    public Room(String description, EnergyType energyType, String dropOffEffect, String dropOffText, String imagePath) throws IOException {
        this.description = description;
        this.energyType = energyType;
        this.dropOffEffect = dropOffEffect;
        this.dropOffText = dropOffText;
        this.image = loadImage(imagePath);
        exits = new HashMap<String, Exit>();
    }

    private Image loadImage(String path) throws IOException {
        Image testImage = new Image(getClass().getResourceAsStream(path));
        return testImage;
    }

    public void setExit(String direction, Exit neighbor) {
        exits.put(direction, neighbor);
    }

    public String getLongDescription() {
        return "Du er " + description + ".\n" + getExitString() + getDropOffString();
    }

    private String getExitString() {
        String returnString = "Udgange:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        //Udskriv hvad der kan samles i et rum hvis der er noget
        if (items.size() == 0) {
            return returnString;
        } else {
            returnString += "\nFølgende ting kan samles op: ";
            returnString += getRoomItems();
            return returnString;
        }
    }

    public Image getBackgroundImage() {
        return this.image;
    }

    public EnergyType getEnergyType() {
        return energyType;
    }

    public String getDropOffEffect() {
        return this.dropOffEffect;
    }

    public String getDropOffText() {
        return this.dropOffText;
    }

    public String getDropOffString() {
        if (dropOffEffect != null) {
            return "\nDu kan placere et produkt i dette rum";
        }
        return "";
    }

    public Exit getExit(String direction) {
        return exits.get(direction);
    }

    public HashMap<String, Exit> getExits() {
        return this.exits;
    }

    //Samle ting op fra rummet
    public Item getItem(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(itemName)) {
                return items.get(i);
            }
        }
        return null;
    }

    public ArrayList<Item> getItems( ){
        return this.items;
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.setVisible(false);
    }

    public void addItem(Item newitem) {
        items.add(newitem);
    }

    //En beskrivelse af hvad der er i et rum

    public String getRoomItems() {
        String output = "";
        for (int i = 0; i < items.size(); i++) {
            output += items.get(i).getName();
        }
        return output;
    }
}

