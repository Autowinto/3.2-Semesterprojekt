package worldofzuul.model;

import java.io.IOException;

public class Material extends Item {

    public Material(String name, EnergyType energyType, double posX, double posY, String imagePath, String description) throws IOException {
        super(name, 0.5, energyType, description);
        this.setX(posX);
        this.setY(posY);
        super.loadImage(imagePath);
    }


}
