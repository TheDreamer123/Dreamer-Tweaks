package net.dreamer.dr123tweaks;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
import net.dreamer.dr123tweaks.block.*;
import net.dreamer.dr123tweaks.entity.BrickEntity;
import net.dreamer.dr123tweaks.entity.NetherBrickEntity;
import net.dreamer.dr123tweaks.item.CustomBoatItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class DreamerTweaks implements ModInitializer {
	public static final Logger LOGGER = LogManager.getLogger("Dreamer Tweaks");
	public static String MOD_ID = "dr123tweaks";



	private void strip() {
		AxeItem.STRIPPED_BLOCKS = new HashMap<>(AxeItem.STRIPPED_BLOCKS);
		AxeItem.STRIPPED_BLOCKS.put(DreamerTweaks.AZALEA_LOG, DreamerTweaks.STRIPPED_AZALEA_LOG);
		AxeItem.STRIPPED_BLOCKS.put(DreamerTweaks.AZALEA_WOOD, DreamerTweaks.STRIPPED_AZALEA_WOOD);
	}
	private void pathable() {
		ShovelItem.PATH_STATES = new HashMap<>(ShovelItem.PATH_STATES);
		ShovelItem.PATH_STATES.put(Blocks.SOUL_SAND, Blocks.SOUL_SOIL.getDefaultState());
	}


	private static PillarBlock LogBlock(MapColor sideMapColor) {
		return new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, (state) -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.PINK : sideMapColor).strength(2.0F).sounds(BlockSoundGroup.WOOD));
	}

	public static Item boat(String path, String boatPath) {
		Identifier id = new Identifier(MOD_ID, path);
		Identifier boatId = new Identifier(MOD_ID, boatPath);

		Item item = CustomBoatItem.registerBoatItem(id, () -> TerraformBoatTypeRegistry.INSTANCE.get(boatId));

		TerraformBoatType boatType = new TerraformBoatType.Builder().item(item).build();
		Registry.register(TerraformBoatTypeRegistry.INSTANCE, boatId, boatType);

		return item;
	}


	public static final EntityType<BrickEntity> BRICK = Registry.register(Registry.ENTITY_TYPE,new Identifier(MOD_ID, "brick"),FabricEntityTypeBuilder.<BrickEntity>create(SpawnGroup.MISC,BrickEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());
	public static final EntityType<NetherBrickEntity> NETHER_BRICK = Registry.register(Registry.ENTITY_TYPE,new Identifier(MOD_ID, "nether_brick"),FabricEntityTypeBuilder.<NetherBrickEntity>create(SpawnGroup.MISC,NetherBrickEntity::new).dimensions(EntityDimensions.fixed(0.25f, 0.25f)).build());


	public static final Identifier ENTITY_BRICK_THROW_ID = new Identifier(MOD_ID, "entity.brick.throw");
	public static SoundEvent ENTITY_BRICK_THROW = new SoundEvent(ENTITY_BRICK_THROW_ID);


	public static final Block AZALEA_BLOOM = new GlowLichenBlock(FabricBlockSettings.copy(Blocks.GLOW_LICHEN).luminance(GlowLichenBlock.getLuminanceSupplier(0)).sounds(BlockSoundGroup.AZALEA_LEAVES));

	public static final Item ROASTED_AZALEA_BLOOM = new Item(new Item.Settings().group(ItemGroup.FOOD).food(FoodComponents.COOKIE));


	public static final Block AZALEA_BUTTON = new ExtendWoodenButtonBlock(AbstractBlock.Settings.of(Material.DECORATION).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));

	public static final Item AZALEA_BOAT = boat("azalea_boat", "azalea");

	public static final Block AZALEA_DOOR = new ExtendDoorBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PINK).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque());

	public static final Block AZALEA_FENCE = new FenceBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PINK).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

	public static final Block AZALEA_FENCE_GATE = new FenceGateBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PINK).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

	public static final Block AZALEA_LOG = LogBlock(MapColor.BROWN);

	public static final Block AZALEA_PLANKS = new Block(AbstractBlock.Settings.of(Material.WOOD, MapColor.PINK).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

	public static final Block AZALEA_PRESSURE_PLATE = new ExtendPressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, AbstractBlock.Settings.of(Material.WOOD, MapColor.PINK).noCollision().strength(0.5F).sounds(BlockSoundGroup.WOOD));

	public static final Block AZALEA_SIGN = new TerraformSignBlock(new Identifier(MOD_ID, "entity/signs/azalea"), FabricBlockSettings.of(Material.WOOD).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD));
	public static final Block AZALEA_WALL_SIGN = new TerraformWallSignBlock(new Identifier(MOD_ID, "entity/signs/azalea"), FabricBlockSettings.of(Material.WOOD).noCollision().strength(1.0F).sounds(BlockSoundGroup.WOOD));
	public static final Item AZALEA_SIGN_ITEM = new SignItem(new Item.Settings().group(ItemGroup.DECORATIONS).maxCount(16), AZALEA_SIGN,  AZALEA_WALL_SIGN);

	public static final Block AZALEA_SLAB = new SlabBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.PINK).strength(2.0F, 3.0F).sounds(BlockSoundGroup.WOOD));

	public static final Block AZALEA_STAIRS = new ExtendStairsBlock(AZALEA_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(AZALEA_PLANKS));

	public static final Block AZALEA_TRAPDOOR = new ExtendTrapdoorBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.OAK_TAN).strength(3.0F).sounds(BlockSoundGroup.WOOD).nonOpaque().allowsSpawning(ExtendBlocks::never));

	public static final Block AZALEA_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD));

	public static final Block STRIPPED_AZALEA_LOG = LogBlock(MapColor.PINK);

	public static final Block STRIPPED_AZALEA_WOOD = new PillarBlock(AbstractBlock.Settings.of(Material.WOOD, MapColor.BROWN).strength(2.0F).sounds(BlockSoundGroup.WOOD));

	@Override
	public void onInitialize() {
		LOGGER.info("Dreamer tweaks initialized! Enjoy your survival!!!");



		strip();
		pathable();


		FlammableBlockRegistry.getDefaultInstance().add(AZALEA_FENCE, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(AZALEA_FENCE_GATE, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(AZALEA_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(AZALEA_PLANKS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(AZALEA_SLAB, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(AZALEA_STAIRS, 5, 20);
		FlammableBlockRegistry.getDefaultInstance().add(AZALEA_WOOD, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_AZALEA_LOG, 5, 5);
		FlammableBlockRegistry.getDefaultInstance().add(STRIPPED_AZALEA_WOOD, 5, 5);


		Registry.register(Registry.SOUND_EVENT, ENTITY_BRICK_THROW_ID, ENTITY_BRICK_THROW);


		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_bloom"), AZALEA_BLOOM);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_bloom"), new BlockItem(AZALEA_BLOOM, new Item.Settings().group(ItemGroup.DECORATIONS)));
		FuelRegistry.INSTANCE.add(AZALEA_BLOOM, 100);
		ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.put(AZALEA_BLOOM.asItem(), 0.5F);

		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "roasted_azalea_bloom"), ROASTED_AZALEA_BLOOM);


		FuelRegistry.INSTANCE.add(AZALEA_BOAT, 1200);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_button"), AZALEA_BUTTON);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_button"), new BlockItem(AZALEA_BUTTON, new Item.Settings().group(ItemGroup.REDSTONE)));
		FuelRegistry.INSTANCE.add(AZALEA_BUTTON, 100);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_door"), AZALEA_DOOR);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_door"), new BlockItem(AZALEA_DOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
		FuelRegistry.INSTANCE.add(AZALEA_DOOR, 200);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_fence"), AZALEA_FENCE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_fence"), new BlockItem(AZALEA_FENCE, new Item.Settings().group(ItemGroup.REDSTONE)));
		FuelRegistry.INSTANCE.add(AZALEA_FENCE, 300);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_fence_gate"), AZALEA_FENCE_GATE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_fence_gate"), new BlockItem(AZALEA_FENCE_GATE, new Item.Settings().group(ItemGroup.REDSTONE)));
		FuelRegistry.INSTANCE.add(AZALEA_FENCE_GATE, 300);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_log"), AZALEA_LOG);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_log"), new BlockItem(AZALEA_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_planks"), AZALEA_PLANKS);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_planks"), new BlockItem(AZALEA_PLANKS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_pressure_plate"), AZALEA_PRESSURE_PLATE);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_pressure_plate"), new BlockItem(AZALEA_PRESSURE_PLATE, new Item.Settings().group(ItemGroup.REDSTONE)));
		FuelRegistry.INSTANCE.add(AZALEA_PRESSURE_PLATE, 300);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_slab"), AZALEA_SLAB);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_slab"), new BlockItem(AZALEA_SLAB, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		FuelRegistry.INSTANCE.add(AZALEA_SLAB, 150);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_stairs"), AZALEA_STAIRS);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_stairs"), new BlockItem(AZALEA_STAIRS, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));
		FuelRegistry.INSTANCE.add(AZALEA_STAIRS, 300);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_trapdoor"), AZALEA_TRAPDOOR);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_trapdoor"), new BlockItem(AZALEA_TRAPDOOR, new Item.Settings().group(ItemGroup.REDSTONE)));
		FuelRegistry.INSTANCE.add(AZALEA_TRAPDOOR, 300);

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_wood"), AZALEA_WOOD);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_wood"), new BlockItem(AZALEA_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "stripped_azalea_log"), STRIPPED_AZALEA_LOG);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "stripped_azalea_log"), new BlockItem(STRIPPED_AZALEA_LOG, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "stripped_azalea_wood"), STRIPPED_AZALEA_WOOD);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "stripped_azalea_wood"), new BlockItem(STRIPPED_AZALEA_WOOD, new Item.Settings().group(ItemGroup.BUILDING_BLOCKS)));

		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_sign"), AZALEA_SIGN);
		Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "azalea_wall_sign"), AZALEA_WALL_SIGN);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "azalea_sign"), AZALEA_SIGN_ITEM);
		FuelRegistry.INSTANCE.add(AZALEA_SIGN_ITEM, 200);
	}
}
