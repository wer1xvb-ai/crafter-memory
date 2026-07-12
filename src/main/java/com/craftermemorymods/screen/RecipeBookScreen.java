package com.craftermemorymods.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.item.ItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.block.entity.CrafterBlockEntity;

import java.util.ArrayList;
import java.util.List;

public class RecipeBookScreen extends Screen {
    private final Screen parent;
    private final CrafterBlockEntity crafterEntity;
    private List<RecipeEntry<CraftingRecipe>> recipes = new ArrayList<>();
    private RecipeEntry<CraftingRecipe> selectedRecipe;
    private int scrollOffset = 0;
    private static final int RECIPE_DISPLAY_HEIGHT = 20;
    private static final int RECIPES_PER_PAGE = 10;

    public RecipeBookScreen(Screen parent, CrafterBlockEntity crafterEntity) {
        super(Text.literal("Recipe Book"));
        this.parent = parent;
        this.crafterEntity = crafterEntity;
        this.loadRecipes();
    }

    private void loadRecipes() {
        // Load all crafting recipes from server
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world != null) {
            // This will be populated with actual recipes
        }
    }

    @Override
    protected void init() {
        super.init();
        
        // Close button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("X"), button -> {
            this.close();
        }).dimensions(this.width - 25, 10, 15, 15).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        
        // Draw title
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 10, 0xFFFFFF);
        
        // Draw recipe list
        int yOffset = 40;
        for (int i = scrollOffset; i < Math.min(scrollOffset + RECIPES_PER_PAGE, recipes.size()); i++) {
            RecipeEntry<CraftingRecipe> recipe = recipes.get(i);
            ItemStack output = recipe.value().getResult(null);
            
            // Highlight selected recipe
            if (recipe == selectedRecipe) {
                context.fill(20, yOffset - 2, this.width - 20, yOffset + RECIPE_DISPLAY_HEIGHT, 0xFF8B7355);
            }
            
            // Draw recipe output item icon
            context.drawItem(output, 25, yOffset);
            context.drawText(this.textRenderer, output.getName(), 50, yOffset + 5, 0xFFFFFF, false);
            
            yOffset += RECIPE_DISPLAY_HEIGHT;
        }
        
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int maxScroll = Math.max(0, recipes.size() - RECIPES_PER_PAGE);
        scrollOffset = Math.max(0, Math.min(scrollOffset - (int) verticalAmount, maxScroll));
        return true;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int yOffset = 40;
        for (int i = scrollOffset; i < Math.min(scrollOffset + RECIPES_PER_PAGE, recipes.size()); i++) {
            if (mouseY >= yOffset && mouseY < yOffset + RECIPE_DISPLAY_HEIGHT) {
                selectedRecipe = recipes.get(i);
                return true;
            }
            yOffset += RECIPE_DISPLAY_HEIGHT;
        }
        return super.mouseClicked(mouseX, mouseY, button);
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
