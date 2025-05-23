package com.dyonovan.repairchests2.blocks.entity;

import com.dyonovan.repairchests2.blocks.RCBlocks;
import com.dyonovan.repairchests2.blocks.RCChestTypes;
import com.dyonovan.repairchests2.inventory.RCMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public class BasicChestBlockEntity extends GenericChestBlockEntity {

    public BasicChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(RCBlockEntityTypes.BASIC.get(), blockPos, blockState, RCChestTypes.BASIC, RCBlocks.BASIC_CHEST::get);
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return RCMenu.createBasicContainer(i, inventory, this);
    }
}
