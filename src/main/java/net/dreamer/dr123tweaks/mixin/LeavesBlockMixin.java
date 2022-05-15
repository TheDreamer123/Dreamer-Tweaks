package net.dreamer.dr123tweaks.mixin;

import net.dreamer.dr123tweaks.DreamerTweaks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block {
    @Shadow @Final public static IntProperty DISTANCE;
    @Shadow @Final public static BooleanProperty PERSISTENT;

    public LeavesBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state,World world,BlockPos pos,PlayerEntity player,Hand hand,BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if(state.isOf(Blocks.FLOWERING_AZALEA_LEAVES)) {
            if(stack.isOf(Items.SHEARS)) {
                if (stack.isOf(Items.SHEARS)) {
                    if (!world.isClient) {
                        world.playSound(null, pos, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        world.setBlockState(pos,Blocks.AZALEA_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, state.get(DISTANCE)).with(LeavesBlock.PERSISTENT, state.get(PERSISTENT)), 11);
                        dropStack(world, pos, new ItemStack(DreamerTweaks.AZALEA_BLOOM));
                        stack.damage(1, player, (playerx) -> playerx.sendToolBreakStatus(hand));
                        world.emitGameEvent(player, GameEvent.SHEAR, pos);
                        player.incrementStat(Stats.USED.getOrCreateStat(Items.SHEARS));
                    }

                    return ActionResult.success(world.isClient);
                }
            }
        } else if(state.isOf(Blocks.AZALEA_LEAVES)) {
            if(stack.isOf(DreamerTweaks.AZALEA_BLOOM.asItem())) {
                if (!world.isClient) {
                    world.playSound(null, pos, SoundEvents.BLOCK_AZALEA_LEAVES_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.setBlockState(pos,Blocks.FLOWERING_AZALEA_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, state.get(DISTANCE)).with(LeavesBlock.PERSISTENT, state.get(PERSISTENT)), 11);
                    if(!player.getAbilities().creativeMode) {
                        stack.decrement(1);
                    }
                }
                return ActionResult.success(world.isClient);
            }
        }
        return super.onUse(state,world,pos,player,hand,hit);
    }
}
