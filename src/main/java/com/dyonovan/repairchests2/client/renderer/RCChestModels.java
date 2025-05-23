package com.dyonovan.repairchests2.client.renderer;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.blocks.RCChestTypes;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RCChestModels {

    public static final Material BASIC_CHEST_LOCATION = chestMaterial(false, "model/basic_chest");
    public static final Material ADVANCED_CHEST_LOCATION = chestMaterial(false, "model/advanced_chest");
    public static final Material ULTIMATE_CHEST_LOCATION = chestMaterial(false, "model/ultimate_chest");
    public static final Material VANILLA_CHEST_LOCATION = chestMaterial(true, "normal");

    public static Material chooseChestMaterial(RCChestTypes type) {
        return getMaterial(type, BASIC_CHEST_LOCATION, ADVANCED_CHEST_LOCATION, ULTIMATE_CHEST_LOCATION, VANILLA_CHEST_LOCATION);
    }

    @NotNull
    private static Material getMaterial(RCChestTypes type, Material basicChestMaterial, Material advancedChestMaterial, Material ultimateChestMaterial, Material vanillaChestMaterial) {
        return switch (type) {
            case BASIC -> basicChestMaterial;
            case ADVANCED -> advancedChestMaterial;
            case ULTIMATE -> ultimateChestMaterial;
            default -> vanillaChestMaterial;
        };
    }

    private static Material chestMaterial(boolean vanillaChest, String chestName) {
        if (vanillaChest) {
            return new Material(Sheets.CHEST_SHEET, ResourceLocation.withDefaultNamespace("entity/chest/" + chestName));
        } else {
            return new Material(Sheets.CHEST_SHEET, RepairChests2.prefix(chestName));
        }
    }
}
