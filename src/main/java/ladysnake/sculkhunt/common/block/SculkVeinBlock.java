package ladysnake.sculkhunt.common.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

import java.util.function.ToIntFunction;

public class SculkVeinBlock extends AbstractLichenBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;

    static {
        WATERLOGGED = Properties.WATERLOGGED;
    }

    public SculkVeinBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    public static ToIntFunction<BlockState> getLuminanceSupplier(int luminance) {
        return (state) -> {
            return AbstractLichenBlock.hasAnyDirection(state) ? luminance : 0;
        };
    }

    public static boolean canGrowOn(BlockView world, Direction direction, BlockPos pos, BlockState state) {
        return Block.isFaceFullSquare(state.getCollisionShape(world, pos), direction.getOpposite());
    }

//	public boolean canReplace(BlockState state, ItemPlacementContext context) {
//		return !context.getStack().isOf(Items.GLOW_LICHEN) || super.canReplace(state, context);
//	}

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    public FluidState getFluidState(BlockState state) {
        return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return state.getFluidState().isEmpty();
    }
}
