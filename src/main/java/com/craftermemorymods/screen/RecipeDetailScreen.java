package com.craftermemorymods.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.text.Text;
import net.minecraft.item.ItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.block.entity.CrafterBlockEntity;
import com.craftermemorymods.recipe.SavedRecipe;

import java.util.List;

public class RecipeDetailScreen extends Screen {
    private final Screen parent;
    private final CrafterBlockEntity crafterEntity;
    private final RecipeEntry<CraftingRecipe> recipe;
    private List<ItemStack> ingredients;
    private ItemStack output;
    private ButtonWidget saveButton;
    private ButtonWidget replaceButton;
    private ButtonWidget forgetButton;

    public RecipeDetailScreen(Screen parent, CrafterBlockEntity crafterEntity, RecipeEntry<CraftingRecipe> recipe) {
        super(Text.literal("Recipe Details"));
        this.parent = parent;
        this.crafterEntity = crafterEntity;
        this.recipe = recipe;
        this.loadRecipeDetails();
    }

    private void loadRecipeDetails() {
        // Load recipe ingredients and output
        if (recipe != null) {
            CraftingRecipe craftingRecipe = recipe.value();
            // Extract ingredients from recipe
        }
    }

    @Override
    protected void init() {
        super.init();
        
        int centerX = this.width / 2;
        int startY = 100;
        
        // Save button
        this.saveButton = this.addDrawableChild(ButtonWidget.builder(Text.literal("Remember Recipe"), button -> {
            this.saveRecipe();
        }).dimensions(centerX - 100, startY, 95, 20).build());
        
        // Replace button (only if recipe already saved)
        this.replaceButton = this.addDrawableChild(ButtonWidget.builder(Text.literal("Replace Recipe"), button -> {
            this.replaceRecipe();
        }).dimensions(centerX + 5, startY, 95, 20).build());
        this.replaceButton.visible = false;
        
        // Forget button (only if recipe already saved)
        this.forgetButton = this.addDrawableChild(ButtonWidget.builder(Text.literal("Forget Recipe"), button -> {
            this.forgetRecipe();
        }).dimensions(centerX - 47, startY + 30, 95, 20).build());
        this.forgetButton.visible = false;
        
        // Close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), button -> {
            this.close();
        }).dimensions(centerX - 50, this.height - 30, 100, 20).build());
        
        this.updateButtonStates();
    }

    private void updateButtonStates() {
        // Check if recipe is already saved
        // If yes: hide Save, show Replace and Forget
        // If no: show Save, hide Replace and Forget
    }

    private void saveRecipe() {
        SavedRecipe savedRecipe = new SavedRecipe(recipe, ingredients, output);
        // Save to crafter entity
    }

    private void replaceRecipe() {
        SavedRecipe savedRecipe = new SavedRecipe(recipe, ingredients, output);
        // Replace saved recipe
    }

    private void forgetRecipe() {
        // Clear saved recipe
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        
        // Draw recipe title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        
        // Draw output item
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Output:"), this.width / 2, 50, 0xFFFFFF);
        context.drawItem(output, this.width / 2 - 8, 60);
        
        // Draw ingredients with semi-transparent icons
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Ingredients:"), this.width / 2, 85, 0xFFFFFF);
        int ingredientX = this.width / 2 - (ingredients.size() * 20) / 2;
        for (ItemStack ingredient : ingredients) {
            // Draw semi-transparent ingredient icon
            context.drawItem(ingredient, ingredientX, 95);
            ingredientX += 20;
        }
        
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}
