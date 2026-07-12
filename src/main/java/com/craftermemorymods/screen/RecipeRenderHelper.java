package com.craftermemorymods.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.ItemStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import org.joml.Matrix4f;

public class RecipeRenderHelper {
    
    /**
     * Draw semi-transparent item stack icon
     */
    public static void drawSemiTransparentItem(DrawContext context, ItemStack item, int x, int y, float alpha) {
        if (item.isEmpty()) {
            return;
        }
        
        // Draw the item with reduced opacity
        int color = 0xFFFFFF;
        int alphaValue = (int) (alpha * 255) & 0xFF;
        int colorWithAlpha = (alphaValue << 24) | (color & 0xFFFFFF);
        
        // Draw background
        context.fill(x, y, x + 16, y + 16, colorWithAlpha);
        
        // Draw item
        context.drawItem(item, x, y);
    }
    
    /**
     * Draw ingredient grid with semi-transparent icons
     */
    public static void drawIngredientGrid(DrawContext context, java.util.List<ItemStack> ingredients, int startX, int startY, int columns) {
        int x = startX;
        int y = startY;
        int col = 0;
        
        for (ItemStack ingredient : ingredients) {
            // Draw semi-transparent background for ingredient slot
            context.fill(x, y, x + 18, y + 18, 0x8B7355FF);
            
            // Draw ingredient with semi-transparency
            drawSemiTransparentItem(context, ingredient, x + 1, y + 1, 0.7f);
            
            // Draw border
            context.fill(x, y, x + 18, y + 1, 0xFF8B7355);
            context.fill(x, y, x + 1, y + 18, 0xFF8B7355);
            context.fill(x + 17, y, x + 18, y + 18, 0xFF8B7355);
            context.fill(x, y + 17, x + 18, y + 18, 0xFF8B7355);
            
            x += 20;
            col++;
            
            if (col >= columns) {
                col = 0;
                x = startX;
                y += 20;
            }
        }
    }
}
