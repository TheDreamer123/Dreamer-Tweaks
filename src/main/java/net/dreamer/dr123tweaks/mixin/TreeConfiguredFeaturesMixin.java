package net.dreamer.dr123tweaks.mixin;

import net.dreamer.dr123tweaks.DreamerTweaks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.RandomSpreadFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.trunk.BendingTrunkPlacer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TreeConfiguredFeatures.class)
public class TreeConfiguredFeaturesMixin {
    @Mutable @Shadow @Final public static ConfiguredFeature<?, ?> AZALEA_TREE;

    static {
        AZALEA_TREE = ConfiguredFeatures.register(DreamerTweaks.MOD_ID + ":azalea_tree", Feature.TREE.configure((new TreeFeatureConfig.Builder(BlockStateProvider.of(DreamerTweaks.AZALEA_LOG), new BendingTrunkPlacer(4, 2, 0, 3, UniformIntProvider.create(1, 2)), new WeightedBlockStateProvider(DataPool.<BlockState>builder().add(Blocks.AZALEA_LEAVES.getDefaultState(), 3).add(Blocks.FLOWERING_AZALEA_LEAVES.getDefaultState(), 1)), new RandomSpreadFoliagePlacer(ConstantIntProvider.create(3), ConstantIntProvider.create(0), ConstantIntProvider.create(2), 50), new TwoLayersFeatureSize(1, 0, 1))).dirtProvider(BlockStateProvider.of(Blocks.ROOTED_DIRT)).forceDirt().build()));
    }
}
