package worldofzuul.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Item extends ImageView {
    private String name;
    private double weight;
    private EnergyType energyType;
    private String description;
    private Boolean hoveredOver = false;

    public Item(String name, double weight, EnergyType energyType, String description) {
        this.name = name;
        this.weight = weight;
        this.energyType = energyType;
        this.description = description;
    }

    protected void loadImage(String path) {
        Image image = new Image(getClass().getResourceAsStream(path));
        this.setImage(image);
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
        return this.weight;
    }

    public EnergyType getEnergyType() {
        return this.energyType;
    }

    public Boolean getHoveredOver() { return this.hoveredOver; }

    public String getDescription() { return this.description; }
}
