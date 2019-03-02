package com.yurtmod.item;

import com.yurtmod.block.BlockTentDoor;
import com.yurtmod.block.Categories.IFrameBlock;
import com.yurtmod.init.NomadicTents;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemMallet extends Item {
	public ItemMallet(ToolMaterial material) {
		this.setMaxDamage(material.getMaxUses());
		this.setCreativeTab(NomadicTents.TAB);
		this.setFull3D();
		this.setMaxStackSize(1);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block b = worldIn.getBlockState(pos).getBlock();
		if (b instanceof IFrameBlock || b instanceof BlockTentDoor) {
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn) {
		return false;
	}

	@Override
	public boolean canItemEditBlocks() {
		return true;
	}
}
