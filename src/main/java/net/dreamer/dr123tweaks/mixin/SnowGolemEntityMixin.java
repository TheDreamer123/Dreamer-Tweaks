package net.dreamer.dr123tweaks.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin extends GolemEntity {
    @Shadow public abstract boolean hasPumpkin();
    @Shadow public abstract void setHasPumpkin(boolean hasPumpkin);

    protected SnowGolemEntityMixin(EntityType<? extends GolemEntity> entityType,World world) {
        super(entityType,world);
    }

    @Inject(at = @At("HEAD"), method = "interactMob", cancellable = true)
    public void interactMobInject(PlayerEntity player,Hand hand,CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.CARVED_PUMPKIN) && (this.isAlive() && !this.hasPumpkin())) {
            this.world.playSoundFromEntity(null, this, SoundEvents.BLOCK_WOOD_PLACE, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (!this.world.isClient()) {
                this.setHasPumpkin(true);
                itemStack.decrement(1);
            }
            this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
            cir.setReturnValue(ActionResult.success(this.world.isClient));
        } else if(itemStack.isOf(Items.SNOWBALL)) {
            float f = this.getHealth();
            this.heal(1.0F);
            if (this.getHealth() == f) {
                cir.setReturnValue(ActionResult.PASS);
            } else {
                float g = 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F;
                this.playSound(SoundEvents.BLOCK_SNOW_PLACE, 1.0F, g);
                this.emitGameEvent(GameEvent.MOB_INTERACT, this.getCameraBlockPos());
                if (!player.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                cir.setReturnValue(ActionResult.success(this.world.isClient));
            }
        }
    }
}
