package com.dyonovan.repairchests2.blocks.entity;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.blocks.RCBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RCBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, RepairChests2.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<BasicChestBlockEntity>> BASIC =
            BLOCK_ENTITIES.register("basic_chest", () -> new BlockEntityType<>(BasicChestBlockEntity::new, RCBlocks.BASIC_CHEST.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AdvancedChestBlockEntity>> ADVANCED =
            BLOCK_ENTITIES.register("advanced_chest", () -> new BlockEntityType<>(AdvancedChestBlockEntity::new, RCBlocks.ADVANCED_CHEST.get()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<UltimateChestBlockEntity>> ULTIMATE =
            BLOCK_ENTITIES.register("ultimate_chest", () -> new BlockEntityType<>(UltimateChestBlockEntity::new, RCBlocks.ULTIMATE_CHEST.get()));
}
