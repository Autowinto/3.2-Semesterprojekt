package worldofzuul.model;

import java.util.ArrayList;

public class CraftingRoom extends Room {
    private final Product craftingResult;
    private EnergyType energyType;
    private ArrayList<Item> placedItems = new ArrayList<>();

    public CraftingRoom(EnergyType energyType, Product craftingResult) {
        super("/Scener/Arbejdsbord.png");
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

    public void placeItem(Item item) {
        placedItems.add(item);
    }
    
    public void removeItem(Item item) {
        placedItems.remove(item);
    }

    public void clearPlacedItems(){
        placedItems.clear();
    }

    public EnergyType getEnergyType() {
        return this.energyType;
    }
}
