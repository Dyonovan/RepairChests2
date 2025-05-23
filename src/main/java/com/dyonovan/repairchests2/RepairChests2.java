package com.dyonovan.repairchests2;

import com.dyonovan.repairchests2.blocks.RCBlocks;
import com.dyonovan.repairchests2.blocks.entity.RCBlockEntityTypes;
import com.dyonovan.repairchests2.inventory.RCMenuTypes;
import com.dyonovan.repairchests2.items.RCItems;
import com.dyonovan.repairchests2.providers.RCCreativeTab;
import com.dyonovan.repairchests2.providers.RCModelProvidor;
import com.dyonovan.repairchests2.providers.RCSpriteSourceProvidor;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

@Mod(RepairChests2.MODID)
public class RepairChests2
{
    public static final String MODID = "repairchests2";
    private static final Logger LOGGER = LogUtils.getLogger();

    public RepairChests2(IEventBus modEventBus, ModContainer modContainer)
    {
        modEventBus.addListener(this::gatherData);

        RCBlocks.BLOCKS.register(modEventBus);
        RCItems.ITEMS.register(modEventBus);
        RCBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
        RCMenuTypes.CONTAINERS.register(modEventBus);
        RCCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public void gatherData(GatherDataEvent.Client event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(true, new RCSpriteSourceProvidor(packOutput, lookupProvider));
        gen.addProvider(true, new RCModelProvidor(packOutput));
    }

    public static ResourceLocation prefix(String name) {
        return ResourceLocation.fromNamespaceAndPath(MODID, name.toLowerCase(Locale.ROOT));
    }
}
