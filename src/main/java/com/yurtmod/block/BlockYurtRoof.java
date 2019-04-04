package com.yurtmod.block;

import com.yurtmod.block.Categories.IYurtBlock;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.state.IProperty;
import net.minecraft.world.chunk.BlockStateContainer;

public class BlockYurtRoof extends BlockUnbreakable implements IYurtBlock {
	public static final PropertyBool OUTSIDE = PropertyBool.create("outside");
	
	public BlockYurtRoof() {
		super(Material.CLOTH, MapColor.LIGHT_BLUE);
		this.setLightOpacity(LIGHT_OPACITY);
		this.setDefaultState(this.blockState.getBaseState().withProperty(OUTSIDE, Boolean.valueOf(false)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { OUTSIDE });
	}

	@Override
	@Deprecated // because the super method is
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(OUTSIDE, meta > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(OUTSIDE).booleanValue() ? 1 : 0;
	}
}
