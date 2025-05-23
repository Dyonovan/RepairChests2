package com.dyonovan.repairchests2.blocks.entity;

import com.dyonovan.repairchests2.Config;
import com.dyonovan.repairchests2.RepairChests2;
import com.dyonovan.repairchests2.blocks.GenericChestBlock;
import com.dyonovan.repairchests2.blocks.RCChestTypes;
import com.dyonovan.repairchests2.inventory.RCMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public abstract class GenericChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity {

    private NonNullList<ItemStack> items;

    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, BlockPos blockPos, BlockState blockState) {
            GenericChestBlockEntity.playSound(level, blockPos, blockState, SoundEvents.CHEST_OPEN);
        }

        @Override
        protected void onClose(Level level, BlockPos blockPos, BlockState blockState) {
            GenericChestBlockEntity.playSound(level, blockPos, blockState, SoundEvents.CHEST_CLOSE);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos blockPos, BlockState blockState, int i, int i1) {
            GenericChestBlockEntity.this.signalOpenCount(level, blockPos, blockState, i, i1);
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (!(player.containerMenu instanceof RCMenu)) {
                return false;
            } else {
                Container container = ((RCMenu) player.containerMenu).getContainer();
                return container instanceof GenericChestBlockEntity || container instanceof CompoundContainer &&
                        ((CompoundContainer) container).contains(GenericChestBlockEntity.this);
            }
        }
    };

    private final ChestLidController chestLidController = new ChestLidController();

    private final RCChestTypes chestType;
    private final Supplier<Block> blockToUse;

    private int tickNum;
    private final int tickTime;

    protected GenericChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState,
                                      RCChestTypes chestType, Supplier<Block> blockToUse) {
        super(blockEntityType, blockPos, blockState);

        this.items = NonNullList.withSize(chestType.size, ItemStack.EMPTY);
        this.chestType = chestType;
        this.blockToUse = blockToUse;

        this.tickTime = switch(chestType) {
            case BASIC -> Config.basicRepairTime * 20;
            case ADVANCED -> Config.advancedRepairTime * 20;
            case ULTIMATE -> Config.ultimateRepairTime * 20;
        };
    }

    @Override
    public int getContainerSize() {
        return this.getItems().size();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable(RepairChests2.MODID + ".container." + this.chestType.getId() + "_chest");
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.loadAdditional(compoundTag, provider);

        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);

        if (!this.tryLoadLootTable(compoundTag)) {
            ContainerHelper.loadAllItems(compoundTag, this.items, provider);
        }
    }

    @Override
    public void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        super.saveAdditional(compoundTag, provider);

        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.items, provider);
        }
    }

    public static void lidAnimateTick(Level level, BlockPos blockPos, BlockState blockState, GenericChestBlockEntity chestBlockEntity) {
        chestBlockEntity.chestLidController.tickLid();
    }

    static void playSound(Level level, BlockPos blockPos, BlockState blockState, SoundEvent soundEvent) {
        double d0 = (double) blockPos.getX() + 0.5;
        double d1 = (double) blockPos.getY() + 0.5;
        double d2 = (double) blockPos.getZ() + 0.5;

        level.playSound(null, d0, d1, d2, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.chestLidController.shouldBeOpen(type > 0);
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    @Override
    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = NonNullList.withSize(this.getChestType().size, ItemStack.EMPTY);

        for (int i = 0; i < itemsIn.size(); i++) {
            if (i < this.items.size()) {
                this.getItems().set(i, itemsIn.get(i));
            }
        }
    }

    @Override
    public float getOpenNess(float partialTicks) {
        return this.chestLidController.getOpenness(partialTicks);
    }

    public static int GetOpenCount(BlockGetter blockGetter, BlockPos blockPos) {
        BlockState blockState = blockGetter.getBlockState(blockPos);

        if (blockState.hasBlockEntity()) {
            BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);

            if (blockEntity instanceof GenericChestBlockEntity) {
                return ((GenericChestBlockEntity) blockEntity).openersCounter.getOpenerCount();
            }
        }
        return 0;
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    protected void signalOpenCount(Level level, BlockPos blockPos, BlockState blockState, int previousCount, int newCount) {
        Block block = blockState.getBlock();
        level.blockEvent(blockPos, block, 1, newCount);
    }

    public void removeAdornments() {
    }

    public RCChestTypes getChestType() {
        RCChestTypes type = RCChestTypes.BASIC;

        if (this.hasLevel()) {
            RCChestTypes typeFromBlock = GenericChestBlock.getTypeFromBlock(this.getBlockState().getBlock());

            if (typeFromBlock != null) {
                type = typeFromBlock;
            }
        }

        return type;
    }

    public Block getBlockToUse() {
        return this.blockToUse.get();
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, GenericChestBlockEntity blockEntity) {
        ++blockEntity.tickNum;
        if (blockEntity.tickNum >= blockEntity.tickTime) {
            for (int c = 0; c < blockEntity.getContainerSize(); c++) {
                ItemStack stack = blockEntity.getItem(c);
                if (!stack.isEmpty() && stack.isDamageableItem() && stack.getDamageValue() > 0) {
                    stack.setDamageValue(stack.getDamageValue() - 1);
                }
            }
            blockEntity.tickNum = 0;
        }
    }
}
