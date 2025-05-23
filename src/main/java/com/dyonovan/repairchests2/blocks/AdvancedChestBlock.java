package com.dyonovan.repairchests2.blocks;

import com.dyonovan.repairchests2.blocks.entity.AdvancedChestBlockEntity;
import com.dyonovan.repairchests2.blocks.entity.RCBlockEntityTypes;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class AdvancedChestBlock extends GenericChestBlock {

    public static final MapCodec<AdvancedChestBlock> CODEC = simpleCodec(AdvancedChestBlock::new);

    public AdvancedChestBlock(Properties properties) {
        super(properties, RCBlockEntityTypes.ADVANCED::get, RCChestTypes.ADVANCED);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AdvancedChestBlockEntity(blockPos, blockState);
    }
}
