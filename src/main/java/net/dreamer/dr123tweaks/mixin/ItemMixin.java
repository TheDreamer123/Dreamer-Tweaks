package net.dreamer.dr123tweaks.mixin;

import net.dreamer.dr123tweaks.DreamerTweaks;
import net.dreamer.dr123tweaks.entity.BrickEntity;
import net.dreamer.dr123tweaks.entity.NetherBrickEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Shadow public abstract ItemStack getDefaultStack();

    @Inject(at = @At("HEAD"), method = "use", cancellable = true)
    public void useInject(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);
        if(itemStack.isOf(Items.BRICK) || itemStack.isOf(Items.NETHER_BRICK)) {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), DreamerTweaks.ENTITY_BRICK_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!world.isClient) {
                if(itemStack.isOf(Items.BRICK)) {
                    BrickEntity brickEntity = new BrickEntity(world,user);
                    brickEntity.setItem(itemStack);
                    brickEntity.setVelocity(user,user.getPitch(),user.getYaw(),0.0F,1.5F,1.0F);
                    brickEntity.setOwner(user);
                    world.spawnEntity(brickEntity);
                } else {
                    NetherBrickEntity netherBrickEntity = new NetherBrickEntity(world,user);
                    netherBrickEntity.setItem(itemStack);
                    netherBrickEntity.setVelocity(user,user.getPitch(),user.getYaw(),0.0F,1.5F,1.0F);
                    netherBrickEntity.setOwner(user);
                    world.spawnEntity(netherBrickEntity);
                }
            }

            user.incrementStat(Stats.USED.getOrCreateStat(itemStack.getItem()));
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }

            user.getItemCooldownManager().set(itemStack.getItem(), 20);

            cir.setReturnValue(TypedActionResult.success(itemStack, world.isClient()));
        }

        if(itemStack.isOf(Items.GLOW_BERRIES)) {
            user.setCurrentHand(hand);
            cir.setReturnValue(TypedActionResult.consume(itemStack));
        }
    }

    @Inject(at = @At("HEAD"), method = "finishUsing")
    public void finishUsingInject(ItemStack stack,World world,LivingEntity user,CallbackInfoReturnable<ItemStack> cir) {
        if(stack.isOf(Items.GLOW_BERRIES)) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 100));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100));
        }
    }

    @Inject(at = @At("HEAD"), method = "getMaxCount", cancellable = true)
    public void getMaxCountInject(CallbackInfoReturnable<Integer> cir) {
        if(getDefaultStack().getItem() instanceof PotionItem) {
            cir.setReturnValue(4);
        }
    }
}
