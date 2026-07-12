package com.craftermemorymods.hopper;

import net.minecraft.block.entity.CrafterBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import com.craftermemorymods.recipe.RecipeMemory;
import com.craftermemorymods.recipe.SavedRecipe;

import java.util.List;

public class HopperIntegration {
    
    /**
     * Load ingredients from hopper into crafter based on saved recipe
     */
    public static void loadIngredientsFromHopper(CrafterBlockEntity crafter, HopperBlockEntity hopper) {
        // Get recipe memory from crafter
        RecipeMemory memory = getRecipeMemory(crafter);
        
        if (!memory.hasRecipe()) {
            return;
        }
        
        SavedRecipe savedRecipe = memory.getSavedRecipe();
        List<ItemStack> ingredients = savedRecipe.getIngredients();
        
        // For each ingredient in the recipe, try to extract from hopper
        for (int i = 0; i < ingredients.size(); i++) {
            ItemStack ingredient = ingredients.get(i);
            
            // Try to transfer ingredient from hopper to crafter
            transferIngredient(hopper, crafter, ingredient, i);
        }
    }
    
    /**
     * Transfer a single ingredient from hopper to crafter slot
     */
    private static void transferIngredient(HopperBlockEntity hopper, CrafterBlockEntity crafter, ItemStack ingredient, int slotIndex) {
        Inventory hopperInventory = (Inventory) hopper;
        Inventory crafterInventory = (Inventory) crafter;
        
        // Find the ingredient in hopper
        for (int i = 0; i < hopperInventory.size(); i++) {
            ItemStack hopperStack = hopperInventory.getStack(i);
            
            if (hopperStack.isEmpty()) {
                continue;
            }
            
            // Check if this item matches the required ingredient
            if (ItemStack.areItemsAndComponentsEqual(hopperStack, ingredient)) {
                // Transfer one item from hopper to crafter slot
                ItemStack toTransfer = hopperStack.split(1);
                
                if (!crafterInventory.getStack(slotIndex).isEmpty()) {
                    // Slot already has item, skip
                    hopperStack.increment(1);
                    continue;
                }
                
                crafterInventory.setStack(slotIndex, toTransfer);
                hopperInventory.markDirty();
                crafterInventory.markDirty();
                return;
            }
        }
    }
    
    /**
     * Get recipe memory from crafter block entity
     */
    private static RecipeMemory getRecipeMemory(CrafterBlockEntity crafter) {
        // This will be implemented through mixin
        return null;
    }
}
