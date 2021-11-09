package worldofzuul;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftingRoom extends Room {
    //private final ArrayList<Material> recipe;
    private final Product craftingResult;
    private ArrayList<Material> placedItems = new ArrayList<>();

    public CraftingRoom(String description, Product craftingResult) {
        super(description);
        //this.recipe = (ArrayList<Material>) Arrays.asList(recipe);
        this.craftingResult = craftingResult;
    }

    public Product getCraftingResult() {
        return this.craftingResult;
    }

    public boolean canCraft() {
        // If the size of placedItems contains 3 items, we're ready to craft
        return placedItems.size() == 3;
    }

}
