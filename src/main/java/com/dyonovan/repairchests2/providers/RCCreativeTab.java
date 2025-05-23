package com.dyonovan.repairchests2.providers;

import com.dyonovan.repairchests2.blocks.RCBlocks;
import com.dyonovan.repairchests2.items.RCItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.dyonovan.repairchests2.RepairChests2.MODID;

public class RCCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> REPAIR_CHESTS_TAB = CREATIVE_MODE_TABS.register("repairchests",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.repairchests2"))
                    .icon(() -> new ItemStack(RCBlocks.BASIC_CHEST.get()))
                    .displayItems((parameters, output) -> {
                        for (final Item item : RCItems.ITEMS.getEntries().stream().map(DeferredHolder::value).toList())
                            output.accept(item);
                    }).build());
}
