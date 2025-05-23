package com.dyonovan.repairchests2.items;

import com.dyonovan.repairchests2.blocks.RCChestTypes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class RCBlockItem extends BlockItem {

    protected RCChestTypes type;

    public RCBlockItem(Block block, Properties properties, RCChestTypes type) {
        super(block, properties);

        this.type = type;
    }

    public RCChestTypes getType() {
        return this.type;
    }
}
