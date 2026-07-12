package com.craftermemorymods.mixin;

import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.CrafterBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.craftermemorymods.hopper.HopperIntegration;

@Mixin(HopperBlockEntity.class)
public class HopperBlockEntityMixin {
    
    @Inject(method = "transferToSingleInventory", at = @At("HEAD"))
    private static void onTransfer(Inventory from, Inventory to, CallbackInfo ci) {
        // When hopper transfers items, check if destination is a Crafter
        if (to instanceof CrafterBlockEntity crafter) {
            HopperIntegration.loadIngredientsFromHopper(crafter, (HopperBlockEntity) (Object) from);
        }
    }
}
