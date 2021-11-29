package worldofzuul.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class Inventory {
    private ObservableList<Item> items = FXCollections.observableArrayList();
    private double weightLimit;

    public Inventory() {
        this.weightLimit = 1.0;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public ObservableList<Item> getItems() {
        return this.items;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }


    public double getWeightLimit() {
        return this.weightLimit;
    }

    public double getCurrentWeight() {
        double currentWeight = 0.0;
        for (Item item : items) {
            currentWeight += item.getWeight();
        }
        return currentWeight;

    }
}
