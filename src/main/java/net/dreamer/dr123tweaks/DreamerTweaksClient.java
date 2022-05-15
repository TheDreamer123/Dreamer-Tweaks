package net.dreamer.dr123tweaks;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class DreamerTweaksClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(DreamerTweaks.AZALEA_BLOOM, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(DreamerTweaks.AZALEA_DOOR, RenderLayer.getTranslucent());

        BlockRenderLayerMap.INSTANCE.putBlock(DreamerTweaks.AZALEA_TRAPDOOR, RenderLayer.getTranslucent());

        TerraformBoatClientHelper.registerModelLayer(new Identifier(DreamerTweaks.MOD_ID, "azalea"));

        registerSignSprite();

        EntityRendererRegistry.INSTANCE.register(DreamerTweaks.BRICK, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.INSTANCE.register(DreamerTweaks.NETHER_BRICK, FlyingItemEntityRenderer::new);
    }

    private void registerSignSprite() {
        Identifier id = new Identifier(DreamerTweaks.MOD_ID, "entity/signs/" + "azalea");
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, id));
    }
}
