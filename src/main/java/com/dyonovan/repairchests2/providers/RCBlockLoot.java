package com.dyonovan.repairchests2.providers;

import com.dyonovan.repairchests2.blocks.RCBlocks;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.Set;

public class RCBlockLoot extends BlockLootSubProvider {

    private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

    protected RCBlockLoot(HolderLookup.Provider provider) {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        this.add(RCBlocks.BASIC_CHEST.get(), this::createNameableBlockEntityTable);
        this.add(RCBlocks.ADVANCED_CHEST.get(), this::createNameableBlockEntityTable);
        this.add(RCBlocks.ULTIMATE_CHEST.get(), this::createNameableBlockEntityTable);
    }

    @Override
    protected void add(Block block, LootTable.Builder table) {
        super.add(block, table);
        knownBlocks.add(block);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }
}
