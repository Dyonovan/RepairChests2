package com.dyonovan.repairchests2.blocks;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.items.RCBlockItem;
import com.dyonovan.repairchests2.items.RCItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.dyonovan.repairchests2.RepairChests2.MODID;

public class RCBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    static final BlockBehaviour.Properties properties = BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.0F).sound(SoundType.METAL);

    public static final DeferredBlock<BasicChestBlock> BASIC_CHEST = registerWithItem("basic_chest", BasicChestBlock::new,
            () -> properties, RCChestTypes.BASIC);
    public static final DeferredBlock<AdvancedChestBlock> ADVANCED_CHEST = registerWithItem("advanced_chest", AdvancedChestBlock::new,
            () -> properties, RCChestTypes.ADVANCED);
    public static final DeferredBlock<UltimateChestBlock> ULTIMATE_CHEST = registerWithItem("ultimate_chest", UltimateChestBlock::new,
            ()-> properties, RCChestTypes.ULTIMATE);

    public static <T extends Block> DeferredBlock<T> registerWithItem(String name, Function<BlockBehaviour.Properties, T> block,
                                                                      Supplier<BlockBehaviour.Properties> properties, RCChestTypes chestType) {
        ResourceKey<Block> blockResourceKey = ResourceKey.create(Registries.BLOCK, RepairChests2.prefix(name));
        DeferredBlock<T> ret = BLOCKS.register(name, () -> block.apply(properties.get().setId(blockResourceKey)));
        RCItems.register(name, itemProps -> new RCBlockItem(ret.get(), itemProps, chestType), Item.Properties::new, blockResourceKey);
        return ret;
    }
}
