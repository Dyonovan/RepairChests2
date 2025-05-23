package com.dyonovan.repairchests2.blocks.entity;

import com.dyonovan.repairchests2.blocks.RCBlocks;
import com.dyonovan.repairchests2.blocks.RCChestTypes;
import com.dyonovan.repairchests2.inventory.RCMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class AdvancedChestBlockEntity extends GenericChestBlockEntity {

    public AdvancedChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RCBlockEntityTypes.ADVANCED.get(), blockPos, blockState, RCChestTypes.ADVANCED, RCBlocks.ADVANCED_CHEST::get);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return RCMenu.createAdvancedContainer(i, inventory, this);
    }
}
