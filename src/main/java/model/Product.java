package worldofzuul.model;

import worldofzuul.model.EnergyType;
import worldofzuul.model.Item;

public class Product extends Item {

    public Product(String name, EnergyType energyType) {
        super(name, 1.0, energyType);
    }
}