package worldofzuul.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DropOff extends Rectangle {
    private EnergyType energyType;

    public DropOff(double width, double height, double posX, double posY, EnergyType energyType) {
        this.setWidth(width);
        this.setHeight(height);
        this.setX(posX);
        this.setY(posY);
        this.energyType = energyType;
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.TRANSPARENT);
    }

    public EnergyType getEnergyType() {
        return energyType;
    }
}
