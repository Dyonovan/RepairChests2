package com.dyonovan.repairchests2.client;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.blocks.RCBlocks;
import com.dyonovan.repairchests2.blocks.entity.RCBlockEntityTypes;
import com.dyonovan.repairchests2.client.model.RCChestModel;
import com.dyonovan.repairchests2.client.renderer.RCChestRenderer;
import com.dyonovan.repairchests2.client.renderer.RCSpecialRenderer;
import com.dyonovan.repairchests2.client.screen.IronChestScreen;
import com.dyonovan.repairchests2.inventory.RCMenuTypes;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialBlockModelRendererEvent;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;

@EventBusSubscriber(modid = RepairChests2.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class RCClientRegistration {

    public static final ModelLayerLocation CHEST = new ModelLayerLocation(RepairChests2.prefix("chest"), "main");

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CHEST, RCChestModel::createLayerDefinition);
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(RCMenuTypes.BASIC_CHEST.get(), IronChestScreen::new);
        event.register(RCMenuTypes.ADVANCED_CHEST.get(), IronChestScreen::new);
        event.register(RCMenuTypes.ULTIMATE_CHEST.get(), IronChestScreen::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RCBlockEntityTypes.BASIC.get(), RCChestRenderer::new);
        event.registerBlockEntityRenderer(RCBlockEntityTypes.ADVANCED.get(), RCChestRenderer::new);
        event.registerBlockEntityRenderer(RCBlockEntityTypes.ULTIMATE.get(), RCChestRenderer::new);
    }

    @SubscribeEvent
    public static void registerSpecialRenderers(RegisterSpecialModelRendererEvent event) {
        event.register(RepairChests2.prefix("repair_chest"), RCSpecialRenderer.Unbaked.MAP_CODEC);
    }

    @SubscribeEvent
    public static void registerSpecialBlockRenderers(RegisterSpecialBlockModelRendererEvent event) {
        event.register(RCBlocks.BASIC_CHEST.get(), new RCSpecialRenderer.Unbaked(RCSpecialRenderer.BASIC_CHEST_TEXTURE));
        event.register(RCBlocks.ADVANCED_CHEST.get(), new RCSpecialRenderer.Unbaked(RCSpecialRenderer.ADVANCED_CHEST_TEXTURE));
        event.register(RCBlocks.ULTIMATE_CHEST.get(), new RCSpecialRenderer.Unbaked(RCSpecialRenderer.ULTIMATE_CHEST_TEXTURE));
    }
}
