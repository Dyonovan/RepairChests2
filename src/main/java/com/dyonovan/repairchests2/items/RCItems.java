package com.dyonovan.repairchests2.items;

import com.dyonovan.repairchests2.RepairChests2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

public class RCItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RepairChests2.MODID);

    public static <T extends Item> DeferredItem<T> register(String name, Function<Item.Properties, T> item, Supplier<Item.Properties> properties) {
        return ITEMS.register(name, () -> item.apply(properties.get().setId(ResourceKey.create(Registries.ITEM, RepairChests2.prefix(name)))));
    }

    public static <T extends Item> void register(String name, Function<Item.Properties, T> item, Supplier<Item.Properties> properties, ResourceKey<Block> blockResourceKey) {
        ITEMS.register(name, () -> item.apply(properties.get().setId(ResourceKey.create(Registries.ITEM, blockResourceKey.location())).useBlockDescriptionPrefix()));
    }
}
