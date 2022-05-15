package net.dreamer.dr123tweaks.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    @Shadow public abstract ItemStack getStack();
    @Shadow public abstract void setDespawnImmediately();

    public ItemEntityMixin(EntityType<?> type,World world) {
        super(type,world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    public void tickInject(CallbackInfo info) {
        if(getStack().isIn(ItemTags.SAPLINGS)) {
            if(isOnGround()) {
                if(random.nextInt(100) == 0) {
                    BlockState saplingPlacer = Block.getBlockFromItem(getStack().getItem()).getDefaultState();

                    BlockPos.Mutable mutable = new BlockPos.Mutable();
                    mutable.set(getBlockPos().getX(),getBlockPos().getY(),getBlockPos().getZ());

                    if(saplingPlacer.canPlaceAt(world, mutable)) {
                        world.setBlockState(mutable,saplingPlacer);
                        setDespawnImmediately();
                    }
                }
            }
        }
    }
}
