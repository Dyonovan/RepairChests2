package com.dyonovan.repairchests2.client.renderer;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.client.RCClientRegistration;
import com.dyonovan.repairchests2.client.model.RCChestModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class RCSpecialRenderer implements NoDataSpecialModelRenderer {

    public static final ResourceLocation BASIC_CHEST_TEXTURE = RepairChests2.prefix("model/basic_chest");
    public static final ResourceLocation ADVANCED_CHEST_TEXTURE = RepairChests2.prefix("model/advanced_chest");
    public static final ResourceLocation ULTIMATE_CHEST_TEXTURE = RepairChests2.prefix("model/ultimate_chest");

    private final RCChestModel model;
    private final Material material;
    private final float openness;

    public RCSpecialRenderer(RCChestModel model, Material material, float openness) {
        this.model = model;
        this.material = material;
        this.openness = openness;
    }

    @Override
    public void render(ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource,
                       int packedLight, int packedOverlay, boolean hasFoilType) {

        VertexConsumer vertexconsumer = this.material.buffer(multiBufferSource, RenderType::entityCutout);
        this.model.setupAnim(this.openness);
        this.model.renderToBuffer(poseStack, vertexconsumer, packedLight, packedOverlay);
    }

    @OnlyIn(Dist.CLIENT)
    public record Unbaked(ResourceLocation texture, float openness) implements SpecialModelRenderer.Unbaked {

        public static final MapCodec<RCSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(
                unbakedInstance -> unbakedInstance.group(
                        ResourceLocation.CODEC.fieldOf("texture").forGetter(RCSpecialRenderer.Unbaked::texture),
                        Codec.FLOAT.optionalFieldOf("openness", 0.0F).forGetter(RCSpecialRenderer.Unbaked::openness)
                ).apply(unbakedInstance, RCSpecialRenderer.Unbaked::new)
        );

        public Unbaked(ResourceLocation resourceLocation) {
            this(resourceLocation, 0.0F);
        }

        @Override
        public MapCodec<RCSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public @Nullable SpecialModelRenderer<?> bake(EntityModelSet entityModelSet) {
            RCChestModel chestModel = new RCChestModel(entityModelSet.bakeLayer(RCClientRegistration.CHEST));
            Material material = new Material(Sheets.CHEST_SHEET, texture);
            return new RCSpecialRenderer(chestModel, material, this.openness);
        }
    }
}
