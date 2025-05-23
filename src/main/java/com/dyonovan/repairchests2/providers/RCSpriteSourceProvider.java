package com.dyonovan.repairchests2.providers;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.blocks.RCChestTypes;
import com.dyonovan.repairchests2.client.renderer.RCChestModels;
import net.minecraft.client.renderer.texture.atlas.sources.SingleFile;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.data.SpriteSourceProvider;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class RCSpriteSourceProvider extends SpriteSourceProvider {

    public RCSpriteSourceProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider, RepairChests2.MODID);
    }

    @Override
    protected void gather() {
        for (RCChestTypes type : RCChestTypes.values()) {
            atlas(CHESTS_ATLAS).addSource(new SingleFile(RCChestModels.chooseChestMaterial(type).texture(), Optional.empty()));
        }
    }
}
