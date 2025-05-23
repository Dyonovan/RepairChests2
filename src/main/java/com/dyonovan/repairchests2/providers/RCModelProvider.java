package com.dyonovan.repairchests2.providers;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.blocks.RCBlocks;
import com.dyonovan.repairchests2.client.renderer.RCSpecialRenderer;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.block.model.Variant;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class RCModelProvider extends ModelProvider {

    public RCModelProvider(PackOutput packOutput) {
        super(packOutput, RepairChests2.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.createChest(blockModels, itemModels, RCBlocks.BASIC_CHEST.get(), RepairChests2.prefix("block/basic_break"), RCSpecialRenderer.BASIC_CHEST_TEXTURE);
        this.createChest(blockModels, itemModels, RCBlocks.ADVANCED_CHEST.get(), RepairChests2.prefix("block/advanced_break"), RCSpecialRenderer.ADVANCED_CHEST_TEXTURE);
        this.createChest(blockModels, itemModels, RCBlocks.ULTIMATE_CHEST.get(), RepairChests2.prefix("block/ultimate_break"), RCSpecialRenderer.ULTIMATE_CHEST_TEXTURE);
    }

    public void createChest(BlockModelGenerators blockModels, ItemModelGenerators itemModels, Block chestBlock, ResourceLocation particleTexture, ResourceLocation texture) {
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(chestBlock, new MultiVariant(WeightedList.of(new Variant(ModelTemplates.PARTICLE_ONLY.create(chestBlock, TextureMapping.particle(particleTexture), blockModels.modelOutput))))));
        Item chestItem = chestBlock.asItem();
        ResourceLocation resourceLocation = ModelTemplates.CHEST_INVENTORY.create(chestItem, TextureMapping.particle(particleTexture), blockModels.modelOutput);
        ItemModel.Unbaked unbaked = ItemModelUtils.specialModel(resourceLocation, new RCSpecialRenderer.Unbaked(texture));
        itemModels.itemModelOutput.accept(chestItem, unbaked);
    }
}
