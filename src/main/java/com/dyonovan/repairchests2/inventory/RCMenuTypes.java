package com.dyonovan.repairchests2.inventory;

import com.dyonovan.repairchests2.RepairChests2;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RCMenuTypes {

    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(Registries.MENU, RepairChests2.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<RCMenu>> BASIC_CHEST = CONTAINERS.register("basic_chest",
            () -> new MenuType<>(RCMenu::createBasicContainer, FeatureFlags.REGISTRY.allFlags()));
    public static final DeferredHolder<MenuType<?>, MenuType<RCMenu>> ADVANCED_CHEST = CONTAINERS.register("advanced_chest",
            () -> new MenuType<>(RCMenu::createAdvancedContainer, FeatureFlags.REGISTRY.allFlags()));
    public static final DeferredHolder<MenuType<?>, MenuType<RCMenu>> ULTIMATE_CHEST = CONTAINERS.register("ultimate_chest",
            () -> new MenuType<>(RCMenu::createUltimateContainer, FeatureFlags.REGISTRY.allFlags()));
}
