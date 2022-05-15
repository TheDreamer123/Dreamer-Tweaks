package net.dreamer.dr123tweaks.item;

import java.util.function.Supplier;

import com.terraformersmc.terraform.boat.impl.item.TerraformBoatItem;
import net.minecraft.block.DispenserBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.impl.item.TerraformBoatDispenserBehavior;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public final class CustomBoatItem {
    private static final ItemGroup DEFAULT_ITEM_GROUP = ItemGroup.TRANSPORTATION;

    private CustomBoatItem() {
    }

    public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier) {
        return registerBoatItem(id, boatSupplier, DEFAULT_ITEM_GROUP);
    }

    public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier, ItemGroup group) {
        return registerBoatItem(id, boatSupplier, new Item.Settings().maxCount(1).group(group));
    }

    public static Item registerBoatItem(Identifier id, Supplier<TerraformBoatType> boatSupplier, Item.Settings settings) {
        Item item = new TerraformBoatItem(boatSupplier, settings);
        Registry.register(Registry.ITEM, id, item);

        registerBoatDispenserBehavior(item, boatSupplier);
        return item;
    }

    public static void registerBoatDispenserBehavior(ItemConvertible item, Supplier<TerraformBoatType> boatSupplier) {
        DispenserBlock.registerBehavior(item, new TerraformBoatDispenserBehavior(boatSupplier));
    }
}