package com.dyonovan.repairchests2.blocks.entity;

import com.dyonovan.repairchests2.blocks.RCBlocks;
import com.dyonovan.repairchests2.blocks.RCChestTypes;
import com.dyonovan.repairchests2.inventory.RCMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class UltimateChestBlockEntity extends GenericChestBlockEntity {

    public UltimateChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RCBlockEntityTypes.ULTIMATE.get(), blockPos, blockState, RCChestTypes.ULTIMATE, RCBlocks.ULTIMATE_CHEST::get);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return RCMenu.createUltimateContainer(i, inventory, this);
    }
}
