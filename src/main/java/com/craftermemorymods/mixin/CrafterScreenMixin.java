package com.craftermemorymods.mixin;

import net.minecraft.client.gui.screen.ingame.CrafterScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.block.entity.CrafterBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.craftermemorymods.screen.RecipeBookScreen;

@Mixin(CrafterScreen.class)
public class CrafterScreenMixin {
    @Shadow
    protected int x;
    @Shadow
    protected int y;
    @Shadow
    protected int backgroundWidth;
    @Shadow
    protected int backgroundHeight;
    
    private CrafterBlockEntity crafterEntity;
    private ButtonWidget recipeBookButton;
    
    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo ci) {
        CrafterScreen screen = (CrafterScreen) (Object) this;
        
        // Get crafter entity from screen (through accessor)
        // This would need proper mixin setup to access the entity
        
        // Add recipe book button like in crafting table
        // Position: right side, near the top
        int buttonX = this.x + this.backgroundWidth - 25;
        int buttonY = this.y + 5;
        
        this.recipeBookButton = ButtonWidget.builder(Text.literal("📖"), button -> {
            // Open recipe book
            if (this.crafterEntity != null) {
                screen.getMinecraftClient().setScreen(
                    new RecipeBookScreen(screen, this.crafterEntity)
                );
            }
        }).dimensions(buttonX, buttonY, 20, 20).build();
        
        screen.addDrawableChild(this.recipeBookButton);
    }
}
