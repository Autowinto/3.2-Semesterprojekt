package worldofzuul.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public abstract class Item extends ImageView {
    private String name;
    private double weight;
    private EnergyType energyType;
    private String description;
    private Boolean hoveredOver = false;

    public Item(String name, double weight, EnergyType energyType) {
        this.name = name;
        this.weight = weight;
        this.energyType = energyType;
    }

    public Item(String name, double weight, EnergyType energyType, String description) {
        this.name = name;
        this.weight = weight;
        this.energyType = energyType;
        this.description = description;
    }

    public Item(String name, double weight, EnergyType energyType, double posX, double posY) {
        this.name = name;
        this.weight = weight;
        this.energyType = energyType;
        this.setX(posX);
        this.setY(posY);
    }

    protected void loadImage(String path) throws IOException {
        Image testImage = new Image(getClass().getResourceAsStream(path));
        this.setImage(testImage);
        this.setFitHeight(100);
        this.setFitWidth(100);
    }

    public void setHoveredOver() {
        this.hoveredOver = true;
    }

    public String getName() {
        return this.name;
    }

    public double getWeight() {
        double weight = 0;
        return this.weight;
    }

    public EnergyType getEnergyType() {
        return this.energyType;
    }

    public Boolean getHoveredOver() { return this.hoveredOver; }

    public String getDescription() { return this.description; }
}
