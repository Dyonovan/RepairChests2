package com.dyonovan.repairchests2.blocks;

import com.dyonovan.repairchests2.blocks.entity.BasicChestBlockEntity;
import com.dyonovan.repairchests2.blocks.entity.RCBlockEntityTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class BasicChestBlock extends GenericChestBlock {

    public static final MapCodec<BasicChestBlock> CODEC = simpleCodec(BasicChestBlock::new);

    public BasicChestBlock(Properties properties) {
        super(properties, RCBlockEntityTypes.BASIC::get, RCChestTypes.BASIC);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new BasicChestBlockEntity(blockPos, blockState);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
