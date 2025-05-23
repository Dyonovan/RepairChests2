package com.dyonovan.repairchests2.providers;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RCLootTableProvider extends LootTableProvider {

    public RCLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Collections.emptySet(), List.of(new SubProviderEntry(RCBlockLoot::new, LootContextParamSets.BLOCK)), registries);
    }
}
