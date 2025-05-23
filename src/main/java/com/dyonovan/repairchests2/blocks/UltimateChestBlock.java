package com.dyonovan.repairchests2.blocks;

import com.dyonovan.repairchests2.blocks.entity.RCBlockEntityTypes;
import com.dyonovan.repairchests2.blocks.entity.UltimateChestBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class UltimateChestBlock extends GenericChestBlock {

    public static final MapCodec<UltimateChestBlock> CODEC = simpleCodec(UltimateChestBlock::new);

    public UltimateChestBlock(Properties properties) {
        super(properties, RCBlockEntityTypes.ULTIMATE::get, RCChestTypes.ULTIMATE);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new UltimateChestBlockEntity(blockPos, blockState);
    }
}
