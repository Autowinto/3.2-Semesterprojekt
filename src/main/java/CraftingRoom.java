package worldofzuul;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftingRoom extends Room {
    private EnergyType energyType;
    private final Product craftingResult;
    private ArrayList<Material> placedItems = new ArrayList<>();

    public CraftingRoom(String description, EnergyType energyType, Product craftingResult) {
        super(description);
        //this.recipe = (ArrayList<Material>) Arrays.asList(recipe);
        this.energyType = energyType;
        this.craftingResult = craftingResult;
    }

    public Product getCraftingResult() {
        return this.craftingResult;
    }

    public boolean canCraft() {
        // If the size of placedItems contains 3 items, we're ready to craft
        return placedItems.size() == 3;
    }

    public String getPlacedItemsString() {
        String string = "";

        for (Item item : this.placedItems) {
            string += item.getName() + " ";
        }
        return string;
    }

    public void placeItem(Material item) {
        placedItems.add(item);
    }

    public EnergyType getEnergyType() {
        return this.energyType;
    }

}
