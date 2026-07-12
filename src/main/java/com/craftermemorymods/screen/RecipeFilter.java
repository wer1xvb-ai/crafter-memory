package com.craftermemorymods.screen;

import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RecipeFilter {
    private List<RecipeEntry<CraftingRecipe>> allRecipes;
    private List<RecipeEntry<CraftingRecipe>> filteredRecipes;
    
    public RecipeFilter(List<RecipeEntry<CraftingRecipe>> recipes) {
        this.allRecipes = new ArrayList<>(recipes);
        this.filteredRecipes = new ArrayList<>(recipes);
    }
    
    /**
     * Filter recipes by search query
     */
    public void search(String query) {
        if (query == null || query.isEmpty()) {
            filteredRecipes = new ArrayList<>(allRecipes);
            return;
        }
        
        String lowerQuery = query.toLowerCase();
        filteredRecipes = allRecipes.stream()
            .filter(recipe -> {
                ItemStack output = recipe.value().getResult(null);
                String name = output.getName().getString().toLowerCase();
                return name.contains(lowerQuery);
            })
            .collect(Collectors.toList());
    }
    
    public List<RecipeEntry<CraftingRecipe>> getFilteredRecipes() {
        return new ArrayList<>(filteredRecipes);
    }
    
    public void reset() {
        filteredRecipes = new ArrayList<>(allRecipes);
    }
}
