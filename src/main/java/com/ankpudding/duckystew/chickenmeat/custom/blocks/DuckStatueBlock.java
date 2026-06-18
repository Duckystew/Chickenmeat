package com.ankpudding.duckystew.chickenmeat.custom.blocks;

import com.ankpudding.duckystew.chickenmeat.ModBlocks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class DuckStatueBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<DuckStatueBlock> CODEC = simpleCodec(DuckStatueBlock::new);
    private static final Map<Direction.Axis, VoxelShape> SHAPES = Shapes.rotateHorizontalAxis(
            Shapes.or(
                    Block.box(5.0, 0.0, 3.0, 11.0, 5.0, 13.0),
                    Block.box(4.0, 0.0, 4.0, 12.0, 4.0, 12.0)
            )
    );

    @Override
    public @NonNull MapCodec<? extends DuckStatueBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(final BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING).getAxis());
    }

    @Override
    protected boolean canSurvive(final @NonNull BlockState state, final LevelReader level, final BlockPos pos) {
        boolean survive;
        if (level.isEmptyBlock(pos.below())){
            survive = false;
        } else survive = level.getBlockState(pos.below()).getBlock() != ModBlocks.DUCK_STATUE;
        return survive;
    }

    @Override
    protected @NonNull BlockState updateShape(
            final BlockState state,
            final @NonNull LevelReader level,
            final @NonNull ScheduledTickAccess ticks,
            final @NonNull BlockPos pos,
            final @NonNull Direction directionToNeighbour,
            final @NonNull BlockPos neighbourPos,
            final @NonNull BlockState neighbourState,
            final @NonNull RandomSource random
    ) {
        return !state.canSurvive(level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }


    public DuckStatueBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }
}
