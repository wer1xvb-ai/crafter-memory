package com.craftermemorymods.util;

import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.client.MinecraftClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecipeUtils {
    
    /**
     * Get all crafting recipes from the world
     */
    public static List<RecipeEntry<CraftingRecipe>> getAllCraftingRecipes() {
        List<RecipeEntry<CraftingRecipe>> recipes = new ArrayList<>();
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null && client.world.getRecipeManager() != null) {
            Collection<RecipeEntry<?>> allRecipes = client.world.getRecipeManager().values();
            
            for (RecipeEntry<?> recipe : allRecipes) {
                if (recipe.value() instanceof CraftingRecipe) {
                    recipes.add((RecipeEntry<CraftingRecipe>) recipe);
                }
            }
        }
        
        return recipes;
    }
    
    /**
     * Get recipe ID as string
     */
    public static String getRecipeId(RecipeEntry<?> recipe) {
        return recipe.id().getValue().toString();
    }
}
