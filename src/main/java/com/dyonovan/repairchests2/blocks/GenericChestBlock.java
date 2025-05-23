package com.dyonovan.repairchests2.blocks;

import com.dyonovan.repairchests2.blocks.entity.GenericChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.ChestBlock.isChestBlockedAt;

public abstract class GenericChestBlock extends BaseEntityBlock {

    public static final EnumProperty<Direction> FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape AABB = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
    private final RCChestTypes type;
    protected final Supplier<BlockEntityType<? extends GenericChestBlockEntity>> blockEntityType;

    public GenericChestBlock(BlockBehaviour.Properties properties, Supplier<BlockEntityType<? extends GenericChestBlockEntity>> blockEntityType, RCChestTypes type) {
        super(properties);

        this.type = type;
        this.blockEntityType = blockEntityType;

        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();

        return this.defaultBlockState().setValue(FACING, direction);
    }

    /*@Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof GenericChestBlockEntity) {
                ((GenericChestBlockEntity) blockEntity).removeAdornments();

                Containers.dropContents(level, pos, (GenericChestBlockEntity) blockEntity);
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.onRemove(state, level, pos, newState, movedByPiston);
    }*/

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            MenuProvider menuProvider = this.getMenuProvider(state, level, pos);
            if (menuProvider != null) {
                player.openMenu(menuProvider);
                player.awardStat(this.getOpenChestStat());
            }
            return InteractionResult.CONSUME;
        }
    }

    protected Stat<ResourceLocation> getOpenChestStat() {
        return Stats.CUSTOM.get(Stats.OPEN_CHEST);
    }

    public BlockEntityType<? extends GenericChestBlockEntity> blockEntityType() {
        return this.blockEntityType.get();
    }

    @Nullable
    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        if (isChestBlockedAt(level, pos))
            return null;

        if (level.getBlockEntity(pos) instanceof GenericChestBlockEntity genericChestBlockEntity)
            return genericChestBlockEntity;

        return null;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return createTickerHelper(blockEntityType, this.blockEntityType(), GenericChestBlockEntity::lidAnimateTick);
        } else {
            return createTickerHelper(blockEntityType, this.blockEntityType(), GenericChestBlockEntity::tick);
        }
        //return level.isClientSide ? createTickerHelper(blockEntityType, this.blockEntityType(), GenericChestBlockEntity::lidAnimateTick) : null;
    }

    @Override
    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (!isChestBlockedAt(level, pos) && level.getBlockEntity(pos) instanceof GenericChestBlockEntity genericChestBlockEntity)
            return AbstractContainerMenu.getRedstoneSignalFromContainer(genericChestBlockEntity);

        return AbstractContainerMenu.getRedstoneSignalFromContainer(null);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);

        if (blockEntity instanceof GenericChestBlockEntity) {
            ((GenericChestBlockEntity) blockEntity).recheckOpen();
        }
    }

    @Nullable
    public static RCChestTypes getTypeFromBlock(Block block) {
        return block instanceof GenericChestBlock ? ((GenericChestBlock) block).getType() : null;
    }

    public RCChestTypes getType() {
        return this.type;
    }

}
