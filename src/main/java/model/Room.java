package worldofzuul.model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Room {
    private String dropOffEffect;
    private String dropOffText;
    private EnergyType energyType;
    private ArrayList<Exit> exits;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<DropOff> dropOffs;
    private Image image;

    public Room(String imagePath){
        this.image = loadImage(imagePath);
        exits = new ArrayList<>();
        dropOffs = new ArrayList<>();
    }

    public Room(EnergyType energyType, String dropOffEffect, String dropOffText, String imagePath){
        this.energyType = energyType;
        this.dropOffEffect = dropOffEffect;
        this.dropOffText = dropOffText;
        this.image = loadImage(imagePath);
        exits = new ArrayList<>();
        dropOffs = new ArrayList<>();
    }

    private Image loadImage(String path) {
        try {

        Image testImage = new Image(getClass().getResourceAsStream(path));
        return testImage;
        } catch (NullPointerException e) {
            return new Image(getClass().getResourceAsStream("/item_placeholder.png"));
        }
    }

    public void setExit(Exit exit) {
        exits.add(exit);
    }

    public void setDropOff(DropOff dropOff) {
        dropOffs.add(dropOff);
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

    public ArrayList<Exit> getExits() {
        return this.exits;
    }

    public ArrayList<DropOff> getDropOffs(){
        return this.dropOffs;
    }

    public ArrayList<Item> getItems(){
        return this.items;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addItem(Item newitem) {
        items.add(newitem);
    }
}

