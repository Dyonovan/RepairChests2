package com.dyonovan.repairchests2.client.renderer;

import com.dyonovan.repairchests2.blocks.GenericChestBlock;
import com.dyonovan.repairchests2.blocks.RCChestTypes;
import com.dyonovan.repairchests2.blocks.entity.GenericChestBlockEntity;
import com.dyonovan.repairchests2.client.RCClientRegistration;
import com.dyonovan.repairchests2.client.model.ModelItem;
import com.dyonovan.repairchests2.client.model.RCChestModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class RCChestRenderer<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {

    private final RCChestModel model;
    private final BlockEntityRenderDispatcher renderer;

    private static final List<ModelItem> MODEL_ITEMS = Arrays.asList(
            new ModelItem(new Vector3f(0.3F, 0.45F, 0.3F), 3.0F),
            new ModelItem(new Vector3f(0.7F, 0.45F, 0.3F), 3.0F),
            new ModelItem(new Vector3f(0.3F, 0.45F, 0.7F), 3.0F),
            new ModelItem(new Vector3f(0.7F, 0.45F, 0.7F), 3.0F),
            new ModelItem(new Vector3f(0.3F, 0.1F, 0.3F), 3.0F),
            new ModelItem(new Vector3f(0.7F, 0.1F, 0.3F), 3.0F),
            new ModelItem(new Vector3f(0.3F, 0.1F, 0.7F), 3.0F),
            new ModelItem(new Vector3f(0.7F, 0.1F, 0.7F), 3.0F),
            new ModelItem(new Vector3f(0.5F, 0.32F, 0.5F), 3.0F)
    );

    public RCChestRenderer(BlockEntityRendererProvider.Context context) {
        this.renderer = context.getBlockEntityRenderDispatcher();
        this.model = new RCChestModel(context.bakeLayer(RCClientRegistration.CHEST));
    }

    @Override
    public void render(T blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        GenericChestBlockEntity chestBlockEntity = (GenericChestBlockEntity) blockEntity;

        Level level = chestBlockEntity.getLevel();
        boolean useTileEntityBlockState = level != null;

        BlockState blockState = useTileEntityBlockState ? chestBlockEntity.getBlockState() : chestBlockEntity.getBlockToUse().defaultBlockState().setValue(GenericChestBlock.FACING, Direction.SOUTH);
        Block block = blockState.getBlock();
        RCChestTypes chestType = RCChestTypes.BASIC;
        RCChestTypes actualType = GenericChestBlock.getTypeFromBlock(block);

        if (actualType != null) {
            chestType = actualType;
        }

        if (block instanceof GenericChestBlock) {
            poseStack.pushPose();

            float f = blockState.getValue(GenericChestBlock.FACING).toYRot();

            poseStack.translate(0.5D, 0.5D, 0.5D);
            poseStack.mulPose(Axis.YP.rotationDegrees(-f));
            poseStack.translate(-0.5D, -0.5D, -0.5D);

            float openness = chestBlockEntity.getOpenNess(partialTick);
            openness = 1.0F - openness;
            openness = 1.0F - openness * openness * openness;

            Material material = RCChestModels.chooseChestMaterial(chestType);
            VertexConsumer vertexConsumer = material.buffer(multiBufferSource, RenderType::entityCutout);
            this.render(poseStack, vertexConsumer, this.model, openness, packedLight, packedOverlay);

            poseStack.popPose();
        }
    }

    private void render(PoseStack poseStack, VertexConsumer buffer, RCChestModel model, float openness, int packedLight, int packedOverlay) {
        model.setupAnim(openness);
        model.renderToBuffer(poseStack, buffer, packedLight, packedOverlay);
    }

    public static void renderItem(PoseStack matrices, MultiBufferSource buffer, ItemStack item, ModelItem modelItem, float rotation, int light) {
        if (item.isEmpty()) return;

        matrices.pushPose();
        Vector3f center = modelItem.getCenter();
        matrices.translate(center.x(), center.y(), center.z());

        matrices.mulPose(Axis.YP.rotationDegrees(rotation));

        // scale
        float scale = modelItem.getSizeScaled();
        matrices.scale(scale, scale, scale);

        // render the actual item
        Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY, matrices, buffer, null, 0);

        matrices.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return AABB.encapsulatingFullBlocks(pos.offset(-1, 0, -1), pos.offset(1, 1, 1));
    }
}
