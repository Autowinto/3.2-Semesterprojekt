package worldofzuul.model;

import worldofzuul.model.EnergyType;
import worldofzuul.model.Item;

import java.io.IOException;

public class Material extends Item {

    public Material(String name, EnergyType energyType, String description) {
        super(name, 0.5, energyType, description);
    }

    public Material(String name, EnergyType energyType, double posX, double posY, String imagePath, String description) throws IOException {
        super(name, 0.5, energyType, description);
        this.setX(posX);
        this.setY(posY);
        super.loadImage(imagePath);
    }


}
