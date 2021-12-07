package worldofzuul.model;

import java.io.IOException;
import java.util.ArrayList;

public class CraftingRoom extends Room {
    private final Product craftingResult;
    private EnergyType energyType;
    private ArrayList<Item> placedItems = new ArrayList<>();

    public CraftingRoom(String description, EnergyType energyType, Product craftingResult) throws IOException {
        super(description, "/Scener/Arbejdsbord.png");
        //this.recipe = (ArrayList<Material>) Arrays.asList(recipe);
        this.energyType = energyType;
        this.craftingResult = craftingResult;
    }

    public ArrayList<Item> getPlacedItems() {
        return placedItems;
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

    public void placeItem(Item item) {
        placedItems.add(item);
    }

    public EnergyType getEnergyType() {
        return this.energyType;
    }

}
