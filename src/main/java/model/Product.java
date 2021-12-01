package worldofzuul.model;

import worldofzuul.model.EnergyType;
import worldofzuul.model.Item;

import java.io.IOException;

public class Product extends Item {

    public Product(String name, EnergyType energyType) {
        super(name, 1.0, energyType);
    }

    public Product(String name, EnergyType energyType, double posX, double posY, String imagePath) throws IOException {
        super(name, 1, energyType);
        this.setX(posX);
        this.setY(posY);

        super.loadImage(imagePath);
    }

}