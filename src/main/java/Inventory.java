package worldofzuul;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items = new ArrayList<Item>();
    private double weightLimit;
    private double currentWeight;

    public Inventory() {
        this.weightLimit = 1.0;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public ArrayList<Item> getItems() {
        return this.items;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public String getName() {
        for (Item item : items) {
            return item.getName();
        }
        return null;
    }

    public double getWeightLimit(){
        return this.weightLimit;
    }

    public double getCurrentWeight(){
        for (Item item : items){
            this.currentWeight += item.getWeight();
        }
        return this.currentWeight;

    }

    public void show() {
        for (Item item : items) {
            System.out.println(item.getName());
        }
    }

}
