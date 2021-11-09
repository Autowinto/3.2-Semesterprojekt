package worldofzuul;

import java.util.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;

public class Room {
    private String description;
    private String name;
    private String dropOff;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items = new ArrayList<>();

    public Room(String description, Item[] items) {
        this.description = description;
        this.items = new ArrayList<Item>(Arrays.asList((items)));
    }

    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }

    public Room(String description, String dropOff, String name) {
        this.description = description;
        this.dropOff = dropOff;
        exits = new HashMap<String, Room>();
        this.name = name;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public String getShortDescription() {
        return description;
    }

    public String getLongDescription() {
        return "Du er " + description + ".\n" + getExitString() +
                (getDropOffString().length() == 0 ? "" : ".\n" + getDropOffString());
    }

    public String getName() {
        return name;
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
            returnString += "\nFølgende ting kan samles op:\n";
            returnString += getRoomItems();
            return returnString;
        }


    }

    public String getDropOffString() {
        if (getDropoff() != null) {
            return "Du kan placere et produkt i dette rum";
        }
        return "";
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public String getDropoff() {
        return this.dropOff;
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

    public void removeItem(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(itemName)) {
                items.remove(i);
            }
        }
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

