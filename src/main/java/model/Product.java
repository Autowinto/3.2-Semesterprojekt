package worldofzuul.model;

import java.io.IOException;

public class Product extends Item {
    public Product(String name, EnergyType energyType, double posX, double posY, String imagePath, String description) throws IOException {
        super(name, 1, energyType, description);
        this.setX(posX);
        this.setY(posY);
        super.loadImage(imagePath);
    }
}