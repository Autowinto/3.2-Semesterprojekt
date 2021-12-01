package worldofzuul.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class DropOff extends Rectangle {
    private EnergyType energyType;
    private ImageView dropOffImageView;

    public DropOff(double width, double height, double posX, double posY, EnergyType energyType) {
        this.setWidth(width);
        this.setHeight(height);
        this.setX(posX);
        this.setY(posY);
        this.energyType = energyType;
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.BLACK);
    }


    public EnergyType getEnergyType() {
        return energyType;
    }
}
