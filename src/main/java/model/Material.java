package worldofzuul.model;

import worldofzuul.model.EnergyType;
import worldofzuul.model.Item;

import java.io.IOException;

public class Material extends Item {

    public Material(String name, EnergyType energyType) {
        super(name, 0.5, energyType);
    }

    public Material(String name, EnergyType energyType, double posX, double posY, String imagePath) throws IOException {
        super(name, 0.5, energyType);
        this.setX(posX);
        this.setY(posY);
        super.loadImage(imagePath);
    }


}
