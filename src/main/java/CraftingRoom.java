package worldofzuul;

import java.util.ArrayList;
import java.util.Arrays;

public class CraftingRoom extends Room {
    private final ArrayList<Material> recipe;
    private final Product craftingResult;
    private ArrayList<Material> placedItems = new ArrayList<>();

    public CraftingRoom(String description, Material[] recipe, Product craftingResult) {
        super(description);

        this.recipe = (ArrayList<Material>) Arrays.asList(recipe);
        this.craftingResult = craftingResult;
    }

    public ArrayList<Material> getRecipe() {
        return this.recipe;
    }

    public Product getCraftingResult() {
        return this.craftingResult;
    }

}
