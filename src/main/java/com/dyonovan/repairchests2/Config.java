package com.dyonovan.repairchests2;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = RepairChests2.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.IntValue BASIC_REPAIR_TIME = BUILDER
            .comment("How many seconds to repair 1 point of damage")
            .defineInRange("basicRepairTime", 10, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue ADVANCED_REPAIR_TIME = BUILDER
            .comment("How many seconds to repair 1 point of damage")
            .defineInRange("advancedRepairTime", 5, 1, Integer.MAX_VALUE);

    private static final ModConfigSpec.IntValue ULTIMATE_REPAIR_TIME = BUILDER
            .comment("How many seconds to repair 1 point of damage")
            .defineInRange("ultimateRepairTime", 1, 1, Integer.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static int basicRepairTime;
    public static int advancedRepairTime;
    public static int ultimateRepairTime;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        basicRepairTime = BASIC_REPAIR_TIME.get();
        advancedRepairTime = ADVANCED_REPAIR_TIME.get();
        ultimateRepairTime = ULTIMATE_REPAIR_TIME.get();
    }
}
