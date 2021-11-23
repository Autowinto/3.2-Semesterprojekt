package worldofzuul.model;

import java.util.ArrayList;

public class CraftingRoom extends Room {
    private final Product craftingResult;
    private EnergyType energyType;
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
