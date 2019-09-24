package com.yurtmod.block;

import com.yurtmod.block.Categories.*;
import com.yurtmod.dimension.TentDimension;
import com.yurtmod.event.TentEvent;
import com.yurtmod.init.NomadicTents;
import com.yurtmod.init.TentConfig;
import com.yurtmod.item.ItemMallet;
import com.yurtmod.item.ItemTent;
import com.yurtmod.structure.StructureBase;
import com.yurtmod.structure.util.StructureData;

import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoor.EnumDoorHalf;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockTentDoor extends BlockUnbreakable
		implements ITileEntityProvider, ITepeeBlock, IYurtBlock, 
		IBedouinBlock, IIndluBlock, IShamianaBlock {
	
	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis",
			EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);
			
	public static final int DECONSTRUCT_DAMAGE = 5;
	private static final double aabbDis = 0.375D;
	public static final AxisAlignedBB AABB_X = new AxisAlignedBB(aabbDis, 0.0D, 0.0D, 1.0D - aabbDis, 1.0D, 1.0D);
	public static final AxisAlignedBB AABB_Z = new AxisAlignedBB(0.0D, 0.0D, aabbDis, 1.0D, 1.0D, 1.0D - aabbDis);
	public final boolean isCube;

	public BlockTentDoor(final String name, final boolean isFull) {
		super(Material.WOOD);
		this.setRegistryName(NomadicTents.MODID, name);
		this.setUnlocalizedName(name);
		this.isCube = isFull;
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER)
				.withProperty(AXIS, EnumFacing.Axis.X));
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		Material m1 = world.getBlockState(pos).getMaterial();
		Material m2 = world.getBlockState(pos.up(1)).getMaterial();
		return (m1 == Material.AIR || m1 == Material.WATER) && (m2 == Material.AIR || m2 == Material.WATER);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			BlockPos base = state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.down(1);
			TileEntity te = worldIn.getTileEntity(base);
			// attempt to activate the TileEntity associated with this door
			if (te instanceof TileEntityTentDoor) {
				TileEntityTentDoor teyd = (TileEntityTentDoor) te;
				StructureData data = teyd.getTentData();
				StructureBase struct = data.getStructure();
				ItemStack held = player.getHeldItem(hand);
				
				// STEP 1:  check if it's the copy tool and creative-mode player
				if((player.capabilities.isCreativeMode || !TentConfig.GENERAL.COPY_CREATIVE_ONLY) 
						&& held != null && held.hasTagCompound() 
						&& held.getTagCompound().hasKey(ItemTent.TAG_COPY_TOOL)
						&& held.getTagCompound().getBoolean(ItemTent.TAG_COPY_TOOL)) {
					final ItemStack copyStack = teyd.getTentData().getDropStack();
					if (copyStack != null) {
						// drop the tent item (without affecting the tent)
						EntityItem dropItem = new EntityItem(worldIn, player.posX, player.posY, player.posZ, copyStack);
						dropItem.setPickupDelay(0);
						worldIn.spawnEntity(dropItem);
						// prevent this interaction from triggering player teleport
						player.timeUntilPortal = player.getPortalCooldown();
					}
					return true;
				}
				// STEP 2:  make sure there is a valid tent before doing anything else
				EnumFacing dir = TentDimension.isTentDimension(worldIn) ? TentDimension.STRUCTURE_DIR
						: struct.getValidFacing(worldIn, base, data.getWidth().getOverworldSize());
				if (dir == null) {
					return false;
				}
				// STEP 3:  deconstruct the tent if the player uses a tentHammer on the door
				// (and in overworld and with fully built tent)
				if (held != null && held.getItem() instanceof ItemMallet
						&& !TentDimension.isTentDimension(worldIn)) {
					// cancel deconstruction if player is not owner
					if(TentConfig.GENERAL.OWNER_PICKUP && teyd.hasOwner() && !teyd.isOwner(player)) {
						return false;
					}
					
					// EXPERIMENTAL: check item use duration
					//System.out.println("Item In Use Count: " + player.getItemInUseCount());
					//System.out.println("Item In Use Max Count: " + held.getMaxItemUseDuration());
					//if(player.getItemInUseCount() + 1 < held.getMaxItemUseDuration()) {
					//	return false;
					//}
					
					// STEP 4:  drop the tent item and damage the tool
					final TentEvent.Deconstruct event = new TentEvent.Deconstruct(teyd, player);
					MinecraftForge.EVENT_BUS.post(event);
					ItemStack toDrop = event.getTentStack();
					if (toDrop != null) {
						// drop the tent item
						EntityItem dropItem = new EntityItem(worldIn, player.posX, player.posY, player.posZ, toDrop);
						dropItem.setPickupDelay(0);
						worldIn.spawnEntity(dropItem);
						// alert the TileEntity
						if(TentConfig.GENERAL.ALLOW_OVERWORLD_SETSPAWN) {
							teyd.onPlayerRemove(player);
						}
						// remove the structure
						struct.remove(worldIn, base, dir, data.getWidth().getOverworldSize());
						// damage the item
						player.getHeldItem(hand).damageItem(DECONSTRUCT_DAMAGE, player);

						return true;
					}
				} else {
					// if the player did not use special items on this door,
					// move on to TileEntity logic to teleport player
					return ((TileEntityTentDoor) te).onPlayerActivate(player);
				}
			} else {
				NomadicTents.LOGGER.error("Error! Failed to retrieve TileEntityTentDoor at " + pos);
			}
		}
		return false;
	}

	@Override
	public void onEntityWalk(final World worldIn, final BlockPos pos, final Entity entityIn) {
		this.onEntityCollidedWithBlock(worldIn, pos, worldIn.getBlockState(pos), entityIn);
	}

	/**
	 * Called When an Entity Collided with the Block
	 */
	@Override
	public void onEntityCollidedWithBlock(final World worldIn, final BlockPos pos, final IBlockState state,
			final Entity entityIn) {
		if (!worldIn.isRemote && worldIn.getBlockState(pos).getValue(BlockDoor.HALF) == EnumDoorHalf.LOWER) {
			TileEntity te = worldIn.getTileEntity(pos);
			if (te instanceof TileEntityTentDoor) {
				TileEntityTentDoor teDoor = (TileEntityTentDoor) te;
				StructureData type = teDoor.getTentData();
				StructureBase struct = type.getStructure();
				// make sure there is a valid tent before doing anything
				EnumFacing dir = TentDimension.isTentDimension(worldIn) ? TentDimension.STRUCTURE_DIR
						: struct.getValidFacing(worldIn, pos, type.getWidth().getOverworldSize());
				if (dir != null) {
					teDoor.onEntityCollide(entityIn, dir);
				}
			}
		}
	}

	@Override
	public void onBlockAdded(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER) {
			worldIn.setBlockState(pos.up(),
					state.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 3);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(final IBlockState state, final IBlockAccess source, final BlockPos pos) {
		if (this.isCube) {
			return FULL_BLOCK_AABB;
		} else {
			EnumFacing.Axis axis = state.getValue(AXIS);
			if (axis == EnumFacing.Axis.X) {
				return AABB_X;
			} else {
				return AABB_Z;
			}
		}
	}

	@Override
	public boolean isOpaqueCube(final IBlockState state) {
		return this.isCube;
	}

	@Override
	public boolean isFullCube(final IBlockState state) {
		return this.isCube;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return this.isCube ? BlockRenderLayer.SOLID : BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onBlockDestroyedByPlayer(final World worldIn, final BlockPos pos, final IBlockState state) {
		if (state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER) {
			// if it's on the bottom
			worldIn.setBlockToAir(pos.up(1));
		} else {
			// if it's on the top
			worldIn.setBlockToAir(pos.down(1));
		}
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockDoor.HALF, AXIS);
	}

	@Override
	public IBlockState getStateFromMeta(final int meta) {
		EnumDoorHalf half = meta % 2 == 0 ? BlockDoor.EnumDoorHalf.LOWER : BlockDoor.EnumDoorHalf.UPPER;
		int metaDiv2 = Math.floorDiv(meta, 2);
		EnumFacing.Axis axis = metaDiv2 < 8 && metaDiv2 % 2 == 1
				? EnumFacing.Axis.Z : EnumFacing.Axis.X;
		return getDefaultState()
				.withProperty(BlockDoor.HALF, half)
				.withProperty(AXIS, axis);
	}

	@Override
	public int getMetaFromState(final IBlockState state) {
		int meta = 0;
		if (state.getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.UPPER) {
			meta += 1;
		}
		if (state.getValue(AXIS) == EnumFacing.Axis.Z) {
			meta += 2;
		}
		return meta;
	}

	@Override
	public boolean hasTileEntity(final IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(final World worldIn, final int meta) {
		TileEntityTentDoor ret = new TileEntityTentDoor();
		ret.setWorld(worldIn);
		return ret;
	}
}
