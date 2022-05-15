package net.dreamer.dr123tweaks.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class ExtendBlocks extends Blocks {

    public static Boolean never(BlockState state,BlockView world,BlockPos pos,EntityType<?> type) {
        return false;
    }

}
