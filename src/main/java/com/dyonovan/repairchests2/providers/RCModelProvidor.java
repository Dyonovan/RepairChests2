package com.dyonovan.repairchests2.providers;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.blocks.RCBlocks;
import com.dyonovan.repairchests2.client.renderer.RCSpecialRenderer;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RCModelProvidor extends ModelProvider {

    public RCModelProvidor(PackOutput packOutput) {
        super(packOutput, RepairChests2.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.createChest(blockModels, itemModels, RCBlocks.BASIC_CHEST.get(), RepairChests2.prefix("block/basic_break"), RCSpecialRenderer.BASIC_CHEST_TEXTURE);
    }

    public void createChest(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block chestBlock, ResourceLocation particleTexture, ResourceLocation texture) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.multiVariant(chestBlock, Variant.variant().with(VariantProperties.MODEL, ModelTemplates.PARTICLE_ONLY.create(chestBlock, TextureMapping.particle(particleTexture), blockModels.modelOutput))));
        Item chestItem = chestBlock.asItem();
        ResourceLocation resourceLocation = ModelTemplates.CHEST_INVENTORY.create(chestItem, TextureMapping.particle(particleTexture), blockModels.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(resourceLocation, new RCSpecialRenderer.Unbaked(texture));
        itemModels.itemModelOutput.accept(chestItem, unbaked);
    }
}
