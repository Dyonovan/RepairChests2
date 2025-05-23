package com.dyonovan.repairchests2.blocks;

import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.Util;
import com.dyonovan.repairchests2.blocks.entity.BasicChestBlockEntity;
import com.dyonovan.repairchests2.blocks.entity.GenericChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

public enum RCChestTypes implements StringRepresentable {
    BASIC(1, 1, 184, 184, RepairChests2.prefix("textures/gui/basic_container.png"), 256, 256);

    private final String name;
    public final int size;
    public final int rowLength;
    public final int xSize;
    public final int ySize;
    public final ResourceLocation guiTexture;
    public final int textureXSize;
    public final int textureYSize;

    RCChestTypes(int size, int rowLength, int xSize, int ySize, ResourceLocation guiTexture, int textureXSize, int textureYSize) {
        this(null, size, rowLength, xSize, ySize, guiTexture, textureXSize, textureYSize);
    }

    RCChestTypes(@Nullable String name, int size, int rowLength, int xSize, int ySize, ResourceLocation guiTexture, int textureXSize, int textureYSize) {
        this.name = name == null ? Util.toEnglishName(this.name()) : name;
        this.size = size;
        this.rowLength = rowLength;
        this.xSize = xSize;
        this.ySize = ySize;
        this.guiTexture = guiTexture;
        this.textureXSize = textureXSize;
        this.textureYSize = textureYSize;
    }

    public String getId() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public String getEnglishName() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.getEnglishName();
    }

    public int getRowCount() {
        return this.size / this.rowLength;
    }

    public static List<Block> get(RCChestTypes type) {
        return switch (type) {
            case BASIC -> List.of(RCBlocks.BASIC_CHEST.get());
            default -> List.of(Blocks.CHEST);
        };
    }

    @Nullable
    public GenericChestBlockEntity makeEntity(BlockPos blockPos, BlockState blockState) {
        return switch (this) {
            case BASIC -> new BasicChestBlockEntity(blockPos, blockState);
            default -> null;
        };
    }
}
