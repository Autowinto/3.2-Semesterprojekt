package worldofzuul.model;

public abstract class Item {
    private String name;
    private double weight;
    private EnergyType energyType;

    public Item(String name, double weight, EnergyType energyType) {
        this.name = name;
        this.weight = weight;
        this.energyType = energyType;
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
}
