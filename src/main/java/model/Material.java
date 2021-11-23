package worldofzuul.model;

import worldofzuul.model.EnergyType;
import worldofzuul.model.Item;

public class Material extends Item {

    public Material(String name, EnergyType energyType) {
        super(name, 0.5, energyType);
    }


}
